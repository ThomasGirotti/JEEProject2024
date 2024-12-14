package com.jeemudae.collection.events;

import com.jeemudae.collection.repository.CollectionSet;

public class CharacterUpdatedEvent {
    private final CollectionSet collectionSet;

    public CharacterUpdatedEvent(CollectionSet collectionSet) {
        this.collectionSet = collectionSet;
    }

    public CollectionSet getCollectionSet() {
        return collectionSet;
    }
}