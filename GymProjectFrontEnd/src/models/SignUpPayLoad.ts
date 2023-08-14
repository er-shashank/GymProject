class SignUpPayLoad {

    username?: string;
    password?: string;
    confirmPassword?: string;
    email?: string;




    constructor(

        username?: string,
        password?: string,
        confirmPassword?: string,
        email?: string


    ) {

        this.username = username
        this.password = password
        this.confirmPassword = confirmPassword
        this.email = email


    }
}

export default SignUpPayLoad;