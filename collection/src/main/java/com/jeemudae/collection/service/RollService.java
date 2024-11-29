package com.jeemudae.collection.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;
import com.jeemudae.collection.repository.User;

import jakarta.transaction.Transactional;

@Service
public class RollService {
    private final CharacterService characterService;
    private final CollectionSetRepository collectionSetRepository;

    public RollService(CharacterService characterService, CollectionSetRepository collectionSetRepository) {
        this.characterService = characterService;
        this.collectionSetRepository = collectionSetRepository;
    }

    @Transactional
    public List<Character> rollCharacters() {
        List<Character> allCharacters = characterService.getAllCharacters();
        Collections.shuffle(allCharacters);
        return allCharacters.subList(0, Math.min(10, allCharacters.size()));
    }

    @Transactional
    public void claimCharacter(User user, Character character) {
        CollectionSet collectionSet = user.getCollectionSet();
        collectionSet.addCharacter(character);
        collectionSetRepository.save(collectionSet);
        characterService.saveCharacter(character);
    }
}
