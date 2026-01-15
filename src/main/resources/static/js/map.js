// Map functionality
let map;
let markers = [];
let infoWindow;

// Initialize map
function initMap() {
    // Default center (San Francisco)
    const center = { lat: 37.7749, lng: -122.4194 };

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: center,
        styles: [
            {
                featureType: 'poi',
                elementType: 'labels',
                stylers: [{ visibility: 'off' }]
            }
        ]
    });

    infoWindow = new google.maps.InfoWindow();

    // Load and display properties
    loadMapProperties();
}

// Load properties on map
async function loadMapProperties() {
    try {
        const properties = await fetchProperties();
        displayPropertiesOnMap(properties);
    } catch (error) {
        console.error('Error loading map properties:', error);
        showToast('Failed to load properties on map', 'error');
    }
}

// Display properties on map
function displayPropertiesOnMap(properties) {
    // Clear existing markers
    markers.forEach(marker => marker.setMap(null));
    markers = [];

    if (properties.length === 0) {
        showToast('No properties found', 'error');
        return;
    }

    const bounds = new google.maps.LatLngBounds();

    properties.forEach(property => {
        const position = {
            lat: property.latitude,
            lng: property.longitude
        };

        const marker = new google.maps.Marker({
            position: position,
            map: map,
            title: property.title,
            animation: google.maps.Animation.DROP
        });

        // Create info window content
        const contentString = `
            <div style="padding: 10px; max-width: 300px;">
                <h3 style="margin: 0 0 10px 0; color: #2563eb;">${property.title}</h3>
                <p style="margin: 5px 0; font-size: 1.2em; font-weight: bold;">
                    ${formatPrice(property.price)}
                </p>
                <p style="margin: 5px 0;">
                    📍 ${property.address}, ${property.city}, ${property.state}
                </p>
                <p style="margin: 5px 0;">
                    🛏️ ${property.bedrooms} Beds | 
                    🚿 ${property.bathrooms} Baths | 
                    📐 ${property.squareFeet} sqft
                </p>
                <p style="margin: 5px 0;">
                    <span style="background: #f3f4f6; padding: 3px 8px; border-radius: 4px; font-size: 0.9em;">
                        ${property.propertyType}
                    </span>
                    <span style="background: #f3f4f6; padding: 3px 8px; border-radius: 4px; font-size: 0.9em;">
                        ${property.status.replace('_', ' ')}
                    </span>
                </p>
                <button onclick="saveProperty(${property.id})" 
                        style="margin-top: 10px; background: #2563eb; color: white; 
                               border: none; padding: 8px 16px; border-radius: 6px; 
                               cursor: pointer; font-weight: 600;">
                    💾 Save Property
                </button>
            </div>
        `;

        marker.addListener('click', () => {
            infoWindow.setContent(contentString);
            infoWindow.open(map, marker);
        });

        markers.push(marker);
        bounds.extend(position);
    });

    // Fit map to show all markers
    if (properties.length > 0) {
        map.fitBounds(bounds);

        // Prevent too much zoom for single property
        google.maps.event.addListenerOnce(map, 'bounds_changed', function () {
            if (map.getZoom() > 15) {
                map.setZoom(15);
            }
        });
    }
}

// Update map with filters
async function updateMapProperties() {
    const filters = {
        city: document.getElementById('mapCity').value || null,
        propertyType: document.getElementById('mapPropertyType').value || null
    };

    const properties = await fetchProperties(filters);
    displayPropertiesOnMap(properties);
}

// Make sure the map initializes after the script loads
window.initMap = initMap;
