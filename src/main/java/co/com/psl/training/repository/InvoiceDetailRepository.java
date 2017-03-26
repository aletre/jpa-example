package co.com.psl.training.repository;

import org.springframework.data.repository.CrudRepository;

import co.com.psl.training.model.InvoiceDetail;

public interface InvoiceDetailRepository extends CrudRepository<InvoiceDetail, Long>, InvoiceDetailRepositoryCustom {

}