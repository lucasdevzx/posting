document.addEventListener('DOMContentLoaded', function () {

    // Screen Default
    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');

    // Document
    const formLogin = document.getElementById('form-login');
    const formRegister = document.getElementById('form-register');
    const inputName = document.getElementById('input-register-name');
    const inputEmail = document.getElementById('input-register-email');
    const inputPassword = document.getElementById('input-register-password');
    const buttonEye = document.getElementById('register-button-eye');
    const buttonEyeConfirm = document.getElementById('register-button-confirm-eye');
    const inputPasswordConfirm = document.getElementById('input-register-password-confirm');
    const btnSubmit = document.getElementById('input-register-submit');
    const buttonLoginRedirect = document.getElementById('button-login-redirect');

    // Components
    buttonLoginRedirect.addEventListener('click', () => {
        console.log('Botao Login Clicado!')
        showDisplay(false, formRegister);
        showDisplay(true, formLogin);
    })

    buttonEye.addEventListener('click', () => {
        inputPassword.type = inputPassword.type === "password" ? "text" : "password";
        buttonEye.style.backgroundImage = inputPassword.type === "password"
        ? 'url("../img/eye.svg")'
        : 'url("../img/eye-off.svg")';
    })
    buttonEyeConfirm.addEventListener('click', () => {
        inputPasswordConfirm.type = inputPasswordConfirm.type === "password" ? "text" : "password";
        buttonEyeConfirm.style.backgroundImage = inputPasswordConfirm.type === "password"
        ? 'url("../img/eye.svg")'
        : 'url("../img/eye-off.svg")';
    })

    inputPasswordConfirm.addEventListener('focusout', () => {
        if (inputPassword.value !== inputPasswordConfirm.value) {
            inputPassword.classList.add('border-incorret')
            inputPasswordConfirm.classList.add('border-incorret');
        } else {
            inputPassword.classList.remove('border-incorret')
            inputPasswordConfirm.classList.remove('border-incorret');
        }
    })

    // API
    formRegister.addEventListener('submit', function (evento) {
        evento.preventDefault();
        processarRegistro();
    });

    function coletarDados() {

        // Criar um objeto JavaScript
        const user = {
            name: inputName.value.trim(),
            email: inputEmail.value.trim(),
            password: inputPassword.value
        }
        return user;
    }

    function processarRegistro() {
        const user = coletarDados();
        btnSubmit.disabled = true;
        btnSubmit.value = 'Carregando...';
        enviarParaAPI(user);
    }

    async function enviarParaAPI(user) {
        const url = '/auth/register';
        try {
            // await "espera" a Promise resolver (similar a um bloqueio)
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user) // Transforma objeto JS em JSON
            });

            if (!response.ok) {
                throw new Error('Erro HTTP:' + response.status);
            }

            formRegister.reset();
            setTimeout(() => {
                window.location.href = '/auth.html';
            }, 2000);
        } catch (error) {
            console.error('Erro', error);
            loaderShow(false, loader, overlay);
        } finally {
            setTimeout(() => {                
                btnSubmit.disabled = false;
                btnSubmit.value = 'Criar Conta';
            }, 3000)
        }
    }

});
