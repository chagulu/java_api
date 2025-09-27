package com.example.society.specification;

import com.example.society.guest.entity.Visitor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VisitorSpecification {

    public static Specification<Visitor> getVisitorFilters(Map<String, String> filters) {
    return (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (filters.containsKey("id")) {
            predicates.add(cb.equal(root.get("id"), Long.parseLong(filters.get("id"))));
        }
        if (filters.containsKey("guestName")) {
            predicates.add(cb.like(cb.lower(root.get("guestName")), "%" + filters.get("guestName").toLowerCase() + "%"));
        }
        if (filters.containsKey("mobile")) {
            predicates.add(cb.equal(root.get("mobile"), filters.get("mobile")));
        }
        if (filters.containsKey("approveStatus")) {
            predicates.add(cb.equal(root.get("approveStatus"), Visitor.ApproveStatus.valueOf(filters.get("approveStatus"))));
        }

        // 🔒 Always restrict by resident’s flat & building
        if (filters.containsKey("flatNumber")) {
            predicates.add(cb.equal(root.get("flatNumber"), filters.get("flatNumber")));
        }
        if (filters.containsKey("buildingNumber")) {
            predicates.add(cb.equal(root.get("buildingNumber"), filters.get("buildingNumber")));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    };
}

}
