package com.jeemudae.collection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionSetRepository extends JpaRepository<CollectionSet, Long> {
    CollectionSet findByCharactersId(Long characterId);
}
