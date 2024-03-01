package com.app.mb_clone.service.IMPL;

import com.app.mb_clone.dto.request.InvoiceUpdateRequest;
import com.app.mb_clone.dto.request.TransactionRequest;
import com.app.mb_clone.model.Account;
import com.app.mb_clone.model.Invoice;
import com.app.mb_clone.repository.IInvoiceRepository;
import com.app.mb_clone.service.IAccountService;
import com.app.mb_clone.service.IInvoiceService;
import com.app.mb_clone.utils.TransactionCodeGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IInvoiceServiceIMPL implements IInvoiceService {
    private final IInvoiceRepository invoiceRepository;
    private final IAccountService accountService;
    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice Not Found!"));
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public void remove(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Optional<Invoice> doTransaction(TransactionRequest transactionRequest) {
        // check PIN valid
        Account senderAcc = accountService.findById(transactionRequest.getSenderAccId());
        Account receiverAcc = accountService.findById(transactionRequest.getReceiverAccId());
        int amount = transactionRequest.getAmount();
        String PIN = transactionRequest.getPin();
        // Nhập sai PIN
        if(!senderAcc.getPIN().equals(PIN)) {
            return Optional.empty();
        }

        // Nhập đúng -> Thực hiện giao dịch
        senderAcc.setBalance(senderAcc.getBalance() - amount);
        Account newSenderAcc = accountService.save(senderAcc);
        receiverAcc.setBalance(receiverAcc.getBalance() + amount);
        Account newReceiverAcc = accountService.save(receiverAcc);

        // Tạo Invoice
        String transactionCode = TransactionCodeGenerate.generateRandomString();

        Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        Invoice invoice = Invoice.builder()
                .timeStamp(timestamp)
                .transactionCode(transactionCode)
                .message(transactionRequest.getMessage())
                .receiverAcc(newReceiverAcc)
                .senderAcc(newSenderAcc)
                .amount(amount)
                .remainSendAcc(senderAcc.getBalance())
                .remainReceiveAcc(receiverAcc.getBalance())
                .build();
        Invoice newInvoice = save(invoice);
        return Optional.of(newInvoice);
    }

    @Override
    public Set<Invoice> findBySenderAccId(Long senderId) {
        return invoiceRepository.findBySenderAccIdOrReceiverAccId(senderId);
    }

    @Override
    public Invoice update(Long id, InvoiceUpdateRequest invoiceUpdateRequest) {
        Invoice invoice = findById(id);
        invoice.setMessage(invoiceUpdateRequest.getMessage());
        invoice.setTimeStamp(invoiceUpdateRequest.getTimestamp());
        invoice.setAmount(Math.abs(Integer.parseInt(invoiceUpdateRequest.getAmount())));
        invoice.setRemainSendAcc(Math.abs(Integer.parseInt(invoiceUpdateRequest.getRemain())));
        invoice.setRemainReceiveAcc(Math.abs(Integer.parseInt(invoiceUpdateRequest.getRemain())));
        Account temp = invoice.getReceiverAcc();
        invoice.setReceiverAcc(invoice.getSenderAcc());
        invoice.setSenderAcc(temp);
        Account a = accountService.findById(invoice.getReceiverAcc().getId());
        a.setBalance(Integer.parseInt(invoiceUpdateRequest.getBalance()));
        a.setNumber(invoiceUpdateRequest.getNumber());
        accountService.save(a);
        Account b = accountService.findById(invoice.getSenderAcc().getId());
        b.setBalance(Integer.parseInt(invoiceUpdateRequest.getBalance()));
        b.setNumber(invoiceUpdateRequest.getNumber());
        accountService.save(b);

        return save(invoice);
    }
}
