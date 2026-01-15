package com.realestate.marketplace.service;

import com.realestate.marketplace.model.Property;
import com.realestate.marketplace.model.PropertyAlert;
import com.realestate.marketplace.repository.PropertyAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyAlertService {

    private final PropertyAlertRepository alertRepository;

    @Transactional
    public PropertyAlert createAlert(PropertyAlert alert) {
        return alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public List<PropertyAlert> getUserAlerts(String userEmail) {
        return alertRepository.findByUserEmail(userEmail);
    }

    @Transactional
    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }

    @Transactional
    public PropertyAlert updateAlert(Long id, PropertyAlert alertDetails) {
        PropertyAlert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setMinPrice(alertDetails.getMinPrice());
        alert.setMaxPrice(alertDetails.getMaxPrice());
        alert.setMinBedrooms(alertDetails.getMinBedrooms());
        alert.setMaxBedrooms(alertDetails.getMaxBedrooms());
        alert.setCity(alertDetails.getCity());
        alert.setState(alertDetails.getState());
        alert.setPropertyType(alertDetails.getPropertyType());
        alert.setActive(alertDetails.getActive());

        return alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public boolean propertyMatchesAlert(Property property, PropertyAlert alert) {
        if (!alert.getActive()) return false;

        if (alert.getMinPrice() != null && property.getPrice().compareTo(alert.getMinPrice()) < 0) {
            return false;
        }
        if (alert.getMaxPrice() != null && property.getPrice().compareTo(alert.getMaxPrice()) > 0) {
            return false;
        }
        if (alert.getMinBedrooms() != null && property.getBedrooms() < alert.getMinBedrooms()) {
            return false;
        }
        if (alert.getMaxBedrooms() != null && property.getBedrooms() > alert.getMaxBedrooms()) {
            return false;
        }
        if (alert.getCity() != null && !property.getCity().equalsIgnoreCase(alert.getCity())) {
            return false;
        }
        if (alert.getState() != null && !property.getState().equalsIgnoreCase(alert.getState())) {
            return false;
        }
        if (alert.getPropertyType() != null && property.getPropertyType() != alert.getPropertyType()) {
            return false;
        }

        return true;
    }
}
