package com.deloitte.baseapp.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public abstract class GenericController<T extends GenericEntity<T>> {

    private final GenericService<T> service;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<T>(repository) {
        };
    }

    @PostMapping("/datatable")
    public MessageResponse<Page<T>> getPage(@RequestBody PagingRequest pagingRequest) {
        return new MessageResponse<>(service.getPage(pagingRequest));
    }

    @GetMapping("/{id}")
    public MessageResponse getOne(@PathVariable Long id) {
        try {
            return new MessageResponse(service.get(id));
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable("id") Long id, @RequestBody T updated) {
        try {
            return new MessageResponse(service.update(id, updated));
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

    @PostMapping("")
    public MessageResponse create(@RequestBody T created) {
        return new MessageResponse(service.create(created));
    }

    @PostMapping("/bulk-delete")
    public MessageResponse deleteBulk(@RequestBody CommonRequest.BulkDelete payload) {
        service.deleteBulk(payload.getIds());
        return new MessageResponse(true);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        try {
            service.delete(id);

            return new MessageResponse(true);
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }
}

