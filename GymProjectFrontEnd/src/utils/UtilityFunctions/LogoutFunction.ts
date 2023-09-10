export const LogoutFunction = () => {
    const authToken = localStorage.getItem("authenticationToken");
    localStorage.removeItem("authenticationToken");
    localStorage.removeItem("username");
  
    alert('User has been Logged Out Successfully!!')
  }