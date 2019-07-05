import React from "react";
import Notification from "./Notification";
import NotificationJson from "./NotificationJson";

class NotificationContainer extends React.Component<
    {
        currentUserId: number;
        notifications: Array<NotificationJson>;
    },
    {
        unread: Array<NotificationJson>;
        read: Array<NotificationJson>;
        show: string;
    }
> {
    constructor(props: {
        currentUserId: number;
        notifications: Array<NotificationJson>;
    }) {
        super(props);

        this.state = {
            unread: this.getUnreadNotifications(),
            read: this.getReadNotifications(),
            show: "unread"
        };
        this.getUnreadNotifications = this.getUnreadNotifications.bind(this);
        this.getReadNotifications = this.getReadNotifications.bind(this);
        this.renderNotifications = this.renderNotifications.bind(this);
        this.dismissNotification = this.dismissNotification.bind(this);
        this.showNotifications = this.showNotifications.bind(this);
        this.showArchive = this.showArchive.bind(this);
    }

    getUnreadNotifications(): Array<NotificationJson> {
        let unreadNotifications: Array<NotificationJson> = [];
        for (let i: number = 0; i < this.props.notifications.length; i++) {
            if (!this.props.notifications[i].dismissed) {
                unreadNotifications.push(this.props.notifications[i]);
            }
        }
        return unreadNotifications;
    }

    getReadNotifications(): Array<NotificationJson> {
        let readNotifications: Array<NotificationJson> = [];
        for (let i: number = 0; i < this.props.notifications.length; i++) {
            if (this.props.notifications[i].dismissed) {
                readNotifications.push(this.props.notifications[i]);
            }
        }
        return readNotifications;
    }

    renderNotifications(): Array<JSX.Element> {
        let notifications: Array<JSX.Element> = [];
        if (this.state.show === "unread") {
            for (let i: number = 0; i < this.state.unread.length; i++) {
                const currentNotification: NotificationJson = this.props
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
        } else {
            for (let i: number = 0; i < this.state.read.length; i++) {
                const currentNotification: NotificationJson = this.props
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
        }
        return notifications;
    }

    dismissNotification(id: number): void {
        for (let i: number = 0; i < this.state.unread.length; i++) {
            const currentNotification: NotificationJson = this.state.unread[i];
            if (
                currentNotification.id === id &&
                !currentNotification.dismissed
            ) {
                fetch(
                    `http://localhost:8080/MOSPark/rest/notifications/dismiss`,
                    {
                        method: "POST",
                        body: '{ "notificationId": ' + id + " }",
                        headers: {
                            "Content-Type": "application/json"
                        }
                    }
                )
                    .then(response => response.json())
                    .then(response => {
                        console.log("Success:", JSON.stringify(response));
                        currentNotification.dismissed = true;

                        let unreadNotifications: Array<NotificationJson> = this
                            .state.unread;
                        let readNotifications: Array<NotificationJson> = this
                            .state.read;
                        unreadNotifications.splice(i, i + 1);
                        readNotifications.push(currentNotification);

                        this.setState({
                            unread: unreadNotifications,
                            read: readNotifications
                        });
                    })
                    .catch(error => console.log("Error:", error));
            }
        }
    }

    showNotifications() {
        this.setState({
            show: "unread"
        });
    }

    showArchive() {
        this.setState({
            show: "read"
        });
    }

    render() {
        return (
            <div className={"notificationContainer"}>
                <div>
                    <div>
                        <a onClick={this.showNotifications}>Notifications</a>
                    </div>
                    <div>
                        <a onClick={this.showArchive}>Archive</a>
                    </div>
                </div>
                <hr />
                <div>{this.renderNotifications()}</div>
            </div>
        );
    }
}

export default NotificationContainer;
