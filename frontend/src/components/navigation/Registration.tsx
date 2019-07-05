import React from "react";

class Registration extends React.Component<
    {
        registrationHandler: (
            username: string,
            password: string,
            licensePlate: string
        ) => void;
    },
    { username: string; password: string; licensePlate: string }
> {
    constructor(props: {
        registrationHandler: (username: string, password: string) => void;
    }) {
        super(props);
        this.state = {
            username: "",
            password: "",
            licensePlate: ""
        };

        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleLicensePlateChange = this.handleLicensePlateChange.bind(
            this
        );
        this.register = this.register.bind(this);
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

    handleLicensePlateChange(event: React.FormEvent<HTMLInputElement>): void {
        this.setState({
            licensePlate: event.currentTarget.value
        });
    }

    register() {
        this.props.registrationHandler(
            this.state.username,
            this.state.password,
            this.state.licensePlate
        );
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
                <input
                    type="text"
                    placeholder="License Plate"
                    value={this.state.licensePlate}
                    onChange={this.handleLicensePlateChange}
                />
                <a onClick={this.register}>Register</a>
            </div>
        );
    }
}

export default Registration;
