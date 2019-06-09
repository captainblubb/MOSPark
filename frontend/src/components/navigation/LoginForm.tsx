import React from "react";

class LoginForm extends React.Component<
    { submitFunction: (username: string, password: string) => void },
    { username: string; password: string }
> {
    constructor(props: {
        submitFunction: (username: string, password: string) => void;
    }) {
        super(props);
        this.state = {
            username: "",
            password: ""
        };

        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
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
        this.props.submitFunction(this.state.username, this.state.password);
    }

    render() {
        return (
            <div className={"loginForm"}>
                <input
                    type="text"
                    placeholder="Username"
                    name="username"
                    value={this.state.username}
                    onChange={this.handleUsernameChange}
                />
                <input
                    type="password"
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.handlePasswordChange}
                />
                <a onClick={this.login}>Login</a>
            </div>
        );
    }
}

export default LoginForm;
