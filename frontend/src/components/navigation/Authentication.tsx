import React from "react";
import LoginForm from "./LoginForm";
import { Link } from "react-router-dom";
import Registration from "./Registration";

class Authentication extends React.Component<
    {},
    { isLoggedIn: boolean; username: string; password: string }
> {
    constructor(props: {}) {
        super(props);
        const currentSessionUser: string | null = sessionStorage.getItem(
            "user"
        );
        const currentUser: string =
            currentSessionUser != null ? currentSessionUser : "";
        this.state = {
            isLoggedIn: currentUser !== "",
            username: currentUser,
            password: ""
        };
        this.handleLogin.bind(this);
    }

    handleLogin = (username: string, password: string): void => {
        this.setState({
            isLoggedIn: true,
            username: username,
            password: password
        });
        sessionStorage.setItem("user", username);
        sessionStorage.setItem("id", "420");
    };

    render() {
        return (
            <div>
                <LoginForm submitFunction={this.handleLogin} />
                <div className={"separator"}>- OR REGISTER -</div>
                <Registration />
            </div>
        );
    }
}

export default Authentication;
