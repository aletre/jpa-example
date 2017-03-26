package co.com.psl.training.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import co.com.psl.training.model.Invoice;
import co.com.psl.training.repository.InvoiceRepositoryCustom;

public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	public EntityManager entityManager;
	
	@Override
	public List<Invoice> getInvoiceListCustomer() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
		Root<Invoice> root = cq.from(Invoice.class);
		root.fetch("customer", JoinType.INNER);
		TypedQuery<Invoice> invoiceQuery = entityManager.createQuery(cq);
		return invoiceQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getInvoiceListByCustomer(Long customerId) {
		Query createQuery = entityManager.createQuery("SELECT i FROM Invoice i JOIN FETCH i.customer WHERE i.customer.id = :customerId");
		createQuery.setParameter("customerId", customerId);
		return createQuery.getResultList();
	}

	@Override
	public Invoice findCustom(Long invoiceId) {
		return entityManager.find(Invoice.class, invoiceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getInvoiceListCustomer(int firstResult, int maxResult) {
		Query createQuery = entityManager.createQuery("SELECT i FROM Invoice i JOIN FETCH i.customer");
		createQuery.setFirstResult(firstResult);
		createQuery.setMaxResults(maxResult);
		return createQuery.getResultList();
	}
}
