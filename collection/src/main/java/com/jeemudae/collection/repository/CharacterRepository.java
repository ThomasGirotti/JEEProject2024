package com.jeemudae.collection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByCollectionSet(CollectionSet collectionSet);
    List<Character> findAllByOrderByPriceDesc(); 
    List<Character> findByInTrade(boolean inTrade);

}