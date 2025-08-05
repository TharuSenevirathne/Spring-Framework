// Dashboard JavaScript functionality
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    if (!checkAuth()) {
        return;
    }

    // Get current user from sessionStorage
    const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));

    if (currentUser) {
        // Update user info in dashboard
        document.getElementById('welcomeUser').textContent = `Welcome, ${currentUser.name}!`;
        document.getElementById('userAvatar').textContent = currentUser.name.charAt(0).toUpperCase();
    }

    // Logout button event listener
    document.getElementById('logoutBtn').addEventListener('click', logout);

    // Dashboard sidebar navigation
    document.querySelectorAll('.sidebar .nav-link').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();

            // Update active link
            document.querySelectorAll('.sidebar .nav-link').forEach(l => l.classList.remove('active'));
            link.classList.add('active');

            // Show corresponding section
            const section = link.dataset.section;
            document.querySelectorAll('.dashboard-section').forEach(s => s.style.display = 'none');
            const targetSection = document.getElementById(section + 'Section');
            if (targetSection) {
                targetSection.style.display = 'block';
                targetSection.classList.add('fade-in');
            }
        });
    });

    // Quick actions functionality
    initializeQuickActions();

    // Initialize real-time updates
    initializeRealTimeUpdates();

    // Initialize settings functionality
    initializeSettings();
});

// Initialize quick action buttons
function initializeQuickActions() {
    // Add User button
    const addUserBtn = document.querySelector('.btn-gradient');
    if (addUserBtn && addUserBtn.textContent.includes('Add User')) {
        addUserBtn.addEventListener('click', () => {
            showAlert('Add User functionality would be implemented here.', 'info');
        });
    }

    // Export Data button
    const exportBtn = document.querySelector('.btn-outline-primary');
    if (exportBtn && exportBtn.textContent.includes('Export')) {
        exportBtn.addEventListener('click', () => {
            showAlert('Exporting data... This would download a CSV file.', 'success');
        });
    }

    // Refresh button
    const refreshBtn = document.querySelector('.btn-outline-success');
    if (refreshBtn && refreshBtn.textContent.includes('Refresh')) {
        refreshBtn.addEventListener('click', () => {
            showAlert('Dashboard refreshed!', 'success');
            updateDashboardStats();
        });
    }
}

// Update dashboard statistics (simulate real-time data)
function updateDashboardStats() {
    const stats = [
        { selector: '.stats-card h3', values: ['1,234', '567', '89', '$12,450'] },
    ];

    // Animate counter updates
    const statCards = document.querySelectorAll('.stats-card h3');
    statCards.forEach((card, index) => {
        const randomValue = Math.floor(Math.random() * 1000) + 500;
        let currentValue = parseInt(card.textContent.replace(/[^\d]/g, '')) || 0;

        animateCounter(card, currentValue, randomValue, index === 3 ? '$' : '');
    });
}

// Animate counter
function animateCounter(element, start, end, prefix = '') {
    let current = start;
    const increment = (end - start) / 50;
    const timer = setInterval(() => {
        current += increment;
        if ((increment > 0 && current >= end) || (increment < 0 && current <= end)) {
            current = end;
            clearInterval(timer);
        }
        element.textContent = prefix + Math.floor(current).toLocaleString();
    }, 20);
}

// Initialize real-time updates
function initializeRealTimeUpdates() {
    // Update time stamps every minute
    setInterval(() => {
        const timeElements = document.querySelectorAll('small.text-muted');
        timeElements.forEach(el => {
            if (el.textContent.includes('minutes ago')) {
                const minutes = parseInt(el.textContent.match(/\d+/)[0]) + 1;
                el.textContent = `${minutes} minutes ago`;
            } else if (el.textContent.includes('minute ago')) {
                el.textContent = '2 minutes ago';
            }
        });
    }, 60000);

    // Simulate new activity updates
    setInterval(() => {
        addNewActivity();
    }, 30000); // Add new activity every 30 seconds
}

