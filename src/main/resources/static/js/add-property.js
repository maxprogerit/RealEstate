// Add property page functionality
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('propertyForm');
    form.addEventListener('submit', handleSubmit);
});

async function handleSubmit(e) {
    e.preventDefault();

    const propertyData = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        propertyType: document.getElementById('propertyType').value,
        status: document.getElementById('status').value,
        bedrooms: parseInt(document.getElementById('bedrooms').value),
        bathrooms: parseInt(document.getElementById('bathrooms').value),
        squareFeet: parseInt(document.getElementById('squareFeet').value),
        address: document.getElementById('address').value,
        city: document.getElementById('city').value,
        state: document.getElementById('state').value,
        zipCode: document.getElementById('zipCode').value,
        latitude: parseFloat(document.getElementById('latitude').value),
        longitude: parseFloat(document.getElementById('longitude').value),
        imageUrl: document.getElementById('imageUrl').value || null,
        yearBuilt: document.getElementById('yearBuilt').value ?
            parseInt(document.getElementById('yearBuilt').value) : null,
        lotSize: document.getElementById('lotSize').value ?
            parseInt(document.getElementById('lotSize').value) : null,
        hasGarage: document.getElementById('hasGarage').checked,
        hasPool: document.getElementById('hasPool').checked,
        hasGarden: document.getElementById('hasGarden').checked
    };

    try {
        const submitBtn = e.target.querySelector('button[type="submit"]');
        submitBtn.disabled = true;
        submitBtn.textContent = 'Submitting...';

        await createProperty(propertyData);

        showToast('Property listed successfully!', 'success');

        // Reset form after successful submission
        setTimeout(() => {
            document.getElementById('propertyForm').reset();
            submitBtn.disabled = false;
            submitBtn.textContent = 'Submit Property';
        }, 2000);

    } catch (error) {
        console.error('Error submitting property:', error);
        showToast('Failed to list property. Please try again.', 'error');

        const submitBtn = e.target.querySelector('button[type="submit"]');
        submitBtn.disabled = false;
        submitBtn.textContent = 'Submit Property';
    }
}

// Helper to get coordinates from address (if Google Maps Geocoding API is enabled)
async function getCoordinatesFromAddress() {
    const address = document.getElementById('address').value;
    const city = document.getElementById('city').value;
    const state = document.getElementById('state').value;

    if (!address || !city || !state) {
        showToast('Please fill in address, city, and state first', 'error');
        return;
    }

    const fullAddress = `${address}, ${city}, ${state}`;

    // This would require Google Geocoding API
    // For now, users need to manually enter coordinates
    showToast('Please enter latitude and longitude manually', 'error');
}
