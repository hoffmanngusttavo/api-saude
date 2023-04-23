package med.voll.api.model.service.impl;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import med.voll.api.model.service.exception.ServiceException;
import med.voll.api.model.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public class GenericCrudServiceImpl<T extends BaseEntity> implements GenericCrudService<T> {


   @Autowired
   protected GenericCrudRepository<T> repository;

    @Override
    @Transactional
    public T save(T entity) {
        try {
           return repository.save(entity);
        }catch (Exception ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            if(!repository.existsById(id)){
                throw new EntityNotFoundException("Id not found "+id);
            }
            repository.deleteById(id);
        }catch (Exception ex){
            throw new ServiceException("Não foi possível remover objeto com id "+id, ex);
        }

    }

    @Override
    public T findById(Long id) {
        try {
           return repository.findById(id)
                   .orElseThrow(() -> new EntityNotFoundException("Id not found "+ id));
        }catch (Exception ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<T> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public T getReferenceById(Long id) {
        if(!repository.existsById(id)){
            throw new ValidacaoException("Id not found "+ id);
        }
        return repository.getReferenceById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}