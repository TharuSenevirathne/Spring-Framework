document.getElementById("logoutBtn").addEventListener("click", function (e) {
    e.preventDefault();

    // 🧹 Remove JWT token and role from Local Storage
    localStorage.removeItem("token");
    localStorage.removeItem("role");

    // 🔁 Redirect to login page using absolute path
    window.location.href = "/Spring-Framework/12-Auth-FrontEnd/signin.html";
});