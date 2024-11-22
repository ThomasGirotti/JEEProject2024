package com.jeemudae.collection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Long>{

	List<Passport> findByOwner(Customer customer);
}
