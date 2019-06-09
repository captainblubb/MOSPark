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

    handleRegistration(username: string, password: string): void {}

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
