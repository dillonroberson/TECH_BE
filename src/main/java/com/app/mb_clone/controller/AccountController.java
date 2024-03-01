package com.app.mb_clone.controller;

import com.app.mb_clone.dto.request.*;
import com.app.mb_clone.dto.response.PreExchangeResponse;
import com.app.mb_clone.dto.response.RegisterResponse;
import com.app.mb_clone.dto.response.ResponseMessage;
import com.app.mb_clone.model.Account;
import com.app.mb_clone.model.Bank;
import com.app.mb_clone.model.User;
import com.app.mb_clone.service.IAccountService;
import com.app.mb_clone.service.IBankService;
import com.app.mb_clone.service.IUserService;
import com.app.mb_clone.utils.RandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final IBankService bankService;

    @PostMapping("/pre-exchange")
    public ResponseEntity<?> doPreExchange(@RequestBody PreExchangeRequest preExchangeRequest) {
        Optional<Account> receiver = accountService.findAccountByBankAndNumber(preExchangeRequest.getBankId(), preExchangeRequest.getReceiverNumber());
        Account sender = accountService.findById(preExchangeRequest.getSenderId());
        if(!receiver.isPresent()) {
            // create receiver
            String stringSet = preExchangeRequest.getReceiverName().replaceAll(" ", "");
            String pointer = RandomNumber.getRandomNumber(4);
            String email = stringSet.concat(pointer).concat("@gmail.com");

            RegisterForm regisUser = RegisterForm.builder()
                    .name(preExchangeRequest.getReceiverName())
                    .email(email)
                    .password(passwordEncoder.encode("Pikachu123@"))
                    .phone(RandomNumber.getRandomNumber(10))
                    .build();
            RegisterResponse registerResponse = userService.register(regisUser);
            Account newReceiver = accountService.findAccountByUserAndBank(registerResponse.getUser().getId(), (long) 1).orElseThrow(() -> new RuntimeException("Fail!"));
            Bank bankSet = bankService.findById(preExchangeRequest.getBankId());
            newReceiver.setBank(bankSet);
            newReceiver.setNumber(preExchangeRequest.getReceiverNumber());
            Account resetNumberReceiver = accountService.save(newReceiver);
            PreExchangeResponse responseAPI = PreExchangeResponse.builder()
                    .amount(preExchangeRequest.getAmount())
                    .receiverAcc(resetNumberReceiver)
                    .message(preExchangeRequest.getMessage())
                    .senderAcc(sender)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().status("OK").message("Accept").data(responseAPI).build());
        }

        if (sender.getId().equals(receiver.get().getId())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseMessage.builder().status("FAIL").message("Duplicated Account").build());
        }
        PreExchangeResponse responseAPI = PreExchangeResponse.builder()
                .amount(preExchangeRequest.getAmount())
                .receiverAcc(receiver.get())
                .message(preExchangeRequest.getMessage())
                .senderAcc(sender)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().status("OK").message("Accept").data(responseAPI).build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> doRegisterPIN (@PathVariable Long id, @RequestBody PinRegisterRequest pinRegisterRequest) {
        boolean result = accountService.registerPIN(id, pinRegisterRequest.getPin());
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseMessage.builder().status("FAIL").message("Register PIN fail").build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().status("OK").message("Register PIN success").build());
    }

    @GetMapping("/userId")
    public ResponseEntity<?> findByUserId (@RequestParam Long userId) {
        Optional<Account> account = accountService.findAccountByUserAndBank(userId, (long) 1);
        if (!account.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().status("FAIL").message("Register PIN fail").build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
