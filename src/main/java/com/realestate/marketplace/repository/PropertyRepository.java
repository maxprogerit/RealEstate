package com.realestate.marketplace.repository;

import com.realestate.marketplace.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByCity(String city);

    List<Property> findByState(String state);

    List<Property> findByPropertyType(Property.PropertyType propertyType);

    List<Property> findByStatus(Property.PropertyStatus status);

    @Query("SELECT p FROM Property p WHERE " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:minBedrooms IS NULL OR p.bedrooms >= :minBedrooms) AND " +
           "(:maxBedrooms IS NULL OR p.bedrooms <= :maxBedrooms) AND " +
           "(:minBathrooms IS NULL OR p.bathrooms >= :minBathrooms) AND " +
           "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
           "(:state IS NULL OR LOWER(p.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
           "(:propertyType IS NULL OR p.propertyType = :propertyType) AND " +
           "(:status IS NULL OR p.status = :status)")
    List<Property> findByFilters(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minBedrooms") Integer minBedrooms,
            @Param("maxBedrooms") Integer maxBedrooms,
            @Param("minBathrooms") Integer minBathrooms,
            @Param("city") String city,
            @Param("state") String state,
            @Param("propertyType") Property.PropertyType propertyType,
            @Param("status") Property.PropertyStatus status
    );

    @Query("SELECT p FROM Property p WHERE " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
           "cos(radians(p.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radiusKm")
    List<Property> findPropertiesNearLocation(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radiusKm") Double radiusKm
    );
}
