package com.jeemudae.collection.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.CollectionSet;
import com.jeemudae.collection.repository.CollectionSetRepository;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RollService {
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private CollectionSetRepository collectionSetRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CharacterRepository characterRepository;
    
    @Transactional
    public List<Character> rollCharacters() {
        List<Character> allCharacters = characterService.getAllCharacters();
        Collections.shuffle(allCharacters);
        for (Character character : allCharacters) {
            System.out.println("Rolled character: " + character.getName());
        }
        return allCharacters.stream().limit(10).collect(Collectors.toList());
    }
    
    @Transactional
    public void claimCharacter(User user, Character character) {
        System.out.println("Claiming character: " + character.getName());
        System.out.println("Character is associated with : " + character.getCollectionSet());
        CollectionSet collectionSet = user.getCollectionSet();
        List<Character> characters = collectionSet.getCharacters();
        characters.add(character);
        collectionSet.setCharacters(characters);
        collectionSetRepository.save(collectionSet);
        character.setCollectionSet(collectionSet);
        characterRepository.save(character);
        userService.updateClaimTime(user);
        userRepository.save(user);
        characterService.updateCall(collectionSet.getId());
    }
    
    @Transactional
    public void boostCharacter(User user, Character character) {
        System.out.println("Boosting character: " + character.getName());
        System.out.println("Character is associated with : " + character.getCollectionSet());
        if (character.getCollectionSet().getUser().getId().equals(user.getId())) {
            System.out.println("Character already claimed by current user");
            character.setPrice((int) (character.getPrice() * 1.1));
            characterRepository.save(character);
            userService.updateBoostTime(user);
            userRepository.save(user);
            characterService.updateCall(character.getCollectionSet().getId());
        } else {
            System.out.println("Character already claimed by : " + character.getCollectionSet().getUser().getUsername());
        }
    }
}
