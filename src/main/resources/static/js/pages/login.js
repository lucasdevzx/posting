document.addEventListener('DOMContentLoaded', function () {

    // Screen Default
    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');

    // Document
    const modalLogin = document.getElementById('modal-login');
    const modalRegister = document.getElementById('modal-register');
    const form = document.getElementById('form-login');
    const inputEmail = document.getElementById('login-email');
    const inputPassword = document.getElementById('login-password');
    const submit = document.getElementById('login-submit');
    const message = document.getElementById('login-message');
    const buttonRegisterRedirect = document.getElementById('button-register-redirect');

    // Components
    buttonRegisterRedirect.addEventListener('click', () => {
        showModal(false, modalLogin);
        showModal(true, modalRegister);
    })

    // API
    form.addEventListener('submit', function (evento) {
        evento.preventDefault();
        console.log('Send Form.');
        loaderShow(true, loader, overlay);
        processRecord();
    })

    function collectData() {
        const user = {
            email: inputEmail.value.trim(),
            password: inputPassword.value.trim()
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
            const token = apiData.token;

            if (!token) {
                throw new Error('Token não recebido.');
            }

            setToken(token);
            form.reset();
            setTimeout(() => {
                window.location.href = '/index.html';
            }, 3000);
            setTimeout(() => {
                loaderShow(false, loader, overlay);
            }, 3000);

        } catch (error) {
            console.error('Erro de Login:', 'Erro');
            loaderShow(false, loader, overlay);
        }
        finally {
            setUserName();
            submit.disabled = false;
            submit.value = 'Login';
        }
    }
})
