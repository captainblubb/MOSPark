import React from "react";

class Notification extends React.Component<
    {
        id: number;
        senderId: number;
        recipientId: number;
        content: string;
        date: string;
        dismissed: boolean;
        dismissedDate: string;
        dismissalHandler: (id: number) => void;
    },
    {}
> {
    constructor(props: {
        id: number;
        senderId: number;
        recipientId: number;
        content: string;
        date: string;
        dismissed: boolean;
        dismissedDate: string;
        dismissalHandler: () => void;
    }) {
        super(props);
        this.toggleDismissal = this.toggleDismissal.bind(this);
    }

    toggleDismissal() {
        this.props.dismissalHandler(this.props.id);
    }

    render() {
        return (
            <div className={"notification"}>
                <div className={"content"}>
                    <div>- {this.props.date} -</div>
                    <div>{this.props.content}</div>
                </div>
                <div>
                    <a onClick={this.toggleDismissal}>Dismiss</a>
                </div>
            </div>
        );
    }
}

export default Notification;
