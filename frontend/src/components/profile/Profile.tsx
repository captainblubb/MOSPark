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
            notifications: Profile.fetchNotifications()
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

    static fetchNotifications(): Array<NotificationJson> {
        /*
        fetch(`http://localhost:8080/notificationsForUser/{this.props.currentUserId}`)
            .then(result => result.json())
            .then(notifications => this.setState({
                notifications: JSON.parse(notifications)
            }));
        */
        return JSON.parse(
            '[{"id": 0, "senderId": 1, "recipientId": 420, "content": "parking spot (x:1,y:2) from area 0 wants you to make space for him", "date": "04.20.2019, 16:20", "dismissed": false, "dismissedDate": ""}]'
        );
    }

    logout() {
        sessionStorage.clear();
        window.location.replace("/");
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
