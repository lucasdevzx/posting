    export function bounceButton(bounceLogin, bounceProfile, token) {

        if (token) {
            bounceLogin.classList.remove('bounce-overlay-active');
            bounceLogin.classList.add('bounce-overlay');
            bounceProfile.classList.remove('bounce-overlay');
            bounceProfile.classList.add('bounce-overlay-active');
        }
    }
