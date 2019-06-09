import React from "react";

import ParkingSpot from "./ParkingSpot";
import ParkingSpotJson from "./ParkingSpotJson";

class ParkingArea extends React.Component<
    {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        currentUserId: number;
    },
    {
        selectedParkingSpotIds: Set<number>;
    }
> {
    constructor(props: {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        currentUserId: number;
    }) {
        super(props);

        this.state = {
            selectedParkingSpotIds: new Set()
        };

        this.handleParkingSpotSelection = this.handleParkingSpotSelection.bind(
            this
        );
        this.notifySelectedUsers = this.notifySelectedUsers.bind(this);
        this.toggleParking = this.toggleParking.bind(this);
        this.createParkingSpots = this.createParkingSpots.bind(this);
    }

    handleParkingSpotSelection(parkingSpotId: number): void {
        const newlySelectedSpot: ParkingSpotJson | null = this.getParkingSpotById(
            parkingSpotId
        );
        if (newlySelectedSpot == null) {
            return;
        }
        console.log(newlySelectedSpot.userId);
        console.log(this.props.currentUserId);

        let selectedIds: Set<number> = new Set(
            this.state.selectedParkingSpotIds
        );

        if (selectedIds.has(newlySelectedSpot.id)) {
            selectedIds.delete(newlySelectedSpot.id);
        } else {
            if (selectedIds.size > 0) {
                if (newlySelectedSpot.userId === this.props.currentUserId) {
                    // any -> blue
                    selectedIds = new Set<number>();
                } else {
                    const currentUserSpot: ParkingSpotJson | null = this.getParkingSpotByUserId(
                        this.props.currentUserId
                    );
                    if (currentUserSpot == null) {
                        return;
                    }
                    if (selectedIds.has(currentUserSpot.id)) {
                        // blue -> any
                        selectedIds = new Set<number>();
                    }

                    const alreadySelectedSpot: ParkingSpotJson | null = this.getParkingSpotById(
                        this.state.selectedParkingSpotIds.values().next().value
                    );
                    if (alreadySelectedSpot == null) {
                        return;
                    }
                    if (alreadySelectedSpot.userId === -1) {
                        // green -> any
                        selectedIds = new Set<number>();
                    } else if (
                        alreadySelectedSpot.userId !== -1 &&
                        newlySelectedSpot.userId === -1
                    ) {
                        // red -> green
                        selectedIds = new Set<number>();
                    }
                }
            }
            selectedIds.add(newlySelectedSpot.id);
        }

        this.setState({
            selectedParkingSpotIds: selectedIds
        });
        console.log("selected parking spots: " + Array.from(selectedIds));
    }

    notifySelectedUsers(): void {
        alert(this.state.selectedParkingSpotIds);
        /*
        fetch(`http://localhost:8080/notifyUsers`, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(response => console.log('Success:', JSON.stringify(response)))
            .catch(error => console.log('Error:', error))
            */
    }

    toggleParking(): void {
        if (
            this.state.selectedParkingSpotIds.size > 1 ||
            this.state.selectedParkingSpotIds.size === 0
        ) {
            return;
        }

        const selectedId: number = this.state.selectedParkingSpotIds
            .values()
            .next().value;
        const selectedParkingSpot: ParkingSpotJson | null = this.getParkingSpotById(
            selectedId
        );
        if (selectedParkingSpot == null) {
            return;
        }

        if (selectedParkingSpot.userId === this.props.currentUserId) {
            selectedParkingSpot.userId = -1;
        } else if (selectedParkingSpot.userId === -1) {
            for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
                const spot: ParkingSpotJson = this.props.parkingSpots[i];
                if (spot.userId === this.props.currentUserId) {
                    return;
                }
            }
            selectedParkingSpot.userId = this.props.currentUserId;
        }
        this.setState({
            selectedParkingSpotIds: new Set([])
        });
        /*
        TODO: API call for both occupying/freeing parking spot
        fetch(`http://localhost:8080/parkingSpot`, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(response => console.log('Success:', JSON.stringify(response)))
            .catch(error => console.log('Error:', error))
        */
    }

    getParkingSpotByUserId(userId: number): ParkingSpotJson | null {
        for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
            const parkingSpot: ParkingSpotJson = this.props.parkingSpots[i];
            if (parkingSpot.userId === userId) {
                return this.props.parkingSpots[i];
            }
        }
        return null;
    }

    getParkingSpotById(parkingSpotId: number): ParkingSpotJson | null {
        for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
            const parkingSpot: ParkingSpotJson = this.props.parkingSpots[i];
            if (parkingSpot.id === parkingSpotId) {
                return this.props.parkingSpots[i];
            }
        }
        return null;
    }

    createParkingSpots(): Array<JSX.Element> {
        let spots: Array<JSX.Element> = [];

        for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
            const currentSpot: ParkingSpotJson = this.props.parkingSpots[i];
            if (currentSpot.areaId === this.props.id) {
                spots.push(
                    <ParkingSpot
                        key={i}
                        id={currentSpot.id}
                        userId={currentSpot.userId}
                        currentUserId={this.props.currentUserId}
                        column={currentSpot.column}
                        row={currentSpot.row}
                        selectionHandler={this.handleParkingSpotSelection}
                        selected={this.state.selectedParkingSpotIds.has(
                            currentSpot.id
                        )}
                    />
                );
            }
        }
        return spots;
    }

    render() {
        if (this.state.selectedParkingSpotIds.size > 0) {
            if (this.state.selectedParkingSpotIds.size === 1) {
                const selectedParkingSpot = this.getParkingSpotById(
                    this.state.selectedParkingSpotIds.values().next().value
                );
                if (
                    selectedParkingSpot != null &&
                    selectedParkingSpot.userId === this.props.currentUserId
                ) {
                    return (
                        <div className={"parkingArea"}>
                            <div className={"parkingSpotsContainer"}>
                                {this.createParkingSpots()}
                            </div>
                            <button onClick={this.toggleParking}>
                                Occupy/Free
                            </button>
                        </div>
                    );
                } else {
                    return (
                        <div className={"parkingArea"}>
                            <div className={"parkingSpotsContainer"}>
                                {this.createParkingSpots()}
                            </div>
                            <button onClick={this.notifySelectedUsers}>
                                notify
                            </button>
                        </div>
                    );
                }
            } else {
                return (
                    <div className={"parkingArea"}>
                        <div className={"parkingSpotsContainer"}>
                            {this.createParkingSpots()}
                        </div>
                        <button onClick={this.notifySelectedUsers}>
                            notify
                        </button>
                    </div>
                );
            }
        } else {
            return (
                <div className={"parkingArea"}>
                    <div className={"parkingSpotsContainer"}>
                        {this.createParkingSpots()}
                    </div>
                    <div>Select a field</div>
                </div>
            );
        }
    }
}

export default ParkingArea;
