import React from 'react';
import LoginForm from './LoginForm';

class Authentication extends React.Component<{}, {isLoggedIn: boolean, username: string, password: string}> {
    constructor(props: {}) {
        super(props);
        this.state = {
            isLoggedIn: false,
            username: '',
            password: ''
        };
        this.handleLogin.bind(this);
        this.renderLoginForm.bind(this);
    }

    handleLogin(event: React.MouseEvent<HTMLInputElement>) {
        this.setState({
            isLoggedIn: true,
            username: event.currentTarget.value,
            password: event.target.
        });
    }

    renderLoginForm() {
        return (
            <LoginForm onSubmit={() => this.handleLogin}/>
        )
    }

    render() {
        if (this.state.isLoggedIn) {
            return (
                <div>
                    <div>Hi, {this.state.username}</div>
                </div>
            )
        } else {
            return (
                <div>{this.renderLoginForm(this.state.username, this.state.password)}</div>
            )
        }
    }
}

export default Authentication;