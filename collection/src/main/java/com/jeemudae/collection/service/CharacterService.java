package com.jeemudae.collection.service;

import java.util.List;

        characterRepository.save(character);
        //eventPublisher.publishEvent(new CharacterClaimedEvent(character)); //TODO: Event to maintain collection value across all users
    }

    @Transactional
    public void deleteCharacterById(Long characterId) {
        CollectionSet collectionSet = collectionSetRepository.findByCharactersId(characterId);
        Character character = characterRepository.findById(characterId).orElseThrow();
        if (collectionSet != null) {
            collectionSet.removeCharacter(character);
            collectionSetRepository.save(collectionSet);
        }
        characterRepository.delete(character);
    }
}