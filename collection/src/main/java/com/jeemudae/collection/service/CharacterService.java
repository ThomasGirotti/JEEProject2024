package com.jeemudae.collection.service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }
}
