package com.realestate.marketplace.service;

import com.realestate.marketplace.model.Property;
import com.realestate.marketplace.model.SavedProperty;
import com.realestate.marketplace.repository.PropertyRepository;
import com.realestate.marketplace.repository.SavedPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedPropertyService {

    private final SavedPropertyRepository savedPropertyRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public SavedProperty saveProperty(String userEmail, Long propertyId) {
        // Check if already saved
        if (savedPropertyRepository.findByUserEmailAndPropertyId(userEmail, propertyId).isPresent()) {
            throw new RuntimeException("Property already saved");
        }

        // Check if property exists
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        SavedProperty savedProperty = new SavedProperty();
        savedProperty.setUserEmail(userEmail);
        savedProperty.setPropertyId(propertyId);

        return savedPropertyRepository.save(savedProperty);
    }

    @Transactional
    public void unsaveProperty(String userEmail, Long propertyId) {
        savedPropertyRepository.deleteByUserEmailAndPropertyId(userEmail, propertyId);
    }

    @Transactional(readOnly = true)
    public List<Property> getSavedProperties(String userEmail) {
        List<SavedProperty> savedProperties = savedPropertyRepository.findByUserEmail(userEmail);
        
        return savedProperties.stream()
                .map(sp -> propertyRepository.findById(sp.getPropertyId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isPropertySaved(String userEmail, Long propertyId) {
        return savedPropertyRepository.findByUserEmailAndPropertyId(userEmail, propertyId).isPresent();
    }
}
