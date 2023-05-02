package med.voll.api.model.service.impl;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import med.voll.api.model.service.exception.ServiceException;
import med.voll.api.model.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public class GenericCrudServiceImpl<T extends BaseEntity> implements GenericCrudService<T> {


    public static final String ID_NOT_FOUND = "Id not found ";

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
    public T saveAndFlush(T entity) {
        try {
            return repository.saveAndFlush(entity);
        }catch (Exception ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(ID_NOT_FOUND +id);
        }
        repository.deleteById(id);
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ID_NOT_FOUND + id));
    }

    @Override
    public Optional<T> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public T getReferenceById(Long id) {
        if(!repository.existsById(id)){
            throw new ValidacaoException(ID_NOT_FOUND + id);
        }
        return repository.getReferenceById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Page<T> findAll(Pageable paginacao) {
        return repository.findAll(paginacao);
    }
}
