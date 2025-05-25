package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import jakarta.persistence.criteria.*;
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
            Path<?> path = getPath(root, criteria.getKey());

            switch (criteria.getOperation()) {
                case ">":
                    if (criteria.getValue() instanceof Comparable) {
                        predicates.add(builder.greaterThan((Expression<? extends Comparable>) path, (Comparable) criteria.getValue()));
                    }
                    break;
                case "<":
                    if (criteria.getValue() instanceof Comparable) {
                        predicates.add(builder.lessThan((Expression<? extends Comparable>) path, (Comparable) criteria.getValue()));
                    }
                    break;
                case ":":
                    predicates.add(builder.equal(path, criteria.getValue()));
                    break;
                case "like":
                    predicates.add(builder.like(path.as(String.class), "%" + criteria.getValue() + "%"));
                    break;
                case "notIn":
                    if (criteria.getValue() instanceof List<?>) {
                        predicates.add(path.in((List<?>) criteria.getValue()).not());
                    }
                    break;
                case "!=":
                    predicates.add(builder.notEqual(path, criteria.getValue()));
                    break;
                default:
                    throw new UnsupportedOperationException("Operation not supported");
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private Path<?> getPath(Root<E> root, String key) {
        if (!key.contains(".")) {
            return root.get(key);
        }

        String[] keys = key.split("\\.");
        Path<?> path = root.join(keys[0], JoinType.INNER); // Join cấp đầu tiên

        for (int i = 1; i < keys.length - 1; i++) {
            path = ((From<?, ?>) path).join(keys[i], JoinType.INNER); // Join tiếp các cấp trung gian
        }

        return path.get(keys[keys.length - 1]); // Lấy field cuối cùng
    }
}
