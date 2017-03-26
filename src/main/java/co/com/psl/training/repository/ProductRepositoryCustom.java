package co.com.psl.training.repository;

import co.com.psl.training.model.Product;

public interface ProductRepositoryCustom {

    Product findProductWithInvoiceDetailList(String code);
}