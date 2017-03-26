package co.com.psl.training.repository;

import java.util.List;

import co.com.psl.training.model.Invoice;

public interface InvoiceRepositoryCustom {
	
	Invoice findCustom(Long invoiceId);

    List<Invoice> getInvoiceListCustomer();
    
    List<Invoice> getInvoiceListByCustomer(Long customerId);
    
    List<Invoice> getInvoiceListCustomer(int firstResult, int maxResult);
}