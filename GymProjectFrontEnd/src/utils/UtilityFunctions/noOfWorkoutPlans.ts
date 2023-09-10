export const noOfWorkoutPlans = async () => {
    const authToken = localStorage.getItem("authenticationToken");
    const userName = localStorage.getItem("username");

    const response = await fetch(`http://localhost:8080/api/gym/gymPlans/count`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${authToken}`,
            'UserName': `${userName}`
        },
    });

    if (!response.ok) {
        throw new Error('Something went wrong!');
    }

    const responseData = await response.json();


    return responseData;
    
  }