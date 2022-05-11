package com.deloitte.baseapp.commons;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericService<T extends GenericEntity<T>> {

    private final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    @Cacheable(value = "warmStorage")
    public List<T> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "warmStorage")
    public Page<T> getPage(PagingRequest pagingRequest) {
        final GenericSpecification<T> specification = new GenericSpecification<>(pagingRequest);
        final Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLength());
        return repository.findAll(specification, pageable);
    }

    @Cacheable(value = "warmStorage")
    public T get(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException()
        );
    }

    @Transactional
    public T update(Long id, T updated) throws ObjectNotFoundException {
        T dbDomain = get(id);
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public T create(T newDomain) throws Exception {
        T dbDomain = newDomain.createNewInstance();
        try {
            return repository.save(dbDomain);
        } catch (final ConstraintViolationException ce) {
            throw new Exception(ce.getConstraintName());
        }
    }

    @Transactional
    public void delete(Long id) throws ObjectNotFoundException {
        //check if object with this id exists
        get(id);
        repository.deleteById(id);
    }

    public void deleteBulk(List<Long> ids) {
        repository.deleteAllById(ids);
    }
}

