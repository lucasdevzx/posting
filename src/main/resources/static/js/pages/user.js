async function setUserName() {
    const user = await fetchGetUser();
    const userName = user.name;
    localStorage.setItem('user-name', userName);
}

async function fetchGetUser() {
    try {
        const token = getToken();
        const url = "/users/me";

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        return  await response.json();

    } catch (e) {
        throw new Error(e);
    }
}