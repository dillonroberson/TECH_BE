package com.app.mb_clone.controller;

import com.app.mb_clone.dto.request.InvoiceUpdateRequest;
import com.app.mb_clone.dto.request.TransactionRequest;
import com.app.mb_clone.dto.response.ResponseMessage;
import com.app.mb_clone.model.Invoice;
import com.app.mb_clone.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final IInvoiceService invoiceService;

    @PatchMapping("/do-transaction")
    public ResponseEntity<?> doTransaction(@RequestBody TransactionRequest transactionRequest) {
        Optional<Invoice> invoice = invoiceService.doTransaction(transactionRequest);
        if(!invoice.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseMessage.builder().status("FAILED").message("Incorrect PIN").build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().status("OK").message("Transaction success!").data(invoice).build());
    }

    @GetMapping("/account")
    public ResponseEntity<?> findInvoiceByAccount (@RequestParam Long accountId) {
        Set<Invoice> invoices = invoiceService.findBySenderAccId(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(invoices);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody InvoiceUpdateRequest invoiceUpdateRequest) {
        Invoice invoice = invoiceService.update(id, invoiceUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().message("Update Success!").status("OK").data(invoice).build());
    }
}
