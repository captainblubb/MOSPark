import React from "react";
import Notification from "./Notification";
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
        Profile.fetchNotifications = Profile.fetchNotifications.bind(this);
        this.state = {
            currentUserId: Profile.getCurrentUserId(),
            notifications: Profile.fetchNotifications()
        };
        this.renderNotifications = this.renderNotifications.bind(this);
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
            '[{"id": 0, "senderId": 1, "recipientId": 420, "content": "parking spot X wants you to make space for him", "date": "04.20.2019, 16:20", "dismissed": false, "dismissedDate": ""}]'
        );
    }

    renderNotifications(): Array<JSX.Element> {
        let notifications: Array<JSX.Element> = [];
        for (let i: number = 0; i < this.state.notifications.length; i++) {
            const currentNotification: NotificationJson = this.state
                .notifications[i];
            notifications.push(
                <div key={i}>
                    <Notification
                        id={currentNotification.id}
                        senderId={currentNotification.senderId}
                        recipientId={currentNotification.recipientId}
                        content={currentNotification.content}
                        date={currentNotification.date}
                        dismissed={currentNotification.dismissed}
                        dismissedDate={currentNotification.dismissedDate}
                        dismissalHandler={this.dismissNotification}
                    />
                </div>
            );
        }
        return notifications;
    }

    dismissNotification(id: number): void {}

    render() {
        return (
            <div>
                <div className={"fsSubheader"}>Notifications</div>
                <div>{this.renderNotifications()}</div>
            </div>
        );
    }
}

export default Profile;
