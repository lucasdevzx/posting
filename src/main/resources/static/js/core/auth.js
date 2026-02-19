function getToken() {
    const token = localStorage.getItem('token');
    const expired = localStorage.getItem('token_expired');
    if (!token || Date.now() > expired) {
        localStorage.removeItem('token');
        localStorage.removeItem('token_expired');
        console.log('Token Expirado!');
    }
    return token;
}

function setToken(token) {
    const expiresIn = Date.now() + 600000; // 10 Minutes
    localStorage.setItem('token', token);
    localStorage.setItem('token_expired', expiresIn);
}

function setTokenRefresh(tokenRefresh) {
    localStorage.setItem('tokenRefresh', tokenRefresh);
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
        window.location.href = 'auth.html'
        return false;
    }
    return true;
}

function logout() {
    localStorage.removeItem('token');
    console.log('token removido');
}
