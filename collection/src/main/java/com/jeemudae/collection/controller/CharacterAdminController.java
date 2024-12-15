package com.jeemudae.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.FileStorageService;

@Controller
@RequestMapping("/admin/characters")
public class CharacterAdminController {
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CharacterRepository characterRepository;


    @GetMapping
    public String viewCharacters(Model model) {
        model.addAttribute("characters", characterService.getAllCharacters());
        return "admin/characters";
    }

    @PostMapping("/add")
    public String addCharacter(@RequestParam("name") String name,@RequestParam("price") int price,@RequestParam("image") MultipartFile image,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/collection";
            //throw new AccessDeniedException("Vous n'êtes pas autorisé à effectuer cette action.");
        }
        String filename = null;
        if (image != null && !image.isEmpty()) {
            filename = fileStorageService.store(image);
        }
        Character character = new Character();
        character.setName(name);
        character.setPrice(price);
        character.setImagePath(filename);
        characterRepository.save(character);
        return "redirect:/admin/characters";
    }

    @PostMapping("/delete")
    public String deleteCharacter(@RequestParam("characterId") Long characterId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/collection";
            //throw new AccessDeniedException("Vous n'êtes pas autorisé à effectuer cette action.");
        }
        characterService.deleteCharacterById(characterId);
        return "redirect:/admin/characters";
    }
}