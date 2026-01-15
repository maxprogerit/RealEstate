package com.realestate.marketplace.repository;

import com.realestate.marketplace.model.PropertyAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyAlertRepository extends JpaRepository<PropertyAlert, Long> {

    List<PropertyAlert> findByUserEmail(String userEmail);

    List<PropertyAlert> findByActiveTrue();
}
