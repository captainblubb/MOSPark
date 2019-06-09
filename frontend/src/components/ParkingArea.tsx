import React from "react";

import ParkingSpot from "./ParkingSpot";
import ParkingSpotJson from "./ParkingSpotJson";

class ParkingArea extends React.Component<
    {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        selectionHandler: (parkingSpotId: number) => void;
        selectedUserIds: Set<number>;
    },
    {}
> {
    constructor(props: {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        selectionHandler: (parkingSpotId: number) => void;
        selectedUserIds: Set<number>;
    }) {
        super(props);
    }
    createParkingSpots(): Array<JSX.Element> {
        let spots: Array<JSX.Element> = [];
        const currentUserIdSession: string | null = sessionStorage.getItem(
            "id"
        );
        const currentUserId: string =
            currentUserIdSession != null ? currentUserIdSession : "";

        for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
            const currentSpot: ParkingSpotJson = this.props.parkingSpots[i];
            if (currentSpot.areaId === this.props.id) {
                spots.push(
                    <ParkingSpot
                        key={i}
                        id={currentSpot.id}
                        userId={currentSpot.userId}
                        column={currentSpot.column}
                        row={currentSpot.row}
                        occupiedByCurrentUser={
                            currentUserId !== "" &&
                            parseInt(currentUserId) === currentSpot.userId
                        }
                        selectionHandler={this.props.selectionHandler}
                        selected={this.props.selectedUserIds.has(
                            currentSpot.userId
                        )}
                    />
                );
            }
        }
        return spots;
    }

    render() {
        return <div className={"parkingArea"}>{this.createParkingSpots()}</div>;
    }
}

export default ParkingArea;
