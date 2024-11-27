package com.jeemudae.collection.events;

import com.jeemudae.collection.repository.Character;

public class CharacterClaimedEvent {
    private final Character character;

    public CharacterClaimedEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}