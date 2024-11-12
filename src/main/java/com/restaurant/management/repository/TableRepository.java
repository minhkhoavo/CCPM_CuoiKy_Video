package com.restaurant.management.repository;

import com.restaurant.management.model.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<DiningTable, Long> {
}

