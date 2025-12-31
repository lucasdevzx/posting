function buildURL(resource, params = {}) {
    const searchParams = new URLSearchParams(params);
    return `${resource}?${searchParams.toString()}`;
}
