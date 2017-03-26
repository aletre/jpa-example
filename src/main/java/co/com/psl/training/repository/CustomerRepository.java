package co.com.psl.training.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.com.psl.training.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByLastName(String lastName);
}