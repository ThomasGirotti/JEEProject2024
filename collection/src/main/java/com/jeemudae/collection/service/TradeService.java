package com.jeemudae.collection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // Créer un trade pour un utilisateur
    public Trade createTradeForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + userId));

        Trade trade = new Trade(user);
        return tradeRepository.save(trade);
    }

    // Ajouter un personnage à un trade existant
    public void addCharacterToTrade(Long tradeId, Long characterId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade non trouvé"));
        
        Character character = userRepository.getOne(characterId).getCollectionSet().getCharacters().stream()
                .filter(c -> c.getId().equals(characterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personnage non trouvé"));
        
        trade.addCharacter(character);
        tradeRepository.save(trade);
    }


    public void setCharacterInTrade(Long characterId, boolean inTrade) {
        // Utilise CharacterRepository pour trouver le personnage
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Personnage non trouvé : " + characterId));
    
        // Modifie l'état du champ inTrade
        character.setInTrade(inTrade);
    
        // Utilise CharacterRepository pour sauvegarder l'entité mise à jour
        characterRepository.save(character);
    }
    
    

    
    public void deactivateTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade non trouvé"));
        trade.setActive(false);
        tradeRepository.save(trade);
    }

    public void createTradeOffer(Character tradeCharacter, List<Character> offeredCharacters, User currentUser) {
        // Créer l'objet Trade
        Trade trade = new Trade(currentUser);  // Associer l'utilisateur connecté
        trade.setTradeCharacter(tradeCharacter);  // Personnage principal à échanger
        trade.setOfferedCharacters(offeredCharacters);  // Personnages à proposer
    
        // Sauvegarder l'offre de trade
        tradeRepository.save(trade);
    }
    
}