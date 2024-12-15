package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.events.CharacterUpdatedEvent;
import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CharacterService {

    @Autowired
    private CollectionSetRepository collectionSetRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserRepository userRepository;

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public List<Character> getCharactersForUser(User user) {
        CollectionSet collectionSet = user.getCollectionSet();
        return characterRepository.findByCollectionSet(collectionSet);
    }

    public Character getCharacterById(Long characterId) {
        return characterRepository.findById(characterId).orElse(null);
    }

    public List<Character> getAllCharactersSortedByPriceDesc() {
        return characterRepository.findAllByOrderByPriceDesc(); 
    }

    @Transactional
    public void updateCall(long collectionId) {
        CollectionSet collectionSet = collectionSetRepository.findById(collectionId).orElseThrow();
        eventPublisher.publishEvent(new CharacterUpdatedEvent(collectionSet));
    }

    @Transactional
    public void sellCharacter(User user, Long characterId) {
        CollectionSet collectionSet = collectionSetRepository.findByCharactersId(characterId);
        Character character = characterRepository.findById(characterId).orElseThrow();
        int value = character.getPrice();
        System.out.println("Selling character for " + value);
        collectionSet.getCharacters().remove(character);
        collectionSetRepository.save(collectionSet);
        character.setCollectionSet(null);
        characterRepository.save(character);
        user.updateCash(value);
        userRepository.save(user);
    }

    @Transactional
    public void deleteCharacterById(Long characterId) {
        CollectionSet collectionSet = collectionSetRepository.findByCharactersId(characterId);
        Character character = characterRepository.findById(characterId).orElseThrow();
        if (collectionSet != null) {
            collectionSet.getCharacters().remove(character);
            collectionSetRepository.save(collectionSet);
            character.setCollectionSet(null);
            characterRepository.save(character);
        }
        characterRepository.delete(character);
    }
}