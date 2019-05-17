import React, {FormEvent} from 'react';

class LoginForm extends React.Component<{onSubmit: React.MouseEvent<HTMLInputElement>}, {username: string, password: string}> {
    constructor(props: {onSubmit: React.MouseEvent<HTMLInputElement>}) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event: React.KeyboardEvent<HTMLInputElement>): void {
        this.setState({
            username: event.currentTarget.value,
            password: event.target.password
        });
    }
    render() {
        return (
            <div>
                <input type="text" placeholder="Username"
                       value={this.state.username}
                       onChange={this.handleChange} />
                <input type="password" placeholder="Password"
                       value={this.state.password}
                       onChange={this.handleChange} />
                <button onClick={this.props.onSubmit}>Login</button>
            </div>
        );
    }
}

export default LoginForm;