document.addEventListener('DOMContentLoaded', function() {

    const bounceLogin = document.getElementById('bounce-login');
    const bounceProfile = document.getElementById('bounce-profile');
    const logoutProfile = document.getElementById('profile-logout');

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

    logoutProfile.addEventListener('click', (event) => {
        console.log('CLICK NO LOGOUT');
        event.preventDefault();
        logout();
        showBounceAuth();
    });

})
