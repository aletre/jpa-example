package co.com.psl.training.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.com.psl.training.model.IdType;

public interface IdTypeRepository extends CrudRepository<IdType, String> {

    List<IdType> findByName(String name);
}