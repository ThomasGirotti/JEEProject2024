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

    public List<Character> getCharactersForUser(User user) {
        CollectionSet collectionSet = user.getCollectionSet();
        return characterRepository.findByCollectionSet(collectionSet);
    }

    @Transactional
    public void saveCharacter(CollectionSet collectionSet, Character character) {
        collectionSet.addCharacter(character);
        characterRepository.save(character);
        collectionSetRepository.save(collectionSet);
        //eventPublisher.publishEvent(new CharacterClaimedEvent(character)); //TODO: Event to maintain collection value across all users
    }

    @Transactional
    public void deleteCharacter(CollectionSet collectionSet, Character character) {
        collectionSet.removeCharacter(character);
        characterRepository.delete(character);
        collectionSetRepository.save(collectionSet);
        //eventPublisher.publishEvent(new CharacterClaimedEvent(character)); //TODO: Event to maintain collection value across all users
    }
}