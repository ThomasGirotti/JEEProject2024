package com.jeemudae.collection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUser(User user);
}