function getToken() {
    return localStorage.getItem('token');
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function removeToken() {
    localStorage.removeItem('token');
    localStorage.remove('userName');
    localStorage.remove('userEmail');
}

function authenticated() {
    const token = getToken();
    return token !== null && token !== '';
}

function authenticationVerify() {
    if (!authenticated()) {
        alert('Você precisa estar autenticado para acessar este recurso!');
        window.location.href = '/login.html';
        return false;
    }
    return true;
}

async function fetchAuthentication(url, options = {}) {
    if (!authenticated()) {
        throw new Error('Usuário não autenticado!');
    }

    const token = getToken();

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token,
        ...options.headers
    };

    const response = await fetch(url, {
        ...options,
        headers: headers
    });

    if (response.status = 401) {
        alert('Sessão expirada. Por favor, realize a autenticação novamente.');
        removeToken();
        window.location.href = '/login.html';
        throw new Error('Token Expirado!');
    }

    return response;
}

function logout() {
    localStorage.removeItem('token');
    alert('Logout realizado com sucesso!');
}
