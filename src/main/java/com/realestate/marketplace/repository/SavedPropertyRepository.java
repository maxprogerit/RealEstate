package com.realestate.marketplace.repository;

import com.realestate.marketplace.model.SavedProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPropertyRepository extends JpaRepository<SavedProperty, Long> {

    List<SavedProperty> findByUserEmail(String userEmail);

    Optional<SavedProperty> findByUserEmailAndPropertyId(String userEmail, Long propertyId);

    void deleteByUserEmailAndPropertyId(String userEmail, Long propertyId);
}
