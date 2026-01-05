document.addEventListener('DOMContentLoaded', function () {

    const containerPosts = document.getElementById('container-posts');
    const bounceLogin = document.getElementById('bounce-login');
    const bounceProfile = document.getElementById('bounce-profile');
    const logoutProfile = document.getElementById('profile-logout');

    const buttonShowModalCreate = document.getElementById('button-show-modal-create');
    const formPost = document.getElementById('form-post');
    const inputTitle = document.getElementById('post-title');
    const inputDescription = document.getElementById('post-description');
    const submit = document.getElementById('post-submit');
    const message = document.getElementById('login-message');

    const modalPost = document.getElementById('modal-create-post');
    const buttonExitModalCreate = document.getElementById('button-exit-modal-create');
    const overlayPost = document.getElementById('overlay-post');
    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');

    // <!---------COMPONENTS---------->

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

    buttonShowModalCreate.addEventListener('click', () => {
        overlayPost.classList.add('overlay-post');
        modalPost.classList.remove('modal-overlay');
        modalPost.classList.add('modal-overlay-active');
    })

    buttonExitModalCreate.addEventListener('click', () => {
        overlayPost.classList.remove('overlay-post');
        modalPost.classList.remove('modal-overlay-active');
        modalPost.classList.add('modal-overlay');
    })

    function submitButton() {
        const formSubmitDiv = submit.parentElement; // Pega o elemento pai do elemento (div)
        if (!inputTitle.value || !inputDescription.value) {
            submit.disabled = true;
            formSubmitDiv.classList.remove('animation-scale-hover');
            formSubmitDiv.classList.remove('form-submit-hover');
        } else {
            submit.disabled = false;
            formSubmitDiv.classList.add('animation-scale-hover');
            formSubmitDiv.classList.add('form-submit-hover');
        }
    }
    inputTitle.addEventListener('input', submitButton);
    inputDescription.addEventListener('input', submitButton);
    // Recarrega a função criando loop para alterações
    submitButton();

    showBounceAuth();
    function showBounceAuth() {
        const token = getToken();
        if (token) {
            console.log(token);
            bounceLogin.classList.remove('bounce-overlay-active');
            bounceLogin.classList.add('bounce-overlay');
            bounceProfile.classList.remove('bounce-overlay');
            bounceProfile.classList.add('bounce-overlay-active');
        } else {
            bounceLogin.classList.remove('bounce-overlay');
            bounceLogin.classList.add('bounce-overlay-active');
            bounceProfile.classList.remove('bounce-overlay-active');
            bounceProfile.classList.add('bounce-overlay');
        }
    }

    // <!---------API---------->

    const token = getToken();

    loadAPI();
    async function loadAPI() {
        const url = '/posts?page=0&size=10';

        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })

            // <!---------DOM Manipulation---------->

            // <!---------GET ALL---------->
            const apiData = await response.json();
            apiData.content.forEach(post => {
                const card = document.createElement('div');
                const title = document.createElement('h2');
                const authorName = document.createElement("h1");
                const description = document.createElement('p');
                const category = document.createElement('p');
                const hr = document.createElement('hr');
                const hrName = document.createElement('hr');
                const profile = document.createElement('img')


                authorName.textContent = post.author.name;
                title.textContent = post.title;
                description.textContent = post.description;
                category.textContent = post.category;

                card.classList.add('card');
                card.classList.add('selection');
                profile.src = "img/circle-user-round.svg";
                profile.classList.add('profile');
                authorName.classList.add('author');
                title.classList.add('title');
                description.classList.add('description');
                category.classList.add('category');
                hr.classList.add('hr');
                hrName.classList.add('hr-name');

                card.dataset.id = post.id;
                card.dataset.authorId = post.authorId;
                card.dataset.canEdit = post.permissions.canEdit;
                card.dataset.canDelete = post.permissions.canDelete;
                card.dataset.isOwner = post.permissions.isOwner;

                card.append(profile, authorName, hrName, title, description, category);
                containerPosts.append(card, hr);

                card.addEventListener('click', () => {
                    window.location.href = '/index.html';
                })

            });

            // <!---------CREATE ME---------->
            createPost();
            function createPost() {

                formPost.addEventListener('submit', function (event) {
                    event.preventDefault();
                    loaderShow(true);
                    processRecord();

                })

                function collectData() {
                    const post = {
                        name: inputTitle.value.trim(),
                        description: inputDescription.value.trim()
                    }
                    return post;
                }

                function processRecord() {
                    const post = collectData();
                    submit.disabled = true;
                    submit.value = 'Carregando...';
                    fetchCreate(post);

                }

                async function fetchCreate(post) {

                    const url = '/posts';
                    const response = await fetch(url, {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(post)
                    });

                    console.log('Sent Data');

                    const apiData = await response.json();
                    console.log('API DATA: ' + apiData);
                    formPost.reset();

                    setTimeout(() => {
                        submit.value = 'Enviar';
                    }, 3000);

                    setTimeout(() => {
                        loaderShow(false);
                    }, 3000);
                }
            }

        } catch (e) {
            throw new Error("Error");

        }

    }

})
