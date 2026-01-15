package com.realestate.marketplace.controller;

import com.realestate.marketplace.model.PropertyAlert;
import com.realestate.marketplace.service.PropertyAlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PropertyAlertController {

    private final PropertyAlertService alertService;

    @PostMapping
    public ResponseEntity<PropertyAlert> createAlert(@Valid @RequestBody PropertyAlert alert) {
        PropertyAlert createdAlert = alertService.createAlert(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }

    @GetMapping
    public ResponseEntity<List<PropertyAlert>> getUserAlerts(@RequestParam String userEmail) {
        List<PropertyAlert> alerts = alertService.getUserAlerts(userEmail);
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyAlert> updateAlert(
            @PathVariable Long id,
            @Valid @RequestBody PropertyAlert alert) {
        try {
            PropertyAlert updatedAlert = alertService.updateAlert(id, alert);
            return ResponseEntity.ok(updatedAlert);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}
