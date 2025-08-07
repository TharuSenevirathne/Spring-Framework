document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();
        console.log("🔍 Full Response data:", data);
        console.log("👉 data.data:", data.data);

        if (response.ok) {
            alert('Login successful!');

            // Extract role from the response
            const role = data.data.role;
            console.log("✅ Role:", role);

            // Store the token and role in localStorage
            const token = data.data.accessToken;
            localStorage.setItem("token", token);
            localStorage.setItem("role", role);

            // Redirect based on role
            if (role === 'ADMIN') {
                window.location.href = 'dashboard.html';
            } else if (role === 'USER') {
                window.location.href = 'UserDashboard.html';
            } else {
                alert('Unknown role: ' + role);
            }
        } else {
            alert('Login failed: ' + (data.message || 'Error'));
        }
    } catch (error) {
        console.error('Login error:', error);
        alert('Something went wrong');
    }
});
