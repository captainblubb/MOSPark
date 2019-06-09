import React from "react";

class LoginForm extends React.Component<
    { submitHandler: (username: string, password: string) => void },
    { username: string; password: string }
> {
    constructor(props: {
        submitHandler: (username: string, password: string) => void;
    }) {
        super(props);
        this.state = {
            username: "",
            password: ""
        };

        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.login = this.login.bind(this);
    }

    handleUsernameChange(event: React.FormEvent<HTMLInputElement>): void {
        this.setState({
            username: event.currentTarget.value
        });
    }

    handlePasswordChange(event: React.FormEvent<HTMLInputElement>): void {
        this.setState({
            password: event.currentTarget.value
        });
    }

    login() {
        this.props.submitHandler(this.state.username, this.state.password);
    }

    render() {
        return (
            <div className={"loginForm"}>
                <input
                    type="text"
                    placeholder="Username"
                    value={this.state.username}
                    onChange={this.handleUsernameChange}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={this.state.password}
                    onChange={this.handlePasswordChange}
                />
                <a onClick={this.login}>Login</a>
            </div>
        );
    }
}

export default LoginForm;
