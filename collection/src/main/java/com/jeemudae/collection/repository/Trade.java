package com.jeemudae.collection.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> charactersToTrade = new ArrayList<>();


        // Ajoutez ceci dans la classe Trade
    @ManyToOne
    @JoinColumn(name = "trade_character_id", nullable = true)
    private Character tradeCharacter; // Personnage principal du trade

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trade_id")
    private List<Character> offeredCharacters = new ArrayList<>(); // Liste des personnages proposés


    @Column(nullable = false)
    private boolean isActive = true;

    public Trade() {}

    public Trade(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Character> getCharactersToTrade() {
        return charactersToTrade;
    }

    public void setCharactersToTrade(List<Character> charactersToTrade) {
        this.charactersToTrade = charactersToTrade;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addCharacter(Character character) {
        this.charactersToTrade.add(character);
    }

    public void removeCharacter(Character character) {
        this.charactersToTrade.remove(character);
    }

        // Définit le personnage principal du trade
    public void setTradeCharacter(Character tradeCharacter) {
        this.tradeCharacter = tradeCharacter;
    }

    // Retourne le personnage principal du trade
    public Character getTradeCharacter() {
        return tradeCharacter;
    }

    // Définit les personnages proposés en échange
    public void setOfferedCharacters(List<Character> offeredCharacters) {
        this.offeredCharacters.clear(); // On vide la liste actuelle
        this.offeredCharacters.addAll(offeredCharacters); // On ajoute les nouveaux personnages
    }

    // Retourne les personnages proposés en échange
    public List<Character> getOfferedCharacters() {
        return offeredCharacters;
    }

}
