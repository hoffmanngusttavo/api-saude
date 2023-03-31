package med.voll.api.model.service;

import med.voll.api.model.entity.base.BaseEntity;

public interface GenericCrudService<T extends BaseEntity>{


    T save (T entity);

    void deleteById(Long id);

    T findById(Long id);

}
