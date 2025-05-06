package com.example.society.repository;

import com.example.society.guest.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitorRepository extends JpaRepository<Visitor, Long>, JpaSpecificationExecutor<Visitor> {
}
