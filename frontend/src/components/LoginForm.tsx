import React from 'react';

class LoginForm extends React.Component<{submitFunction: (event: React.FormEvent<HTMLFormElement>) => void}, {username: string, password: string}> {
    constructor(props: {submitFunction: (event: React.FormEvent<HTMLFormElement>) => void}) {
        super(props);
        this.state = {
            username: '',
            password: ''
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

    render() {
        return (
            <form onSubmit={this.props.submitFunction}>
                <input type="text" placeholder="Username" name="username"
                       value={this.state.username}
                       onChange={this.handleUsernameChange} />
                <input type="password" placeholder="Password" name="password"
                       value={this.state.password}
                       onChange={this.handlePasswordChange} />
                <input type="submit"
                       value="Login" />
            </form>
        );
    }
}

export default LoginForm;