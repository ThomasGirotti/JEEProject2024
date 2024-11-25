package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.User;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getCharactersForUser(User user) {
        CollectionSet collectionSet = user.getCollectionSet();
        return characterRepository.findByCollectionSet(collectionSet);
    }

    public void saveCharacter(Character character) {
        CollectionSet collectionSet = character.getCollectionSet();
        characterRepository.save(character);
        collectionSet.recalculateTotalValue();
    }

    public void deleteCharacter(Character character) {
        CollectionSet collectionSet = character.getCollectionSet();
        characterRepository.delete(character);
        if (collectionSet != null) {
            collectionSet.recalculateTotalValue();
        }
    }
}