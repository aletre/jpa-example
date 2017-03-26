package co.com.psl.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.com.psl.training.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long>, InvoiceRepositoryCustom {	

    List<Invoice> findByCustomer(Long customerId);
    
    @Query("SELECT i FROM Invoice i JOIN FETCH i.customer WHERE i.customer.id = :customerId ")
    List<Invoice> findInvoiceListByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT i FROM Invoice i JOIN FETCH i.customer ")
    List<Invoice> findInvoiceListWithCustomer();
}