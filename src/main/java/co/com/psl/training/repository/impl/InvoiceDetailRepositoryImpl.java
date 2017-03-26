package co.com.psl.training.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import co.com.psl.training.model.InvoiceDetail;
import co.com.psl.training.repository.InvoiceDetailRepositoryCustom;

public class InvoiceDetailRepositoryImpl implements InvoiceDetailRepositoryCustom {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	public EntityManager entityManager;

	@Override
	public InvoiceDetail findByInvoiceAndProductCode(Long invoiceId, String productCode) {
		Query createQuery = entityManager.createQuery(
				"SELECT id FROM InvoiceDetail id JOIN FETCH id.product JOIN FETCH id.invoice WHERE id.product.code = :productCode AND id.invoice.id=:invoiceId");
		createQuery.setParameter("invoiceId", invoiceId);
		createQuery.setParameter("productCode", productCode);
		return (InvoiceDetail) createQuery.getSingleResult();
	}
}
