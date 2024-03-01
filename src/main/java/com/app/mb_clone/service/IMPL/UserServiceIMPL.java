package com.app.mb_clone.service.IMPL;

import com.app.mb_clone.dto.request.LoginFormRequest;
import com.app.mb_clone.dto.request.RegisterForm;
import com.app.mb_clone.dto.response.JwtResponse;
import com.app.mb_clone.dto.response.RegisterResponse;
import com.app.mb_clone.model.Account;
import com.app.mb_clone.model.Bank;
import com.app.mb_clone.model.Role;
import com.app.mb_clone.model.User;
import com.app.mb_clone.repository.IUserRepository;
import com.app.mb_clone.security.jwt.JwtProvider;
import com.app.mb_clone.security.userPrincipal.UserPrincipal;
import com.app.mb_clone.service.IAccountService;
import com.app.mb_clone.service.IBankService;
import com.app.mb_clone.service.IRoleService;
import com.app.mb_clone.service.IUserService;
import com.app.mb_clone.utils.CreditCardNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements IUserService {
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final IBankService bankService;
    private final IAccountService accountService;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public JwtResponse login(LoginFormRequest loginFormRequest) {
        Optional<User> user = findByUsernameOrEmailOrPhoneNumber(loginFormRequest.getUsername());
        if (!user.isPresent()) {
            return JwtResponse.builder()
                    .status("Failed")
                    .build();
        } else {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.get().getEmail(),
                                loginFormRequest.getPassword()
                        ));
                User justSavedUser = save(user.get());
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                String token = jwtProvider.generateToken(authentication);
                return JwtResponse.builder()
                        .expiredTime(jwtProvider.getExpiredTimeFromToken(token))
                        .status("OK")
                        .type("Bearer")
                        .user(justSavedUser)
                        .token(token)
                        .roles(userPrincipal.getRoles())
                        .build();
            } catch (AuthenticationException e) {
                return JwtResponse.builder()
                        .status("Failed")
                        .build();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Optional<User> findByUsernameOrEmailOrPhoneNumber(String findingUserData) {
        Optional<User> user = findByEmail(findingUserData);
        if(!user.isPresent()) {
            user = findByPhone(findingUserData);
        }
        return user;
    }

    @Override
    public RegisterResponse register(RegisterForm registerForm) {
        Set<String> stringRoles = registerForm.getRoles();
        Set<Role> roles = roleService.registerRole(stringRoles);
        boolean isExistEmail = existsByEmail(registerForm.getEmail());
        if (isExistEmail) {
            return RegisterResponse.builder()
                    .status("Fail!")
                    .message("Email is existed!")
                    .build();
        }

        boolean isExistPhoneNumber = existsByPhone(registerForm.getPhone());
        if (isExistPhoneNumber) {
            return RegisterResponse.builder()
                    .status("Fail!")
                    .message("Phone is existed!")
                    .build();
        }

        User user = User.builder()
                .name(registerForm.getName())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .email(registerForm.getEmail())
                .phone(registerForm.getPhone())
                .roles(roles)
                .build();

        User userJustAdd = save(user);

        String bin = "1800";
        int length = 10;
        String numberAccount = new CreditCardNumberGenerator().generate(bin, length);
        String bankCode = "MBB";
        Bank bank = bankService.findBankByCode(bankCode);

        Set<Account> accounts = new HashSet<>();
        Account account = Account.builder()
                .user(userJustAdd)
                .balance(1000000000)
                .number(numberAccount)
                .bank(bank)
                .build();
        accounts.add(account);
        accountService.save(account);
        userJustAdd.setAccounts(accounts);
        return RegisterResponse.builder()
                .status("OK")
                .message("Register Successfully!")
                .user(userJustAdd)
                .build();
    }
}
