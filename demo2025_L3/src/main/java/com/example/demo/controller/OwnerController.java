package com.example.demo.controller;

import com.example.demo.model.Owner;
import com.example.demo.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping()
    public String showOwners(Model model) {
        List<Owner> owners = ownerService.getAllOwners();
        model.addAttribute("owners", owners);
        return "owners/list-owners";
    }

    // Process adding a new owner
    @PostMapping("/save")
    public String saveOwner(@ModelAttribute Owner owner) {
        ownerService.addOwner(owner);
        return "redirect:/owner";
    }

    // Show form for editing an owner
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Owner owner = ownerService.getOwnerById(id);
            model.addAttribute("owner", owner);
            return "owners/edit-owner";  // Render the edit form page
        } catch (NoSuchElementException e) {
            return "redirect:/owner";
        }
    }
    // Process updating an owner
    @PostMapping("/update")
    public String updateOwner(@ModelAttribute Owner owner) {
        ownerService.updateOwner(owner);
        return "redirect:/owner";
    }

    // Delete an owner
    @GetMapping("/delete/{id}")
    public String deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return "redirect:/owner";
    }


}
