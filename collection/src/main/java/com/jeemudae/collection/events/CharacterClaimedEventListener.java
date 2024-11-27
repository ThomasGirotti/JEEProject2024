package com.jeemudae.collection.events;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;

@Component
public class CharacterClaimedEventListener {
    private final CollectionSetRepository collectionSetRepository;
    
    public CharacterClaimedEventListener(CollectionSetRepository collectionSetRepository) {
        this.collectionSetRepository = collectionSetRepository;
    }
    
    @Async
    @EventListener
    public void handleCharacterClaimed(CharacterClaimedEvent event) {
        Character claimedCharacter = event.getCharacter();
        List<CollectionSet> allCollections = collectionSetRepository.findAll();
        for (CollectionSet collection : allCollections) {
            collection.recalculateTotalValue();
        }
        collectionSetRepository.saveAll(allCollections);
        System.out.println("Recalcul des collections terminé après le claim de : " + claimedCharacter.getName());
    }
}
