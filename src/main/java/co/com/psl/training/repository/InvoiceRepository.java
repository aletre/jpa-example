package co.com.psl.training.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.com.psl.training.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findByCustomer(Long customerId);
}