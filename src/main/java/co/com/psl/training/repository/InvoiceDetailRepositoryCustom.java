package co.com.psl.training.repository;

import co.com.psl.training.model.InvoiceDetail;

public interface InvoiceDetailRepositoryCustom {
	
	InvoiceDetail findByInvoiceAndProductCode(Long invoice, String productCode);

}