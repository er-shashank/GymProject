export const resetGymplanFunction= ()=>{

    fetch("http://localhost:8080/removenewplans",{method: 'DELETE'}).then((result) => {
        console.log("data resetted  ")
      }).catch((err) => {
        console.error(err);
      });
    alert('Gymplan has been reset!!')
}