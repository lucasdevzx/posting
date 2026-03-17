document.addEventListener('DOMContentLoaded', function () {

    // Screen Default
    const asideButtonCreatePost = document.getElementById('aside-button-create');
    const asideButtonSearch = document.getElementById('aside-button-search');
    const asideButtonHome = document.getElementById('aside-button-home');
    const asideButtonHomeContainer = document.getElementById('aside-button-home-container');
    const asideButtonHomeImg = document.getElementById('aside-button-home-img');

    // Modal
    const buttonCloseModal = document.getElementById('button-close-post')
    const modalPost = document.getElementById('modal-post');
    const modalPositionCenter = document.getElementById('modal-position-center');
    //const modalAlertPost = document.getElementById('modal-alert-post');
    //const textAlertPost = document.getElementById('text-alert-post');

    // Document
    const postTitle = document.getElementById('post-title');
    const formPostCreate = document.getElementById('post-create');
    const formPostTitleCreate = document.getElementById('post-title-create');
    const formPostDescriptionCreate = document.getElementById('post-description-create');
    const formPostCategoryCreate = document.getElementById('post-category-create');
    const formPostSubmitCreate = document.getElementById('post-submit-create');
    
    const formPostUpdate = document.getElementById('post-update');
    const formPostTitleUpdate = document.getElementById('post-title-update');
    const formPostDescriptionUpdate = document.getElementById('post-description-update');
    const formPostCategoryUpdate = document.getElementById('post-category-update');
    const formPostSubmitUpdate = document.getElementById('post-submit-update');

    // Call
    //alertAuthentication(false, modalPosition, modalAlertPost, textAlertPost);

    // Listeners
    buttonCloseModal.addEventListener('click', () => {
        modalPost.classList.add('closing');
        modalPost.addEventListener('animationend', () =>{
            modalPost.classList.remove('dropdown', 'closing');
            showDisplay(false, modalPositionCenter);
            showDisplay(false, modalPost);
            showDisplay(false, formPostCreate);
            showDisplay(false, formPostUpdate);
        }, {once: true});
    })
    
    asideButtonCreatePost.addEventListener('click', () => {
        if (!authenticated()) {
            alertAuthentication(true, modalPositionCenter, modalAlert, textAlert);
        } else {
            setUiMode('post-create-view');
            showDisplay(true, modalPositionCenter);
            showDisplay(true, modalPost);
            postTitle.innerText = 'Publicar Postagem';
            formPostCreate.classList.remove('display-invisible');
            modalPost.classList.remove('closing');
            modalPost.classList.add('dropdown');
        }
    })

    formPostSubmitCreate.addEventListener('click', () => {
        fetchPosts();
    })
    formPostSubmitUpdate.addEventListener('click', () => {
        fetchPosts();
    })

    //Generate HTML
    loadCategorys();
    async function loadCategorys() {
        const categoryData = await fetchGetCategorys();

        formPostCategoryCreate.addEventListener('click', () => {
            categoryData.content.forEach(categorys => {
                const formPostCategoryOption = document.createElement('option');
                formPostCategoryOption.textContent = categorys.name;
                formPostCategoryCreate.append(formPostCategoryOption);
            })
        }, { once: true })
        
        formPostCategoryUpdate.addEventListener('click', () => {
            categoryData.content.forEach(categorys => {
                const formPostCategoryOption = document.createElement('option');
                formPostCategoryOption.textContent = categorys.name;
                formPostCategoryUpdate.append(formPostCategoryOption);
            })
        }, { once: true })
    }

    //API
    const token = getToken();
    function fetchPosts() {
        const uiMode = getUiMode();
        console.log(uiMode);

        formPostCreate.addEventListener('submit', function (event) {
            event.preventDefault();
            loaderShow(true, loader, overlay);
            processRecord();
        })

        formPostUpdate.addEventListener('submit', function (event) {
            event.preventDefault();
            loaderShow(true, loader, overlay);
            processRecord();
        })

        function collectData() {
            if (uiMode === 'post-create-view') {
                const post = {
                    name: formPostTitleCreate.value.trim(),
                    description: formPostDescriptionCreate.value.trim(),
                    category: formPostCategoryCreate.value.trim()
                }
                return post;
            }
            if (uiMode === 'post-update-view') {
                const post = {
                    name: formPostTitleUpdate.value.trim(),
                    description: formPostDescriptionUpdate.value.trim(),
                    category: formPostCategoryUpdate.value.trim()
                }
                return post;
            }
        }

        function processRecord() {
            const post = collectData();
            if (uiMode === 'post-create-view') {
                formPostSubmitCreate.disabled = true;
                formPostSubmitCreate.value = 'Carregando...';
                fetchCreate(post);
            }
            if (uiMode === 'post-update-view') {
                formPostSubmitUpdate.disabled = true;
                formPostSubmitUpdate.value = 'Carregando...';
                fetchUpdate(post);
            }
        }

        // CREATE
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
            console.log(response);
            formPostCreate.reset();

            setTimeout(() => {
                formPostSubmitCreate.value = 'Publicar';
            }, 3000);
            setTimeout(() => {
                loaderShow(false, loader, overlay);
            }, 3000);

            setTimeout(() => {
                const responseMessage = postExceptionStatus(response);
                showModal(true, modalPosition);
                showModal(true, modalAlertPost);
                showTextException(responseMessage, textAlertPost);
            }, 3000);
        }

        // UPDATE
        async function fetchUpdate(post) {
            const cached = sessionStorage.getItem("post");
            const postCache = JSON.parse(cached);
            const postId = postCache.id;
            const url = `/posts/${postId}`;
            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(post)
            })

            console.log('Sent Data');
            console.log(response);
            formPostCreate.reset();

            setTimeout(() => {
                formPostSubmitCreate.value = 'Publicar';
            }, 3000);
            setTimeout(() => {
                loaderShow(false, loader, overlay);
            }, 3000);

            setTimeout(() => {
                const responseMessage = postExceptionStatus(response);
                showModal(true, modalPosition);
                showModal(true, modalAlertPost);
                showTextException(responseMessage.PUT, textAlertPost);
            }, 3000);
        }

    }
})
