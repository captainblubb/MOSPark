import React from "react";
import { Link } from "react-router-dom";

import Authentication from "./Authentication";

class Navigation extends React.Component<
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

        this.login = this.login.bind(this);
    }

    login(username: string, password: string) {
        console.log("hi");
        this.setState({
            isLoggedIn: true,
            username: username,
            password: password
        });
        sessionStorage.setItem("user", username);
        sessionStorage.setItem("id", "420");
    }

    render() {
        if (this.state.isLoggedIn) {
            return (
                <div className={"navigation"}>
                    <div>
                        <Link to="/">Home</Link>
                    </div>
                    <div>
                        <Link to="/profile/">Profile</Link>
                    </div>
                </div>
            );
        } else {
            return (
                <div>
                    <Authentication loginHandler={this.login} />
                </div>
            );
        }
    }
}

export default Navigation;
