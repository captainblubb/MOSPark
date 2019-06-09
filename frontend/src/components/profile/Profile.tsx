import React from "react";
import NotificationContainer from "./NotificationContainer";

class Profile extends React.Component<
    {},
    {
        currentUserId: number;
    }
> {
    constructor(props: {}) {
        super(props);

        Profile.getCurrentUserId = Profile.getCurrentUserId.bind(this);
        this.state = {
            currentUserId: Profile.getCurrentUserId()
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

    render() {
        return (
            <div>
                <NotificationContainer
                    currentUserId={this.state.currentUserId}
                />
            </div>
        );
    }
}

export default Profile;