// Add new activity to the recent activity list
function addNewActivity() {
    const activities = [
        { name: 'New User', username: 'NU', action: 'registered', time: 'Just now' },
        { name: 'System Admin', username: 'SA', action: 'updated system', time: 'Just now' },
        { name: 'Data Manager', username: 'DM', action: 'exported report', time: 'Just now' }
    ];

    const randomActivity = activities[Math.floor(Math.random() * activities.length)];
    const activityList = document.querySelector('.list-group-flush');

    if (activityList) {
        const newActivity = document.createElement('div');
        newActivity.className = 'list-group-item border-0 px-0';
        newActivity.innerHTML = `
            <div class="d-flex align-items-center">
                <div class="user-avatar me-3" style="width: 35px; height: 35px; font-size: 14px;">${randomActivity.username}</div>
                <div>
                    <p class="mb-1 fw-bold">${randomActivity.name} ${randomActivity.action}</p>
                    <small class="text-muted">${randomActivity.time}</small>
                </div>
            </div>
        `;

        // Add to top of list
        activityList.insertBefore(newActivity, activityList.firstChild);

        // Remove last item if more than 5 activities
        if (activityList.children.length > 5) {
            activityList.removeChild(activityList.lastChild);
        }

        // Add fade-in animation
        newActivity.style.opacity = '0';
        setTimeout(() => {
            newActivity.style.opacity = '1';
            newActivity.style.transition = 'opacity 0.5s ease-in';
        }, 100);
    }
}

// Initialize settings functionality
function initializeSettings() {
    // Handle settings form submission
    const settingsForm = document.querySelector('#settingsSection form');
    if (settingsForm) {
        settingsForm.addEventListener('submit', (e) => {
            e.preventDefault();
            showAlert('Profile settings updated successfully!', 'success');
        });
    }

    // Handle switch toggles
    const switches = document.querySelectorAll('.form-check-input');
    switches.forEach(switchEl => {
        switchEl.addEventListener('change', (e) => {
            const label = e.target.nextElementSibling.textContent.trim();
            const status = e.target.checked ? 'enabled' : 'disabled';
            showAlert(`${label} ${status}`, 'info');

            // Dark mode toggle
            if (e.target.id === 'darkMode') {
                toggleDarkMode(e.target.checked);
            }
        });
    });

    // User management table actions
    const actionButtons = document.querySelectorAll('#usersSection .btn');
    actionButtons.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const action = btn.textContent.trim();
            const row = btn.closest('tr');
            const username = row.cells[1].textContent;

            if (action === 'Edit') {
                showAlert(`Edit functionality for ${username} would be implemented here.`, 'info');
            } else if (action === 'Delete') {
                if (confirm(`Are you sure you want to delete user ${username}?`)) {
                    showAlert(`User ${username} deleted successfully.`, 'success');
                    row.remove();
                }
            }
        });
    });
}

// Toggle dark mode
function toggleDarkMode(enabled) {
    if (enabled) {
        document.body.classList.add('dark-mode');
        // You would add dark mode CSS classes here
        showAlert('Dark mode enabled', 'info');
    } else {
        document.body.classList.remove('dark-mode');
        showAlert('Light mode enabled', 'info');
    }
}

// Search functionality (if search input exists)
function initializeSearch() {
    const searchInput = document.querySelector('input[type="search"]');
    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            const searchTerm = e.target.value.toLowerCase();
            filterTableRows(searchTerm);
        });
    }
}

// Filter table rows based on search term
function filterTableRows(searchTerm) {
    const tableRows = document.querySelectorAll('#usersSection tbody tr');
    tableRows.forEach(row => {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(searchTerm) ? '' : 'none';
    });
}

// Export data functionality
function exportToCSV() {
    const data = [
        ['Name', 'Username', 'Status', 'Last Login'],
        ['admin', 'senez', 'Active', 'Today, 10:30 AM'],
        ['John Doe', 'john', 'Inactive', 'Yesterday, 2:15 PM']
    ];

    const csvContent = data.map(row => row.join(',')).join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = 'users_export.csv';
    a.click();

    window.URL.revokeObjectURL(url);
}

// Initialize tooltips (if Bootstrap tooltips are needed)
function initializeTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}