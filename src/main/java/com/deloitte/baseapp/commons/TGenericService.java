package com.deloitte.baseapp.commons;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

public abstract class TGenericService <T extends TGenericEntity<T,ID>, ID> {

    private final TGenericRepository<T,ID> repository;

    public TGenericService(TGenericRepository<T,ID> repository) {
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
    public T get(ID id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(
                ObjectNotFoundException::new
        );
    }

    @Transactional
    public T update (ID id, T updated) throws ObjectNotFoundException{
        T dbDomain = get(id);
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public T create(T newDomain) throws Exception {
        T dbDomain = newDomain.createNewInstance();
        try {
            return repository.save(dbDomain);
        }catch(final ConstraintViolationException ce) {
            throw new Exception(ce.getConstraintName());
        }
    }

    @Transactional
    public void delete(ID id) throws ObjectNotFoundException{
        get(id);
        repository.deleteById(id);
    }

}
