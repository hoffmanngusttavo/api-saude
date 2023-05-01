package med.voll.api.model.service;

import med.voll.api.model.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenericCrudService<T extends BaseEntity>{


    T save (T entity);

    T saveAndFlush (T entity);

    void deleteById(Long id);

    T findById(Long id);

    Optional<T> getOneById(Long id);

    T getReferenceById(Long id);

    boolean existsById(Long id);

    Page<T> findAll(Pageable paginacao);
}
