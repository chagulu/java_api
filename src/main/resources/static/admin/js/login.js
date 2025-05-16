document.getElementById('adminLoginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const data = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    fetch('http://localhost:8080/api/admin/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (res.ok) {
            window.location.href = "/admin/dashboard";
        } else {
            document.getElementById('error-msg').innerText = "Invalid credentials!";
        }
    }).catch(err => {
        console.error(err);
        document.getElementById('error-msg').innerText = "Server error!";
    });
});

document.getElementById('adminLoginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const data = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    fetch('http://localhost:8080/api/admin/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (!res.ok) throw new Error("Invalid credentials");
        return res.json();
    })
    .then(data => {
        localStorage.setItem("adminToken", data.token);
        window.location.href = "/admin/dashboard";
    })
    .catch(err => {
        document.getElementById('error-msg').innerText = err.message;
    });
});
