package com.jeemudae.collection.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.service.CollectionSetService;

@Component
public class CharacterUpdatedEventListener {
    
    @Autowired
    CollectionSetService collectionSetService;
    
    @Async
    @EventListener
    public void handleCharacterUpdated(CharacterUpdatedEvent event) {
        System.out.println("Received Async event");
        System.out.println("Recalculated collection set, START");
        CollectionSet collectionSet = event.getCollectionSet();
        System.out.println("Collection to recalculate : " + collectionSet);
        if (collectionSet != null) {
            collectionSetService.recalculateCollectionSet(collectionSet);
            System.out.println("Recalculated collection set, END");
        } else {
            System.out.println("Collection set associated is null");
        }
    }
}
