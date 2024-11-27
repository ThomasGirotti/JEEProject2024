package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.events.CharacterClaimedEvent;
import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.User;

import jakarta.transaction.Transactional;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CharacterService(CharacterRepository characterRepository, ApplicationEventPublisher eventPublisher) {
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
        eventPublisher.publishEvent(new CharacterClaimedEvent(character));
    }

    @Transactional
    public void deleteCharacter(CollectionSet collectionSet, Character character) {
        collectionSet.removeCharacter(character);
        characterRepository.delete(character);
        eventPublisher.publishEvent(new CharacterClaimedEvent(character));
    }
}