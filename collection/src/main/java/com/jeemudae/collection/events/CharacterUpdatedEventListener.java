package com.jeemudae.collection.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.service.CollectionSetService;

@Component
public class CharacterUpdatedEventListener {
    
    @Autowired
    CollectionSetService collectionSetService;
    
    @Async
    @EventListener
    public void handleCharacterClaimed(CharacterUpdatedEvent event) {
        System.out.println("Received Async event");
        Character claimedCharacter = event.getCharacter();
        System.out.println("Character causing the recalculation: " + claimedCharacter.getName());
        CollectionSet collectionSet = claimedCharacter.getCollectionSet();
        if (collectionSet != null) {
            collectionSetService.recalculateCollectionSet(collectionSet);
            System.out.println("Recalculated collection set, END");
        } else {
            System.out.println("Collection set associated is null");
        }
    }
}
