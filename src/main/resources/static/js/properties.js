// Properties page functionality
let allProperties = [];

// Load properties on page load
document.addEventListener('DOMContentLoaded', async () => {
    await loadProperties();
});

// Load properties
async function loadProperties(filters = {}) {
    const container = document.getElementById('propertiesContainer');
    container.innerHTML = '<div class="spinner"></div>';

    allProperties = await fetchProperties(filters);
    displayProperties(allProperties);
}

// Display properties
function displayProperties(properties) {
    const container = document.getElementById('propertiesContainer');

    if (properties.length === 0) {
        container.innerHTML = `
            <div style="grid-column: 1/-1; text-align: center; padding: 3rem;">
                <h3>No properties found</h3>
                <p>Try adjusting your filters or <a href="/add-property">add a new property</a></p>
            </div>
        `;
        return;
    }

    container.innerHTML = properties.map(property => createPropertyCard(property)).join('');
}

// Apply filters
function applyFilters() {
    const filters = {
        minPrice: document.getElementById('minPrice').value || null,
        maxPrice: document.getElementById('maxPrice').value || null,
        minBedrooms: document.getElementById('minBedrooms').value || null,
        maxBedrooms: document.getElementById('maxBedrooms').value || null,
        minBathrooms: document.getElementById('minBathrooms').value || null,
        city: document.getElementById('city').value || null,
        state: document.getElementById('state').value || null,
        propertyType: document.getElementById('propertyType').value || null,
        status: document.getElementById('status').value || null
    };

    loadProperties(filters);
}

// Clear filters
function clearFilters() {
    document.getElementById('minPrice').value = '';
    document.getElementById('maxPrice').value = '';
    document.getElementById('minBedrooms').value = '';
    document.getElementById('maxBedrooms').value = '';
    document.getElementById('minBathrooms').value = '';
    document.getElementById('city').value = '';
    document.getElementById('state').value = '';
    document.getElementById('propertyType').value = '';
    document.getElementById('status').value = '';

    loadProperties();
}

// Add enter key listener for filters
document.addEventListener('DOMContentLoaded', () => {
    const filterInputs = document.querySelectorAll('.filters-container input, .filters-container select');
    filterInputs.forEach(input => {
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                applyFilters();
            }
        });
    });
});
