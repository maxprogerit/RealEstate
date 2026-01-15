// Alerts page functionality
document.addEventListener('DOMContentLoaded', async () => {
    await loadUserAlerts();

    const form = document.getElementById('alertForm');
    form.addEventListener('submit', handleAlertSubmit);
});

async function loadUserAlerts() {
    const container = document.getElementById('alertsContainer');
    container.innerHTML = '<div class="spinner"></div>';

    const alerts = await getUserAlerts();

    if (alerts.length === 0) {
        container.innerHTML = `
            <div style="text-align: center; padding: 2rem; background: white; border-radius: 1rem;">
                <p>No alerts created yet. Create one above to get notified!</p>
            </div>
        `;
        return;
    }

    container.innerHTML = alerts.map(alert => createAlertCard(alert)).join('');
}

function createAlertCard(alert) {
    return `
        <div class="filters-container" style="margin-bottom: 1rem;" data-alert-id="${alert.id}">
            <div style="display: flex; justify-content: space-between; align-items: start;">
                <div>
                    <h4 style="margin-bottom: 0.5rem;">Alert #${alert.id}</h4>
                    <div style="display: flex; flex-wrap: wrap; gap: 0.5rem; margin-bottom: 0.5rem;">
                        ${alert.minPrice ? `<span class="feature-badge">Min: ${formatPrice(alert.minPrice)}</span>` : ''}
                        ${alert.maxPrice ? `<span class="feature-badge">Max: ${formatPrice(alert.maxPrice)}</span>` : ''}
                        ${alert.minBedrooms ? `<span class="feature-badge">Min Beds: ${alert.minBedrooms}</span>` : ''}
                        ${alert.maxBedrooms ? `<span class="feature-badge">Max Beds: ${alert.maxBedrooms}</span>` : ''}
                        ${alert.city ? `<span class="feature-badge">City: ${alert.city}</span>` : ''}
                        ${alert.state ? `<span class="feature-badge">State: ${alert.state}</span>` : ''}
                        ${alert.propertyType ? `<span class="feature-badge">${alert.propertyType}</span>` : ''}
                    </div>
                    <p style="color: var(--text-secondary); font-size: 0.875rem;">
                        Email: ${alert.userEmail} | 
                        Status: <span style="color: ${alert.active ? 'var(--secondary-color)' : 'var(--text-secondary)'};">
                            ${alert.active ? 'Active' : 'Inactive'}
                        </span>
                    </p>
                </div>
                <button class="btn btn-secondary" onclick="removeAlert(${alert.id})">
                    Delete
                </button>
            </div>
        </div>
    `;
}

async function handleAlertSubmit(e) {
    e.preventDefault();

    const alertData = {
        userEmail: document.getElementById('userEmail').value,
        minPrice: document.getElementById('alertMinPrice').value ?
            parseFloat(document.getElementById('alertMinPrice').value) : null,
        maxPrice: document.getElementById('alertMaxPrice').value ?
            parseFloat(document.getElementById('alertMaxPrice').value) : null,
        minBedrooms: document.getElementById('alertMinBedrooms').value ?
            parseInt(document.getElementById('alertMinBedrooms').value) : null,
        maxBedrooms: document.getElementById('alertMaxBedrooms').value ?
            parseInt(document.getElementById('alertMaxBedrooms').value) : null,
        city: document.getElementById('alertCity').value || null,
        state: document.getElementById('alertState').value || null,
        propertyType: document.getElementById('alertPropertyType').value || null,
        active: true
    };

    try {
        const submitBtn = e.target.querySelector('button[type="submit"]');
        submitBtn.disabled = true;
        submitBtn.textContent = 'Creating...';

        await createAlert(alertData);

        showToast('Alert created successfully!', 'success');

        // Reset form
        document.getElementById('alertForm').reset();

        // Reload alerts
        await loadUserAlerts();

        submitBtn.disabled = false;
        submitBtn.textContent = 'Create Alert';

    } catch (error) {
        console.error('Error creating alert:', error);
        showToast('Failed to create alert. Please try again.', 'error');

        const submitBtn = e.target.querySelector('button[type="submit"]');
        submitBtn.disabled = false;
        submitBtn.textContent = 'Create Alert';
    }
}

async function removeAlert(alertId) {
    if (!confirm('Are you sure you want to delete this alert?')) {
        return;
    }

    try {
        await deleteAlert(alertId);
        showToast('Alert deleted successfully!', 'success');

        // Remove the card from the DOM
        const card = document.querySelector(`[data-alert-id="${alertId}"]`);
        if (card) {
            card.style.animation = 'slideIn 0.3s ease-out reverse';
            setTimeout(() => {
                card.remove();

                // Check if there are no more alerts
                const container = document.getElementById('alertsContainer');
                if (container.children.length === 0) {
                    container.innerHTML = `
                        <div style="text-align: center; padding: 2rem; background: white; border-radius: 1rem;">
                            <p>No alerts created yet. Create one above to get notified!</p>
                        </div>
                    `;
                }
            }, 300);
        }
    } catch (error) {
        console.error('Error deleting alert:', error);
        showToast('Failed to delete alert', 'error');
    }
}
