package com.jeemudae.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeemudae.collection.repository.Character;
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

    // Retirer un personnage d'un trade existant
    public void removeCharacterFromTrade(Long tradeId, Long characterId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade non trouvé"));
        
        Character character = userRepository.getOne(characterId).getCollectionSet().getCharacters().stream()
                .filter(c -> c.getId().equals(characterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personnage non trouvé"));
        
        trade.removeCharacter(character);
        tradeRepository.save(trade);
    }

    // Désactiver un trade (exemple si un utilisateur annule son échange)
    public void deactivateTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade non trouvé"));
        trade.setActive(false);
        tradeRepository.save(trade);
    }
}
