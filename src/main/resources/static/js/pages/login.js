document.addEventListener('DOMContentLoaded', function () {

    const form = document.getElementById('form-login');
    const inputEmail = document.getElementById('login-email');
    const inputPassword = document.getElementById('login-password');
    const submit = document.getElementById('login-submit');
    const message = document.getElementById('login-message');

    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');

    // <!---------COMPONENTS---------->

    function submitButton() {
        const formSubmitDiv = submit.parentElement; // Pega o elemento pai do elemento (div)
        if (!inputEmail.value || !inputPassword.value) {
            submit.disabled = true;
            formSubmitDiv.classList.remove('animation-scale-hover');
            formSubmitDiv.classList.remove('form-submit-hover');
        } else {
            submit.disabled = false;
            formSubmitDiv.classList.add('animation-scale-hover');
            formSubmitDiv.classList.add('form-submit-hover');
        }
    }

    inputEmail.addEventListener('input', submitButton);
    inputPassword.addEventListener('input', submitButton);
    // Recarrega a função criando loop para alterações
    submitButton();

    function loaderShow(boolean) {
        if (boolean === true) {
            loader.classList.remove('load-overlay');
            loader.classList.add('loader-overlay-active');
            overlay.classList.add('overlay');

        } else {
            loader.classList.remove('loader-overlay-active');
            loader.classList.add('load-overlay');
            overlay.classList.remove('overlay');
        }
    }

    // <!---------API---------->

    form.addEventListener('submit', function (evento) {
        evento.preventDefault();
        console.log('Send Form.');
        loaderShow(true);
        processRecord();
    })

    function collectData() {
        const user = {
            email: inputEmail.value.trim(),
            password: inputPassword.value.trim()
        }
        return user;
    }

    function displayMessage(text, type) {
        message.textContent = text;
        message.className = 'message ' + type;
        message.style.display = 'block';
        setTimeout(function () {
            message.style.display = 'none';
        }, 5000);
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

            console.log('Sent Data.');

            switch (response.status) {
                case 400:
                    throw new Error(displayMessage('Preencha os Campos Corretamente!', 'Campos Invalidos'));
                    break;

                case 401:
                    throw new Error(displayMessage('Usuário não autenticado'));
                    break;
                default:
                    break;
            }


            const apiData = await processarResposta(response);


            console.log('API Data Received: ' + apiData);

            const token = apiData.token;

            if (!token) throw new Error('Token não recebido.');

            setToken(token);

            console.log('Token Received: ' + token);

            if (apiData.user) {
                localStorage.setItem('userEmail', apiData.user.email); R
            }

            //displayMessage('Login realizado com sucesso!', response.status);
            form.reset();

            setTimeout(() => {
                window.location.href = '/index.html';
            }, 3000);
            setTimeout(() => {
                loaderShow(false);
            }, 3000);

        } catch (error) {
            console.error('Erro de Login:', 'Erro');
            loaderShow(false);
        }
        finally {
            submit.disabled = false;
            submit.value = 'Login';
        }
    }
})
