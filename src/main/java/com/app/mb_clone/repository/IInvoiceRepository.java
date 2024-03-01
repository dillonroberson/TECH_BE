package com.app.mb_clone.repository;

import com.app.mb_clone.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query(value = "select * from invoices where receiver = ?1 or sender = ?1", nativeQuery = true)
    Set<Invoice> findBySenderAccIdOrReceiverAccId(Long senderId);
}
