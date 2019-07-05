import React from "react";
import LoginForm from "./LoginForm";
import Registration from "./Registration";

class Authentication extends React.Component<
    { loginHandler(username: string, password: string): void },
    { isLoggedIn: boolean; username: string; password: string }
> {
    constructor(props: {
        loginHandler(username: string, password: string): void;
    }) {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleRegistration = this.handleRegistration.bind(this);
    }

    handleLogin(username: string, password: string): void {
        this.props.loginHandler(username, password);
    }

    handleRegistration(
        username: string,
        password: string,
        licensePlate: string
    ): void {
        fetch(`http://localhost:8080/MOSPark/rest/user/register`, {
            method: "POST",
            body:
                '{"username": "' +
                username +
                '", "password": "' +
                password +
                '", "licensePlate": "' +
                licensePlate +
                '" }',
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => console.log("Success"))
            .catch(error => console.log("Error:", error));
        //window.location.reload();
    }

    render() {
        return (
            <div>
                <LoginForm submitHandler={this.handleLogin} />
                <div className={"separator"}>- OR REGISTER -</div>
                <Registration registrationHandler={this.handleRegistration} />
            </div>
        );
    }
}

export default Authentication;
