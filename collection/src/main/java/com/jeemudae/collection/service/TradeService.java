package com.jeemudae.collection.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.Trade;
import com.jeemudae.collection.repository.TradeRepository;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

@Service
public class TradeService {
    
    @Autowired
    private TradeRepository tradeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CharacterRepository characterRepository;
    
    public Trade createTradeForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + userId));
        Trade trade = new Trade(user);
        return tradeRepository.save(trade);
    }
    
    public void setCharacterInTrade(Long characterId, boolean inTrade) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new RuntimeException("Personnage non trouvé : " + characterId));
        character.setInTrade(inTrade);
        characterRepository.save(character);
    }
    
    @Transactional
    public void createTradeOffer(Character tradeCharacter, List<Character> offeredCharacters, User user) {
        List<Character> offeredCharactersAttached = new ArrayList<>();
        Trade trade = new Trade(tradeCharacter.getCollectionSet().getUser());
        trade.setTradeCharacter(tradeCharacter);

        for (Character character : offeredCharacters) {
            Character offeredCharacterAttached = characterRepository.findById(character.getId())
                .orElseThrow(() -> new RuntimeException("Personnage offert introuvable"));
            offeredCharacterAttached.setTrade(trade);
            offeredCharactersAttached.add(offeredCharacterAttached);
        }

        trade.setOfferedCharacters(offeredCharactersAttached);
        tradeRepository.save(trade);
    }
}