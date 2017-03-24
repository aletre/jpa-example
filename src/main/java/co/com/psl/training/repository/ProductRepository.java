package co.com.psl.training.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.com.psl.training.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByCode(String code);
}