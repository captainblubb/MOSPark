import React from 'react';
import LoginForm from './LoginForm';
import {Link} from "react-router-dom";

class Authentication extends React.Component<{}, {isLoggedIn: boolean, username: string, password: string}> {
    constructor(props: {}) {
        super(props);
        this.state = {
            isLoggedIn: false,
            username: '',
            password: ''
        };
        this.handleLogin.bind(this);
    }

    handleLogin = (event: React.FormEvent<HTMLFormElement>): void => {
        event.preventDefault();
        this.setState({
            isLoggedIn: true,
            username: event.currentTarget.username.value,
            password: event.currentTarget.password.value
        });
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