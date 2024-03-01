package com.app.mb_clone.service;

import com.app.mb_clone.dto.request.InvoiceUpdateRequest;
import com.app.mb_clone.dto.request.TransactionRequest;
import com.app.mb_clone.model.Invoice;
import com.app.mb_clone.service.design.IGenericService;

import java.util.Optional;
import java.util.Set;

public interface IInvoiceService extends IGenericService<Invoice> {
    Optional<Invoice> doTransaction(TransactionRequest transactionRequest);
    Set<Invoice> findBySenderAccId(Long senderId);
    Invoice update(Long id, InvoiceUpdateRequest invoiceUpdateRequest);
}
