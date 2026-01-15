package com.realestate.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "property_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User email is required")
    @Column(nullable = false)
    private String userEmail;

    @Column(name = "min_price")
    private BigDecimal minPrice;

    @Column(name = "max_price")
    private BigDecimal maxPrice;

    @Column(name = "min_bedrooms")
    private Integer minBedrooms;

    @Column(name = "max_bedrooms")
    private Integer maxBedrooms;

    private String city;
    private String state;

    @Enumerated(EnumType.STRING)
    private Property.PropertyType propertyType;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
