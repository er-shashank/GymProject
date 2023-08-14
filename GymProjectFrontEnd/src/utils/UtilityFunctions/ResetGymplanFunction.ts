export const resetGymplanFunction = () => {
  const authToken = localStorage.getItem("authenticationToken");
  fetch("http://localhost:8080/api/gym/removenewplans", {method: 'DELETE', headers: {
    'Authorization': `Bearer ${authToken}`
  }}).then((result) => {
    console.log("data resetted  ")
  }).catch((err) => {
    console.error(err);
  });

  alert('Gymplan has been reset!!')
}