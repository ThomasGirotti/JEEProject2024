package com.jeemudae.collection.service;

import java.util.Comparator;
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
    
    public List<Character> getCharactersInTrade() {
        return characterRepository.findByInTrade(true); 
    }
    
    public List<Character> getCharactersByIds(List<Long> characterIds) {
        return characterRepository.findAllById(characterIds);
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
    
    public List<Character> getSortedCharactersForUser(User user, String sortBy) {
        CollectionSet collectionSet = user.getCollectionSet();
        List<Character> characters = characterRepository.findByCollectionSet(collectionSet);
        switch (sortBy.toLowerCase()) {
            case "price" -> characters.sort(Comparator.comparingInt(Character::getPrice).reversed());
            case "name" -> characters.sort(Comparator.comparing(Character::getName));
            case "custom" -> characters.sort(Comparator.comparing(Character::getPosition));
            default -> throw new IllegalArgumentException("Critère de tri invalide : " + sortBy);
        }
        for (Character character : characters) {
            System.out.println("Character: " + character.getName() + " - " + character.getPrice() + " - " + character.getPosition());
        }
        return characters;
    }

    @Transactional
    public void moveCharacterLeft(User user, Long characterId) {
        List<Character> characters = getCharactersForUser(user);
        characters.sort(Comparator.comparing(Character::getPosition));
        for (int i = 1; i < characters.size(); i++) {
            if (characters.get(i).getId().equals(characterId)) {
                Character currentCharacter = characters.get(i);
                Character leftCharacter = characters.get(i - 1);
                int currentPosition = currentCharacter.getPosition();
                currentCharacter.setPosition(leftCharacter.getPosition());
                leftCharacter.setPosition(currentPosition);
                characterRepository.save(currentCharacter);
                characterRepository.save(leftCharacter);
                break;
            }
        }
    }

    @Transactional
    public void moveCharacterRight(User user, Long characterId) {
        List<Character> characters = getCharactersForUser(user);
        characters.sort(Comparator.comparing(Character::getPosition));
        for (int i = 0; i < characters.size() - 1; i++) {
            if (characters.get(i).getId().equals(characterId)) {
                Character currentCharacter = characters.get(i);
                Character rightCharacter = characters.get(i + 1);
                int currentPosition = currentCharacter.getPosition();
                currentCharacter.setPosition(rightCharacter.getPosition());
                rightCharacter.setPosition(currentPosition);
                characterRepository.save(currentCharacter);
                characterRepository.save(rightCharacter);
                break;
            }
        }
    }

    @Transactional
    public void updateCharacterPosition(User user, Long characterId, int newPosition) {
        List<Character> characters = getCharactersForUser(user);
        characters.sort(Comparator.comparing(Character::getPosition));
        Character characterToMove = characters.stream()
            .filter(character -> character.getId().equals(characterId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Character not found"));
        characters.remove(characterToMove);
        characters.add(newPosition, characterToMove);
        updateCharacterPositions(characters);
    }

    public void updateCharacterPositions(List<Character> characters) {
        for (int i = 0; i < characters.size(); i++) {
            System.out.println("Updating position of character: " + characters.get(i).getName() + " to " + i);
            characters.get(i).setPosition(i);
            characterRepository.save(characters.get(i));
        }
    }
}