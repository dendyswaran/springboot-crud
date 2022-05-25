package com.deloitte.baseapp.commons;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericSpecification<T> implements Specification<T> {

    private PagingRequest pagingRequest;

    public GenericSpecification(PagingRequest pagingRequest) {
        this.pagingRequest = pagingRequest;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (null == pagingRequest.getDtSearch()) {
            return null;
        }

        final Map<String, PagingRequest.FilterObject> filterObjectMap =
                pagingRequest.getDtSearch();

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();
        for (String key : filterObjectMap.keySet()) {
            final PagingRequest.FilterObject filterObject = filterObjectMap.get(key);
            final PagingRequest.FilterObjectType type = filterObject.getType();
            final String value = filterObject.getValue();

            if (null != value && value.trim().length() > 0) {
                switch (filterObject.getMatchMode()) {
                    case CONTAINS:
                        predicates.add(criteriaBuilder.like(root.get(key), "%" + filterObject.getValue() + "%"));
                        break;
                    case EQUALS:
                        predicates.add(criteriaBuilder.equal(root.get(key), this.predictValueType(type, value)));
                        break;
                    case NOT_EQUALS:
                        predicates.add(criteriaBuilder.notEqual(root.get(key), this.predictValueType(type, value)));
                        break;
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Object predictValueType(PagingRequest.FilterObjectType type, String value) {
        switch (type) {
            case NUMERIC:
                return Integer.parseInt(value);
            default:
                return value;
        }
    }
}
