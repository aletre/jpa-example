package co.com.psl.training.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import co.com.psl.training.model.Product;
import co.com.psl.training.repository.ProductRepositoryCustom;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	public EntityManager entityManager;
	
	@Override
	public Product findProductWithInvoiceDetailList(String code) {
		Query createQuery = entityManager.createQuery("SELECT p FROM Product p WHERE p.code = :code");
		createQuery.setParameter("code", code);
		return (Product) createQuery.getSingleResult();
	}
}
