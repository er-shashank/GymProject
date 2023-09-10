export const checkIsUserLoggedIn = (): boolean => {
    const isIt = (localStorage.getItem("authenticationToken") != null && localStorage.getItem("username") != null);
    return isIt;
}