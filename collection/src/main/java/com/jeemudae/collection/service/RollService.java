package com.jeemudae.collection.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
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

    @Transactional
    public List<Character> rollCharacters(User user) {
        if(userService.canRoll(user)) {
            List<Character> allCharacters = characterService.getAllCharacters();
            Collections.shuffle(allCharacters);
            return allCharacters.subList(0, Math.min(10, allCharacters.size()));
        } else {
            return null;
        }
    }

    @Transactional
    public void claimCharacter(User user, Character character) {
        System.out.println("RollService.claimCharacter()");
        CollectionSet collectionSet = user.getCollectionSet();
        List<Character> characters = collectionSet.getCharacters();
        
        characters.add(character);
        collectionSet.setCharacters(characters);
        collectionSetRepository.save(collectionSet);

        character.setCollectionSet(collectionSet);
        characterService.updateCharacter(character);

        userService.updateClaimTime(user);
        userRepository.save(user);
    }
}
