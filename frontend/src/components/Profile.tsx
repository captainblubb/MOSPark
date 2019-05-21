import React from 'react';
import Notification from './Notification';
import NotificationJson from "./NotificationJson";

class Profile extends React.Component<{}, {
    currentUserId: number,
    notifications: Array<NotificationJson>
}> {
    constructor(props: {}) {
        super(props);

        Profile.getCurrentUserId = Profile.getCurrentUserId.bind(this);
        Profile.fetchNotifications = Profile.fetchNotifications.bind(this);
        this.state = {
            currentUserId: Profile.getCurrentUserId(),
            notifications: Profile.fetchNotifications()
        }
    }

    static getCurrentUserId(): number {
        const currentSessionUserId: string | null = sessionStorage.getItem("id");
        return currentSessionUserId != null ? parseInt(currentSessionUserId) : 0;
    }

    static fetchNotifications(): Array<NotificationJson> {
        /*
        fetch(`http://localhost:8080/notificationsForUser/{this.props.currentUserId}`)
            .then(result => result.json())
            .then(notifications => this.setState({
                notifications: JSON.parse(notifications)
            }));
        */
        return JSON.parse('[{"id": 0, "fromUserId": 1, "toUserId": 420, "notification": "parking spot X wants you to make space for him", "date": "04.20.2019, 16:20"}]');
    }

    renderNotifications(): Array<JSX.Element> {
        let notifications: Array<JSX.Element> = [];
        for (let i: number = 0; i < this.state.notifications.length; i++) {
            const currentNotification: NotificationJson = this.state.notifications[i];
            notifications.push(
                <Notification
                    fromUser={currentNotification.fromUserId}
                    toUser={currentNotification.toUserId}
                    notification={currentNotification.notification}
                    date={currentNotification.date}/>
            )
        }
        return notifications;
    }

    render() {
        return (
            <div>
                <div>Notifications</div>
                <div>{this.renderNotifications()}</div>
            </div>
        )
    }
}

export default Profile;