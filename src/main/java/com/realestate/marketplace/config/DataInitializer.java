package com.realestate.marketplace.config;

import com.realestate.marketplace.model.Property;
import com.realestate.marketplace.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PropertyRepository propertyRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (propertyRepository.count() > 0) {
            return;
        }

        // Create sample properties
        List<Property> sampleProperties = Arrays.asList(
                createProperty(
                        "Stunning Modern Villa with Ocean View",
                        "Luxurious 4-bedroom villa with panoramic ocean views, infinity pool, and modern amenities.",
                        new BigDecimal("1250000"),
                        4, 3, 3500,
                        "123 Ocean Drive", "San Francisco", "CA", "94102",
                        37.7749, -122.4194,
                        Property.PropertyType.VILLA,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1613490493576-7fde63acd811",
                        2020, 5000, true, true, true
                ),
                createProperty(
                        "Charming Downtown Apartment",
                        "Beautiful 2-bedroom apartment in the heart of downtown with city views.",
                        new BigDecimal("650000"),
                        2, 2, 1200,
                        "456 Market Street", "San Francisco", "CA", "94103",
                        37.7849, -122.4094,
                        Property.PropertyType.APARTMENT,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00",
                        2018, 0, false, false, false
                ),
                createProperty(
                        "Spacious Family House with Garden",
                        "Perfect family home with 5 bedrooms, large garden, and garage.",
                        new BigDecimal("850000"),
                        5, 3, 2800,
                        "789 Elm Street", "San Jose", "CA", "95110",
                        37.3382, -121.8863,
                        Property.PropertyType.HOUSE,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1568605114967-8130f3a36994",
                        2015, 6000, true, false, true
                ),
                createProperty(
                        "Luxury Condo with City Views",
                        "Modern 3-bedroom condo with stunning city views and premium finishes.",
                        new BigDecimal("725000"),
                        3, 2, 1800,
                        "321 Pine Avenue", "Oakland", "CA", "94612",
                        37.8044, -122.2712,
                        Property.PropertyType.CONDO,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1512917774080-9991f1c4c750",
                        2019, 0, true, false, false
                ),
                createProperty(
                        "Cozy Townhouse in Quiet Neighborhood",
                        "Newly renovated 3-bedroom townhouse with modern kitchen and backyard.",
                        new BigDecimal("580000"),
                        3, 2, 1600,
                        "555 Oak Lane", "Berkeley", "CA", "94704",
                        37.8715, -122.2730,
                        Property.PropertyType.TOWNHOUSE,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1580587771525-78b9dba3b914",
                        2017, 2500, true, false, true
                ),
                createProperty(
                        "Prime Commercial Space Downtown",
                        "Excellent location for retail or office space in bustling downtown area.",
                        new BigDecimal("950000"),
                        0, 2, 2500,
                        "888 Business Plaza", "San Francisco", "CA", "94104",
                        37.7906, -122.4016,
                        Property.PropertyType.COMMERCIAL,
                        Property.PropertyStatus.FOR_RENT,
                        "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab",
                        2021, 0, false, false, false
                ),
                createProperty(
                        "Beautiful Suburban House",
                        "Spacious 4-bedroom house in family-friendly neighborhood with great schools.",
                        new BigDecimal("780000"),
                        4, 3, 2400,
                        "999 Maple Drive", "Palo Alto", "CA", "94301",
                        37.4419, -122.1430,
                        Property.PropertyType.HOUSE,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1564013799919-ab600027ffc6",
                        2016, 5500, true, true, true
                ),
                createProperty(
                        "Modern Studio Apartment",
                        "Sleek studio apartment perfect for young professionals, close to tech companies.",
                        new BigDecimal("450000"),
                        1, 1, 650,
                        "777 Tech Way", "Mountain View", "CA", "94041",
                        37.3861, -122.0839,
                        Property.PropertyType.APARTMENT,
                        Property.PropertyStatus.FOR_SALE,
                        "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267",
                        2020, 0, false, false, false
                )
        );

        propertyRepository.saveAll(sampleProperties);
        System.out.println("✓ Sample data initialized: " + sampleProperties.size() + " properties created");
    }

    private Property createProperty(String title, String description, BigDecimal price,
                                   int bedrooms, int bathrooms, int squareFeet,
                                   String address, String city, String state, String zipCode,
                                   double latitude, double longitude,
                                   Property.PropertyType propertyType, Property.PropertyStatus status,
                                   String imageUrl, int yearBuilt, int lotSize,
                                   boolean hasGarage, boolean hasPool, boolean hasGarden) {
        Property property = new Property();
        property.setTitle(title);
        property.setDescription(description);
        property.setPrice(price);
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setSquareFeet(squareFeet);
        property.setAddress(address);
        property.setCity(city);
        property.setState(state);
        property.setZipCode(zipCode);
        property.setLatitude(latitude);
        property.setLongitude(longitude);
        property.setPropertyType(propertyType);
        property.setStatus(status);
        property.setImageUrl(imageUrl);
        property.setYearBuilt(yearBuilt);
        property.setLotSize(lotSize);
        property.setHasGarage(hasGarage);
        property.setHasPool(hasPool);
        property.setHasGarden(hasGarden);
        return property;
    }
}
