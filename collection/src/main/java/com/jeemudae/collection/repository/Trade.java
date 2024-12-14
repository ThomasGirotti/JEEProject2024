package com.jeemudae.collection.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "trade_character_id", nullable = true)
    private Character tradeCharacter;

    @OneToMany(mappedBy = "trade", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> offeredCharacters = new ArrayList<>();

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

    public Character getTradeCharacter() {
        return tradeCharacter;
    }

    public void setTradeCharacter(Character tradeCharacter) {
        this.tradeCharacter = tradeCharacter;
    }

    public List<Character> getOfferedCharacters() {
        return offeredCharacters;
    }

    public void setOfferedCharacters(List<Character> offeredCharacters) {
        this.offeredCharacters.clear();
        this.offeredCharacters.addAll(offeredCharacters);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> charactersToTrade = new ArrayList<>();

    public List<Character> getCharactersToTrade() {
        return charactersToTrade;
    }

    public void setCharactersToTrade(List<Character> charactersToTrade) {
        this.charactersToTrade = charactersToTrade;
    }

    public void addCharacter(Character character) {
        this.charactersToTrade.add(character);
    }

    public void removeCharacter(Character character) {
        this.charactersToTrade.remove(character);
    }


}
