function alertAuthentication(validate, modalPosition, modal, textAlert) {
    if (validate) {
        showModal(true, modalPosition);
        showModal(true, modal);
        showTextException("Login Necessário", textAlert);
    } else {
        if (!authenticated()) {
            showModal(true, modalPosition);
            showModal(true, modal);
            showTextException("Login Necessário", textAlert);
        }
    }

}

function showModal(boolean, modal) {
    if (boolean) {
        modal.classList.remove('invisible');
        modal.classList.remove('modal-animation-close');
        modal.classList.add('visible');
        modal.classList.add('modal-animation-open');
    } else {
        modal.classList.remove('visible');
        modal.classList.remove('modal-animation-open');
        modal.classList.add('modal-animation-close');
        modal.classList.add('invisible');
    }
}

function showDisplay(boolean, element) {
    if (boolean) {
        element.classList.remove('display-invisible');
        element.classList.add('display-visible');
    } else {
        element.classList.remove('display-visible');
        element.classList.add('display-invisible');
    }
}

function loaderShow(boolean, loader, overlay) {
    if (boolean === true) {
        loader.classList.remove('invisible');
        loader.classList.add('visible');
        overlay.classList.add('overlay');

    } else {
        loader.classList.remove('visible');
        loader.classList.add('invisible');
        overlay.classList.remove('overlay');
    }
}

function showBounceAuth(bounceLogin, bounceProfile) {
    const token = getToken();
    const userName = localStorage.getItem('user-name');

    if (token) {
        bounceLogin.classList.remove('display-visible');
        bounceLogin.classList.add('display-invisible');

        bounceProfile.textContent = getInitials(userName);
        bounceProfile.style.backgroundColor = stringToColor(userName);

        bounceProfile.classList.remove('display-invisible');
        bounceProfile.classList.add('display-visible');



    } else {
        bounceLogin.classList.remove('display-invisible');
        bounceLogin.classList.add('display-visible');

        bounceProfile.classList.add('invisible');
        bounceProfile.classList.remove('display-visible');
        bounceProfile.classList.add('display-invisible');
    }
}

function replaceImage(element, directory) {
    element.src = directory;
}

function showTextException(message, element) {
    element.innerText = message;
    element.classList.remove('invisible');
    element.classList.add('visible');
}

function setUiMode(state) {
    sessionStorage.setItem('uiMode', state);
}

function getUiMode() {
    return sessionStorage.getItem('uiMode');
}

function timeAgo(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diffInSeconds = Math.floor((now - date) / 1000);
    const rtf = new Intl.RelativeTimeFormat("pt-BR", { numeric: "auto" });

    if (diffInSeconds < 60) {
        return rtf.format(-diffInSeconds, "second");
    }

    const diffInMinutes = Math.floor(diffInSeconds / 60);
    if (diffInMinutes < 60) {
        return rtf.format(-diffInMinutes, "minute");
    }

    const diffInHours = Math.floor(diffInMinutes / 60);
    if (diffInHours < 24) {
        return rtf.format(-diffInHours, "hour");
    }

    const diffInDays = Math.floor(diffInHours / 24);
    if (diffInDays < 30) {
        return rtf.format(-diffInDays, "day");
    }

    const diffInMonths = Math.floor(diffInDays / 30);
    if (diffInMonths < 12) {
        return rtf.format(-diffInMonths, "month");
    }

    const diffInYears = Math.floor(diffInMonths / 12);
    return rtf.format(-diffInYears, "year");
}

function getInitials(name) {
    const parts = name.trim().split(" ");

    if (parts.length === 1) {
        return parts[0][0].toUpperCase();
    }

    return (
        parts[0][0] +
        parts[parts.length - 1][0]
    ).toUpperCase();
}

function stringToColor(str) {
    let hash = 0;

    for (let i = 0; i < str.length; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }

    const color = `hsl(${hash % 360}, 60%, 50%)`;
    return color;
}
