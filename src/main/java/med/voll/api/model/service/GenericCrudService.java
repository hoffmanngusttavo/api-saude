package med.voll.api.model.service;

import med.voll.api.model.entity.base.BaseEntity;

import java.util.Optional;

public interface GenericCrudService<T extends BaseEntity>{


    T save (T entity);

    void deleteById(Long id);

    T findById(Long id);

    Optional<T> getOneById(Long id);

    T getReferenceById(Long id);

    boolean existsById(Long id);
}
