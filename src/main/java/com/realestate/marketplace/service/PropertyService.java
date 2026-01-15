package com.realestate.marketplace.service;

import com.realestate.marketplace.model.Property;
import com.realestate.marketplace.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Transactional(readOnly = true)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    @Transactional
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Transactional
    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));

        property.setTitle(propertyDetails.getTitle());
        property.setDescription(propertyDetails.getDescription());
        property.setPrice(propertyDetails.getPrice());
        property.setBedrooms(propertyDetails.getBedrooms());
        property.setBathrooms(propertyDetails.getBathrooms());
        property.setSquareFeet(propertyDetails.getSquareFeet());
        property.setAddress(propertyDetails.getAddress());
        property.setCity(propertyDetails.getCity());
        property.setState(propertyDetails.getState());
        property.setZipCode(propertyDetails.getZipCode());
        property.setLatitude(propertyDetails.getLatitude());
        property.setLongitude(propertyDetails.getLongitude());
        property.setPropertyType(propertyDetails.getPropertyType());
        property.setStatus(propertyDetails.getStatus());
        property.setImageUrl(propertyDetails.getImageUrl());
        property.setYearBuilt(propertyDetails.getYearBuilt());
        property.setLotSize(propertyDetails.getLotSize());
        property.setHasGarage(propertyDetails.getHasGarage());
        property.setHasPool(propertyDetails.getHasPool());
        property.setHasGarden(propertyDetails.getHasGarden());

        return propertyRepository.save(property);
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Property> searchProperties(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minBedrooms,
            Integer maxBedrooms,
            Integer minBathrooms,
            String city,
            String state,
            Property.PropertyType propertyType,
            Property.PropertyStatus status
    ) {
        return propertyRepository.findByFilters(
                minPrice, maxPrice, minBedrooms, maxBedrooms, minBathrooms,
                city, state, propertyType, status
        );
    }

    @Transactional(readOnly = true)
    public List<Property> findPropertiesNearLocation(Double latitude, Double longitude, Double radiusKm) {
        return propertyRepository.findPropertiesNearLocation(latitude, longitude, radiusKm);
    }

    @Transactional(readOnly = true)
    public List<Property> getPropertiesByCity(String city) {
        return propertyRepository.findByCity(city);
    }

    @Transactional(readOnly = true)
    public List<Property> getPropertiesByState(String state) {
        return propertyRepository.findByState(state);
    }
}
