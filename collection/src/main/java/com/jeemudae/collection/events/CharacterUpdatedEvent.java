package com.jeemudae.collection.events;

import com.jeemudae.collection.repository.Character;

public class CharacterUpdatedEvent {
    private final Character character;

    public CharacterUpdatedEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}