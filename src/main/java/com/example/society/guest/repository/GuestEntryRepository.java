package com.example.society.guest.repository;

import com.example.society.guest.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestEntryRepository extends JpaRepository<Visitor, Long> {
}
