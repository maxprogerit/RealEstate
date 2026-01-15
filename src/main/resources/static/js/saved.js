// Saved properties page functionality
document.addEventListener('DOMContentLoaded', async () => {
    await loadSavedProperties();
});

async function loadSavedProperties() {
    const container = document.getElementById('savedPropertiesContainer');
    container.innerHTML = '<div class="spinner"></div>';

    const properties = await getSavedProperties();

    if (properties.length === 0) {
        container.innerHTML = `
            <div style="grid-column: 1/-1; text-align: center; padding: 3rem;">
                <h3>No saved properties</h3>
                <p>Start browsing <a href="/properties">properties</a> to save your favorites</p>
            </div>
        `;
        return;
    }

    container.innerHTML = properties.map(property => createSavedPropertyCard(property)).join('');
}

function createSavedPropertyCard(property) {
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
                <div style="margin-top: 1rem;">
                    <button class="btn btn-secondary" onclick="removeSavedProperty(${property.id})">
                        ✗ Remove
                    </button>
                </div>
            </div>
        </div>
    `;
}

async function removeSavedProperty(propertyId) {
    const success = await unsaveProperty(propertyId);
    if (success) {
        // Remove the card from the DOM
        const card = document.querySelector(`[data-property-id="${propertyId}"]`);
        if (card) {
            card.style.animation = 'slideIn 0.3s ease-out reverse';
            setTimeout(() => {
                card.remove();

                // Check if there are no more saved properties
                const container = document.getElementById('savedPropertiesContainer');
                if (container.children.length === 0) {
                    container.innerHTML = `
                        <div style="grid-column: 1/-1; text-align: center; padding: 3rem;">
                            <h3>No saved properties</h3>
                            <p>Start browsing <a href="/properties">properties</a> to save your favorites</p>
                        </div>
                    `;
                }
            }, 300);
        }
    }
}
