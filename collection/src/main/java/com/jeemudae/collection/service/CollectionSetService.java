package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;

import jakarta.transaction.Transactional;

@Service
public class CollectionSetService {

    @Autowired
    private CollectionSetRepository collectionSetRepository;
    
    @Transactional
    public void recalculateAllCollectionSets() {
        List<CollectionSet> allCollections = collectionSetRepository.findAll();
        for (CollectionSet collection : allCollections) {
            collection.recalculateTotalValue();
        }
        collectionSetRepository.saveAll(allCollections);
    }
    
    @Transactional
    public void recalculateCollectionSet(CollectionSet collectionSet) {
        List<Character> characters = collectionSet.getCharacters();
        if (characters != null) {
            int sum = characters.stream().mapToInt(character -> {System.out.println("Character price: " + character.getPrice());return character.getPrice();}).sum();
            int size = characters.size();
            collectionSet.setTotalValue(sum);
            System.out.println("Total value of collection set: " + sum);
            collectionSet.setCollectionSize(size);
            System.out.println("Collection size: " + size);
            collectionSetRepository.save(collectionSet);
        }
    }
}
