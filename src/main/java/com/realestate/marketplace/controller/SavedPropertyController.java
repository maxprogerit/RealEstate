package com.realestate.marketplace.controller;

import com.realestate.marketplace.model.Property;
import com.realestate.marketplace.model.SavedProperty;
import com.realestate.marketplace.service.SavedPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SavedPropertyController {

    private final SavedPropertyService savedPropertyService;

    @PostMapping
    public ResponseEntity<SavedProperty> saveProperty(
            @RequestParam String userEmail,
            @RequestParam Long propertyId) {
        try {
            SavedProperty saved = savedPropertyService.saveProperty(userEmail, propertyId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> unsaveProperty(
            @RequestParam String userEmail,
            @RequestParam Long propertyId) {
        savedPropertyService.unsaveProperty(userEmail, propertyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Property>> getSavedProperties(@RequestParam String userEmail) {
        List<Property> properties = savedPropertyService.getSavedProperties(userEmail);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isPropertySaved(
            @RequestParam String userEmail,
            @RequestParam Long propertyId) {
        boolean isSaved = savedPropertyService.isPropertySaved(userEmail, propertyId);
        return ResponseEntity.ok(isSaved);
    }
}
