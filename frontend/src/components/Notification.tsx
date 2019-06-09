import React from "react";

class Notification extends React.Component<
    {
        fromUser: number;
        toUser: number;
        notification: string;
        date: string;
    },
    {}
> {
    constructor(props: {
        fromUser: number;
        toUser: number;
        notification: string;
        date: string;
    }) {
        super(props);
    }

    render() {
        return (
            <div className={"notification"}>
                <div>{this.props.date}</div>
                <div>{this.props.notification}</div>
            </div>
        );
    }
}

export default Notification;
