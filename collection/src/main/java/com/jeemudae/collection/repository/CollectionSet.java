package com.jeemudae.collection.repository;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "collection_sets")
public class CollectionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "collectionSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;

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

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        recalculateTotalValue();
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void recalculateTotalValue() {
        this.totalValue = characters.stream().mapToInt(Character::getValue).sum();
    }

    public void addCharacter(Character character) {
        characters.add(character);
        recalculateTotalValue();
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
        recalculateTotalValue();
    }
}
