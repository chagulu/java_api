package com.example.society.specification;

import com.example.society.model.Visitor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VisitorSpecification {

    public static Specification<Visitor> getVisitorFilters(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    predicates.add(
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(key)),
                            "%" + value.toLowerCase() + "%"
                        )
                    );
                }
            });

            // ✅ Correct return — creates a single Predicate
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
