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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection_sets")
public class CollectionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "collectionSet", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters = new ArrayList<>();

    @Column(nullable = false)
    private int totalValue;

    public CollectionSet() {
        this.totalValue = 0;
    }

    public CollectionSet(User user) {
        this.user = user;
        this.totalValue = 0;
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

    public List<Character> getCharacters() {
        return characters;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void recalculateTotalValue() {
        this.totalValue = characters.stream().mapToInt(Character::getPrice).sum();
        System.out.println("Total value recalculated: " + totalValue);
    }

    public void addCharacter(Character character) {
        if (characters != null) {
            characters.add(character);
            character.setCollectionSet(this);
            recalculateTotalValue();
        }
    }
    
    public void removeCharacter(Character character) {
        if (characters != null) {
            characters.remove(character);
            character.setCollectionSet(null);
            recalculateTotalValue();
        }
    }
}