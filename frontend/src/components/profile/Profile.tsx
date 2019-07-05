import React from "react";
import NotificationContainer from "./NotificationContainer";
import NotificationJson from "./NotificationJson";

class Profile extends React.Component<
    {},
    {
        currentUserId: number;
        notifications: Array<NotificationJson>;
    }
    > {
    constructor(props: {}) {
        super(props);

        this.getCurrentUserId = this.getCurrentUserId.bind(this);
        this.state = {
            currentUserId: 0,
            notifications: []
        };

        this.fetchNotifications = this.fetchNotifications.bind(this);
        this.logout = this.logout.bind(this);
    }

    componentWillMount() {
        this.getCurrentUserId();
        this.fetchNotifications();
    }

    getCurrentUserId(): void {
        const currentSessionUserId: string | null = sessionStorage.getItem(
            "id"
        );
        const val: any = currentSessionUserId != null
            ? parseInt(currentSessionUserId)
            : 0;
        this.setState({
            currentUserId: val
        })
    }

    fetchNotifications(): void {
        let fetchedNotifications: Array<NotificationJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/notifications/user`, {
            method: "POST",
            body: "{ \"userId\": " + this.state.currentUserId + " }",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(response => {
                console.log("Success:", response);
                fetchedNotifications = response;
                this.setState({
                    notifications: fetchedNotifications
                });
            })
            .catch(error => console.log("Error:", error));
    }

    logout() {
        fetch(`http://localhost:8080/MOSPark/rest/user/logout`, {
            method: "POST",
            body: "{ \"userID\": " + this.state.currentUserId + "}",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                console.log("Success:", response);
                sessionStorage.clear();
                window.location.replace("/");
            })
            .catch(error => console.log("Error:", error));
    }

    render() {
        return (
            <div>
                <div>
                    <a onClick={this.logout}>Logout</a>
                </div>
                <NotificationContainer
                    currentUserId={this.state.currentUserId}
                    notifications={this.state.notifications}
                />
            </div>
        );
    }
}

export default Profile;
