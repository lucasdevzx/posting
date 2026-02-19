async function fetchGetCategorys() {
    const url = "/categories?page=0&size=20";
    try {
        const response = await fetch(url, {
            method: 'GET'
        })
        const ApiData = await response.json();
        return ApiData;

    } catch (e) {
        throw new Error("Algo deu errado.");
    }
}
