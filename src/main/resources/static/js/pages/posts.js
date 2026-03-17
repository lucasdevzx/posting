document.addEventListener('DOMContentLoaded', function () {

    // Screen Default
    const body = document.getElementById('body');
    const asideButtonSearch = document.getElementById('aside-button-search');
    const asideButtonHome = document.getElementById('aside-button-home');
    const asideButtonHomeContainer = document.getElementById('aside-button-home-container');
    const asideButtonHomeImg = document.getElementById('aside-button-home-img');

    const bounceLogin = document.getElementById('bounce-login');
    const bounceProfile = document.getElementById('bounce-profile');

    // Screen Effects
    const overlay = document.getElementById('overlay');
    const loader = document.getElementById('loader');
    const logoLoading = document.getElementById('logo-loading');

    // Modal
    const modalPost = document.getElementById('modal-post');
    const modalPositionCenter = document.getElementById('modal-position-center');
    const modalInfo = document.getElementById('modal-info');

    const postTitle = document.getElementById('post-title');
    const formPostUpdate = document.getElementById('post-update');
    const formPostTitleUpdate = document.getElementById('post-title-update');
    const formPostDescriptionUpdate = document.getElementById('post-description-update');
    const formPostCategoryUpdate = document.getElementById('post-category-update');
    const formPostSubmitUpdate = document.getElementById('post-submit-update');

    // Document
    const containerPosts = document.getElementById('container-posts');
    const buttonLinkCreatePost = document.getElementById('button-link-create-post');
    const textPostNotFound = document.getElementById('text-post-notfound');

    const buttonProfilePost = document.getElementById('button-profile-post');

    const buttonEditPost = document.getElementById('button-edit-post');
    const buttonDeletePost = document.getElementById('button-delete-post');

    const buttonDeletePostConfirm = document.getElementById('delete-post-button-confirm');
    const buttonDeletePostCancel = document.getElementById('delete-post-button-cancel');
    const buttonsMessage = document.getElementById('confirm-message-delete');
    const confirmButtons = document.getElementById('confirm-buttons-delete');

    const formPostCategoryCreate = document.getElementById('post-category-create');


    // Call
    sessionStorage.clear();
    showBounceAuth(bounceLogin, bounceProfile);
    asideButtonsSelected();

    function asideButtonsSelected() {
        asideButtonHomeContainer.classList.add('aside-links-selected');
    }

    // Listeners
    function infoPostPermission(canEdit, canDelete) {
        if (canEdit === 'true') {
            buttonEditPost.classList.remove('display-invisible');
            buttonEditPost.classList.add('display-visible');
        } else {
            buttonEditPost.classList.remove('display-visible');
            buttonEditPost.classList.add('display-invisible');
        }
        if (canDelete === 'true') {
            buttonDeletePost.classList.remove('display-invisible');
            buttonDeletePost.classList.add('display-visible');
        } else {
            buttonDeletePost.classList.remove('display-visible');
            buttonDeletePost.classList.add('display-invisible');
        }
    }

    // API

    // Get All
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
            const apiData = await response.json();
            const responseMessage = postExceptionStatus(response);
            if (!response.ok) {
                showTextException(responseMessage, textPostNotFound);
                loaderShow(false, loader, overlay);
            }

            showDisplay(true, logoLoading)
            setTimeout(() => {
                showDisplay(false, logoLoading);
                apiData.content.forEach(post => {
                    createCard(post);
                });
            }, 2000)

        } catch (e) {
            throw new Error("Error");
        }
    }

    // Delete by Id
    async function deletePost(postId) {
        try {
            const url = `/posts/${postId}`
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            setTimeout(() => {
                loaderShow(false, loader, overlay);
            }, 3000);
            setTimeout(() => {
                window.location.href = 'posts.html';
            }, 3000);


            /* Refatoração Futura
            setTimeout(() => {
                const responseMessage = postExceptionStatus(response);
                showModal(false, modalAlertConfirm);
                showModal(true, modalAlert);
                showTextException(responseMessage, textAlert);
            }, 3000);
             */

        } catch (o) {
            throw new Error("Erro ao Deletar");
        }
    }

    // HTML GENERATION

    //CARD
    function createCard(post) {

        // Card
        const card = document.createElement('div');
        const topCard = document.createElement('div');
        const midCard = document.createElement('div');

        // Card Infos
        const title = document.createElement('h2');
        const authorName = document.createElement("h1");
        const description = document.createElement('p');
        const createdAt = document.createElement('p');
        const category = document.createElement('p');
        const info = document.createElement('a');
        const infoImage = document.createElement('img');
        const profile = document.createElement('div')
        const date = timeAgo(post.date);

        // Wrapper
        authorName.textContent = post.author.name;
        title.textContent = post.title;
        description.textContent = post.description;
        createdAt.textContent = "•    " + date;
        category.textContent = "•    " + post.category;

        // DataSet
        const id = post.id;
        const authorId = post.authorId;
        const canEdit = post.permissions.canEdit;
        const canDelete = post.permissions.canDelete;
        const isOwner = post.permissions.isOwner;

        card.dataset.id = id;
        card.dataset.authorId = authorId;
        card.dataset.canEdit = canEdit;
        card.dataset.canDelete = canDelete;
        card.dataset.isOwner = isOwner;

        // Class
        card.classList.add('card');
        card.classList.add('selection');
        card.classList.add('selection-border');
        topCard.classList.add('top-card');
        midCard.classList.add('mid-card');

        profile.classList.add('avatar');
        profile.textContent = getInitials(post.author.name);
        profile.style.backgroundColor = stringToColor(post.author.name);

        authorName.classList.add('author');
        title.classList.add('title');
        description.classList.add('description');
        createdAt.classList.add('date');
        category.classList.add('category');

        info.classList.add('info', 'selection');
        info.title = "Informações"
        infoImage.src = "img/ellipsis-vertical.svg";

        // Append
        info.append(infoImage);
        topCard.append(authorName, createdAt, category);
        midCard.append(title, description);
        card.append(profile ,topCard, midCard, info);
        containerPosts.append(card);

        setTimeout(() => {
            loaderShow(false, loader, overlay);
        }, 1000);


        // Listeners
        info.addEventListener('click', () => {
            card.classList.remove('selection');
        })

        modalInfo.addEventListener('mouseenter', () => {
            card.classList.remove('selection');
        })

        modalInfo.addEventListener('mouseleave', () => {
            card.classList.add('selection');
        })


        info.addEventListener('click', (e) => {
            e.stopPropagation();

            // Zera o z-index de todos os cards
            document.querySelectorAll('.card').forEach(c => c.style.zIndex = 'auto');

            // Traz o card atual para frente de tudo (evitar erro de stacking no modal)
            card.style.zIndex = "1000";

            showDisplay(false, buttonsMessage);
            showDisplay(false, confirmButtons);

            card.append(modalInfo);
            modalInfo.classList.remove('closing');
            modalInfo.classList.add('dropdown');
            showDisplay(true, modalInfo);

            buttonEditPost.addEventListener('click', () => {
                setUiMode('post-update-view');
                formPostUpdate.classList.remove('display-invisible');

                postTitle.innerText = 'Editar Postagem';
                formPostTitleUpdate.value = post.title;
                formPostDescriptionUpdate.value = post.description;
                formPostCategoryUpdate.options[0].text = post.category;
                sessionStorage.setItem("post", JSON.stringify(post));

                showDisplay(false, modalInfo);
                showDisplay(false, buttonsMessage);
                showDisplay(false, confirmButtons);
                card.style.zIndex = "998";
                showDisplay(true, modalPositionCenter);
                showDisplay(true, modalPost);
                modalPost.classList.remove('closing');
                modalPost.classList.add('dropdown');
            })

            const canEdit = card.dataset.canEdit;
            const canDelete = card.dataset.canDelete;
            infoPostPermission(canEdit, canDelete);

            buttonDeletePost.onclick = () => {
                showDisplay(true, buttonsMessage);
                showDisplay(true, confirmButtons);

            }

            buttonDeletePostConfirm.onclick = () => {
                const postId = card.dataset.id;
                loaderShow(true, loader, overlay);
                deletePost(postId);
            }

            buttonDeletePostCancel.onclick = () => {
                showDisplay(false, buttonsMessage);
                showDisplay(false, confirmButtons);
            }
        })

        modalInfo.addEventListener('click', (e) => {
            e.stopPropagation();
        } )

        document.addEventListener('click', () => {
            modalInfo.classList.add('closing');
            modalInfo.addEventListener('animationend', () =>{
                modalInfo.classList.remove('dropdown', 'closing');
                showDisplay(false, modalInfo);
                showDisplay(false, buttonsMessage);
                showDisplay(false, confirmButtons);

                // Devolve o z-index do card ao normal
                const activeCard = modalInfo.closest('.card');
                if (activeCard) {
                    activeCard.style.zIndex = "auto";
                }
            }, {once:true});
        })
    }
})
