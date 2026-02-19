document.addEventListener('DOMContentLoaded', function () {

    // Screen Default
    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');

    // Document
    const formLogin = document.getElementById('form-login');
    const formRegister = document.getElementById('form-register');
    const inputEmail = document.getElementById('input-login-email');
    const inputPassword = document.getElementById('input-login-password');
    const buttonEye = document.getElementById('login-button-eye');
    const inputCheckbox = document.getElementById('input-login-checkbox');
    const submit = document.getElementById('input-login-submit');
    const buttonRegisterRedirect = document.getElementById('button-register-redirect');

    // Components
    buttonRegisterRedirect.addEventListener('click', () => {
        console.log('Redirect Clicado!');
        showDisplay(false, formLogin);
        showDisplay(true, formRegister);
    })

    buttonEye.addEventListener('click', () => {
        inputPassword.type = inputPassword.type === "password" ? "text" : "password";
        buttonEye.style.backgroundImage = inputPassword.type === "password"
        ? 'url("../img/eye.svg")'
        : 'url("../img/eye-off.svg")';
    })

    // API
    formLogin.addEventListener('submit', function (evento) {
        evento.preventDefault();
        processRecord();
    })

    function collectData() {
        const user = {
            email: inputEmail.value.trim(),
            password: inputPassword.value.trim(),
            rememberMe: inputCheckbox.checked
        }
        return user;
    }

    function processRecord() {
        const user = collectData();
        submit.disabled = true;
        submit.value = 'Carregando...';
        loadAPI(user);
    }

    async function loadAPI(user) {
        const url = '/auth/login';
        try {

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            const apiData = await response.json();

            if (Object.keys(apiData).length == 2) {
                const token = apiData.tokenAcess;
                const tokenRefresh = apiData.tokenRefresh;
                setToken(token);
                setTokenRefresh(tokenRefresh);
            } else {
                const token = apiData.tokenAcess;
                setToken(token);
            }
            
            setUserName();
            formLogin.reset();
            
            setTimeout(() => {
                window.location.href = '/posts.html';
            }, 3000);

        } catch (error) {
            console.error('Erro de Login:', 'Erro');
            loaderShow(false, loader, overlay);
        }
        finally {
            setUserName();
            setTimeout(() => {                
                submit.disabled = false;
                submit.value = 'Entrar';
            }, 3000)
        }
    }
})
