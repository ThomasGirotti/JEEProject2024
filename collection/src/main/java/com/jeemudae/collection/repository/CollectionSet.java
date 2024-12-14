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

    @OneToMany(mappedBy = "collectionSet", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Character> characters = new ArrayList<>();

    @Column(nullable = false)
    private int totalValue;

    @Column(nullable = false)
    private int collectionSize;

    public CollectionSet() {
        this.totalValue = 0;
        this.collectionSize = 0;
    }

    public CollectionSet(User user) {
        this.user = user;
        this.totalValue = 0;
        this.collectionSize = 0;
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

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public int getCollectionSize() {
        return collectionSize;
    }

    public void setCollectionSize(int collectionSize) {
        this.collectionSize = collectionSize;
    }

    public void recalculateTotalValue() {
        if (characters == null) {
            System.out.println("Characters list is null!");
            return;
        }
        int sum = characters.stream().mapToInt(character -> {System.out.println("Character price: " + character.getPrice());return character.getPrice();}).sum();
        setTotalValue(sum);
        System.out.println("Total value recalculated: " + sum);
    }

    public void recalculateCollectionSize() {
        if (characters == null) {
            System.out.println("Characters list is null!");
            return;
        }
        setCollectionSize(characters.size());
        System.out.println("Collection size recalculated: " + characters.size());
    }

    public void addCharacter(Character character) {
        if (characters != null) {
            characters.add(character);
            character.setCollectionSet(this);
            recalculateTotalValue();
            recalculateCollectionSize();
        }
    }

    public void removeCharacter(Character character) {
        if (characters != null && characters.contains(character)) {
            System.out.println("Removing character: " + character.getName());
            characters.remove(character);
            character.setCollectionSet(null);
            recalculateTotalValue();
            recalculateCollectionSize();
        }
    }
}