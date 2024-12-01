package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;
import com.jeemudae.collection.repository.User;

import jakarta.transaction.Transactional;

@Service
public class CharacterService {
    private final CollectionSetRepository collectionSetRepository;
    private final CharacterRepository characterRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CharacterService(CharacterRepository characterRepository, ApplicationEventPublisher eventPublisher, CollectionSetRepository collectionSetRepository) {
        this.collectionSetRepository = collectionSetRepository;
        this.characterRepository = characterRepository;
        this.eventPublisher = eventPublisher;
    }

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
    
    public List<Character> getCharactersInTrade() {
        return characterRepository.findByInTrade(true); 
    }
    

    @Transactional
    public void saveCharacter(Character character) {
        characterRepository.save(character);
        //eventPublisher.publishEvent(new CharacterClaimedEvent(character)); //TODO: Event to maintain collection value across all users
    }

    @Transactional
    public void deleteCharacterById(Long characterId) {
        CollectionSet collectionSet = collectionSetRepository.findByCharactersId(characterId);
        Character character = characterRepository.findById(characterId).orElseThrow();
        if (collectionSet != null) {
            collectionSet.removeCharacter(character);
            collectionSetRepository.save(collectionSet);
        }
        characterRepository.delete(character);
    }
}