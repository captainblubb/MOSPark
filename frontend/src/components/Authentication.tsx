import React from 'react';
import LoginForm from './LoginForm';
import {Link} from "react-router-dom";

class Authentication extends React.Component<{}, {isLoggedIn: boolean, username: string, password: string}> {
    constructor(props: {}) {
        super(props);
        const currentSessionUser: string | null = sessionStorage.getItem("user");
        const currentUser: string = currentSessionUser != null ? currentSessionUser : "";
        this.state = {
            isLoggedIn: currentUser !== "",
            username: currentUser,
            password: ''
        };
        this.handleLogin.bind(this);
    }

    handleLogin = (event: React.FormEvent<HTMLFormElement>): void => {
        event.preventDefault();
        const username: string = event.currentTarget.username.value;

        this.setState({
            isLoggedIn: true,
            username: username,
            password: event.currentTarget.password.value
        });
        sessionStorage.setItem("user", username);
        sessionStorage.setItem("id", "420");
    };

    render() {
        if (this.state.isLoggedIn) {
            return (
                <div>
                    <Link to="/profile/">
                        <div>Hi, {this.state.username}</div>
                    </Link>
                </div>
            )
        } else {
            return (
                <div>
                    <LoginForm submitFunction={this.handleLogin}/>
                </div>
            )
        }
    }
}

export default Authentication;