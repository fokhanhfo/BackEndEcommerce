package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EntitySpecification<E> implements Specification<E> {
    private final List<SearchCriteria> criteriaList = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            switch (criteria.getOperation()) {
                case ">":
                    if (criteria.getValue() instanceof Comparable) {
                        predicates.add(builder.greaterThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    }
                    break;
                case "<":
                    if (criteria.getValue() instanceof Comparable) {
                        predicates.add(builder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    }
                    break;
                case ":":
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case "like":
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
                default:
                    throw new UnsupportedOperationException("Operation not supported");
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
