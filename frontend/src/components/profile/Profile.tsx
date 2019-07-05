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

        Profile.getCurrentUserId = Profile.getCurrentUserId.bind(this);
        this.state = {
            currentUserId: Profile.getCurrentUserId(),
            notifications: this.fetchNotifications()
        };
    }

    static getCurrentUserId(): number {
        const currentSessionUserId: string | null = sessionStorage.getItem(
            "id"
        );
        return currentSessionUserId != null
            ? parseInt(currentSessionUserId)
            : 0;
    }

    fetchNotifications(): Array<NotificationJson> {
        let fetchedNotifications: Array<NotificationJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/notifications/dismiss`, {
            method: "POST",
            body: '{ "userId": ' + this.state.currentUserId + " }",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(response => {
                console.log("Success:", JSON.stringify(response));
                fetchedNotifications = JSON.parse(response);
            })
            .catch(error => console.log("Error:", error));
        return fetchedNotifications;
    }

    logout() {
        fetch(`http://localhost:8080/MOSPark/rest/user/logout`, {
            method: "POST",
            body: '{ "userID": ' + this.state.currentUserId + "}",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(response => {
                console.log("Success:", JSON.stringify(response));
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
