package com.demo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.library.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
	
}