package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.User;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getCharactersForUser(User user) {
        return characterRepository.findByUser(user);
    }

    public void saveCharacter(Character character) {
        characterRepository.save(character);
    }

}
