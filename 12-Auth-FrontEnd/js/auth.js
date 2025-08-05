// In-memory user storage (simulating database)
let users = [
    { name: "admin", username: "senez", password: "123" },
    { name: "John Doe", username: "john", password: "password" }
];

// Get users from localStorage or use default
function getUsers() {
    const storedUsers = localStorage.getItem('users');
    if (storedUsers) {
        users = JSON.parse(storedUsers);
    }
    return users;
}

// Save users to localStorage
function saveUsers() {
    localStorage.setItem('users', JSON.stringify(users));
}

// Initialize users
getUsers();

// Show alert messages
function showAlert(message, type = 'success') {
    const alertContainer = document.getElementById('alertContainer');
    if (!alertContainer) return;

    const alertId = 'alert-' + Date.now();
    const alertHTML = `
        <div id="${alertId}" class="alert alert-${type} alert-custom alert-dismissible fade show" role="alert">
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'danger' ? 'exclamation-circle' : 'info-circle'} me-2"></i>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    alertContainer.insertAdjacentHTML('beforeend', alertHTML);

    // Auto-remove alert after 5 seconds
    setTimeout(() => {
        const alert = document.getElementById(alertId);
        if (alert) {
            alert.remove();
        }
    }, 5000);
}

// Show loading state
function showLoading(button, show = true) {
    const spinner = button.querySelector('.loading-spinner');
    const text = button.querySelector('.btn-text');

    if (show) {
        spinner.style.display = 'inline-block';
        text.style.opacity = '0.7';
        button.disabled = true;
    } else {
        spinner.style.display = 'none';
        text.style.opacity = '1';
        button.disabled = false;
    }
}

// Simulate AJAX request
function simulateAjaxRequest(data, endpoint) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (endpoint === 'signin') {
                const user = users.find(u =>
                    u.name === data.name &&
                    u.username === data.username &&
                    u.password === data.password
                );
                if (user) {
                    resolve({ success: true, user: user });
                } else {
                    reject({ success: false, message: 'Invalid credentials' });
                }
            } else if (endpoint === 'signup') {
                // Check if username already exists (case insensitive)
                const existingUser = users.find(u => u.username.toLowerCase() === data.username.toLowerCase());
                if (existingUser) {
                    reject({ success: false, message: 'Username already exists' });
                } else {
                    const newUser = {
                        name: data.username,
                        username: data.username,
                        password: data.password
                    };
                    users.push(newUser);
                    saveUsers(); // Save to localStorage
                    resolve({ success: true, user: newUser });
                }
            }
        }, 1500); // Simulate network delay
    });
}

// Check if user is authenticated
function checkAuth() {
    const currentUser = sessionStorage.getItem('currentUser');
    if (!currentUser && window.location.pathname.includes('dashboard.html')) {
        window.location.href = 'signin.html';
        return false;
    }
    return true;
}

// Logout function
function logout() {
    sessionStorage.removeItem('currentUser');
    showAlert('Logged out successfully!', 'info');
    setTimeout(() => {
        window.location.href = 'signin.html';
    }, 1000);
}