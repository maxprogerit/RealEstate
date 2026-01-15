// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// User email for demo purposes
const USER_EMAIL = 'demo@example.com';

// Utility function to format price
function formatPrice(price) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 0
    }).format(price);
}

// Utility function to show toast messages
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span>${type === 'success' ? '✓' : '✗'}</span>
        <span>${message}</span>
    `;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideIn 0.3s ease-out reverse';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Create property card HTML
function createPropertyCard(property, showSaveButton = true) {
    const imageUrl = property.imageUrl || 'https://via.placeholder.com/400x300/667eea/ffffff?text=No+Image';

    return `
        <div class="property-card" data-property-id="${property.id}">
            <img src="${imageUrl}" alt="${property.title}" class="property-image" 
                 onerror="this.src='https://via.placeholder.com/400x300/667eea/ffffff?text=No+Image'">
            <div class="property-content">
                <div class="property-price">${formatPrice(property.price)}</div>
                <h3 class="property-title">${property.title}</h3>
                <div class="property-location">
                    📍 ${property.address}, ${property.city}, ${property.state}
                </div>
                <div class="property-features">
                    <span class="feature-badge">🛏️ ${property.bedrooms} Beds</span>
                    <span class="feature-badge">🚿 ${property.bathrooms} Baths</span>
                    <span class="feature-badge">📐 ${property.squareFeet} sqft</span>
                </div>
                <div style="display: flex; gap: 0.5rem; margin-top: 1rem;">
                    <span class="feature-badge">${property.propertyType}</span>
                    <span class="feature-badge">${property.status.replace('_', ' ')}</span>
                </div>
                ${showSaveButton ? `
                <div style="margin-top: 1rem;">
                    <button class="btn btn-primary" onclick="saveProperty(${property.id})">
                        💾 Save Property
                    </button>
                </div>
                ` : ''}
            </div>
        </div>
    `;
}

// Fetch all properties
async function fetchProperties(filters = {}) {
    try {
        const params = new URLSearchParams();

        if (filters.minPrice) params.append('minPrice', filters.minPrice);
        if (filters.maxPrice) params.append('maxPrice', filters.maxPrice);
        if (filters.minBedrooms) params.append('minBedrooms', filters.minBedrooms);
        if (filters.maxBedrooms) params.append('maxBedrooms', filters.maxBedrooms);
        if (filters.minBathrooms) params.append('minBathrooms', filters.minBathrooms);
        if (filters.city) params.append('city', filters.city);
        if (filters.state) params.append('state', filters.state);
        if (filters.propertyType) params.append('propertyType', filters.propertyType);
        if (filters.status) params.append('status', filters.status);

        const url = params.toString()
            ? `${API_BASE_URL}/properties/search?${params}`
            : `${API_BASE_URL}/properties`;

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch properties');
        return await response.json();
    } catch (error) {
        console.error('Error fetching properties:', error);
        showToast('Failed to load properties', 'error');
        return [];
    }
}

// Save a property
async function saveProperty(propertyId) {
    try {
        const response = await fetch(
            `${API_BASE_URL}/saved-properties?userEmail=${USER_EMAIL}&propertyId=${propertyId}`,
            { method: 'POST' }
        );

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Failed to save property');
        }

        showToast('Property saved successfully!', 'success');
    } catch (error) {
        console.error('Error saving property:', error);
        if (error.message.includes('already saved')) {
            showToast('Property already saved', 'error');
        } else {
            showToast('Failed to save property', 'error');
        }
    }
}

// Unsave a property
async function unsaveProperty(propertyId) {
    try {
        const response = await fetch(
            `${API_BASE_URL}/saved-properties?userEmail=${USER_EMAIL}&propertyId=${propertyId}`,
            { method: 'DELETE' }
        );

        if (!response.ok) throw new Error('Failed to unsave property');

        showToast('Property removed from saved', 'success');
        return true;
    } catch (error) {
        console.error('Error unsaving property:', error);
        showToast('Failed to remove property', 'error');
        return false;
    }
}

// Get saved properties
async function getSavedProperties() {
    try {
        const response = await fetch(`${API_BASE_URL}/saved-properties?userEmail=${USER_EMAIL}`);
        if (!response.ok) throw new Error('Failed to fetch saved properties');
        return await response.json();
    } catch (error) {
        console.error('Error fetching saved properties:', error);
        showToast('Failed to load saved properties', 'error');
        return [];
    }
}

// Create a new property
async function createProperty(propertyData) {
    try {
        const response = await fetch(`${API_BASE_URL}/properties`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(propertyData)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to create property');
        }

        return await response.json();
    } catch (error) {
        console.error('Error creating property:', error);
        throw error;
    }
}

// Get user alerts
async function getUserAlerts() {
    try {
        const response = await fetch(`${API_BASE_URL}/alerts?userEmail=${USER_EMAIL}`);
        if (!response.ok) throw new Error('Failed to fetch alerts');
        return await response.json();
    } catch (error) {
        console.error('Error fetching alerts:', error);
        showToast('Failed to load alerts', 'error');
        return [];
    }
}

// Create alert
async function createAlert(alertData) {
    try {
        const response = await fetch(`${API_BASE_URL}/alerts`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(alertData)
        });

        if (!response.ok) throw new Error('Failed to create alert');
        return await response.json();
    } catch (error) {
        console.error('Error creating alert:', error);
        throw error;
    }
}

// Delete alert
async function deleteAlert(alertId) {
    try {
        const response = await fetch(`${API_BASE_URL}/alerts/${alertId}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Failed to delete alert');
        return true;
    } catch (error) {
        console.error('Error deleting alert:', error);
        throw error;
    }
}
