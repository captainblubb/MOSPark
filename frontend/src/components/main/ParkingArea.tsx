import React from "react";

import ParkingSpot from "./ParkingSpot";
import ParkingSpotJson from "./ParkingSpotJson";

class ParkingArea extends React.Component<
    {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        currentUserId: number;
        reloader: () => void;
    },
    {
        selectedParkingSpotIds: Set<number>;
    }
    > {
    constructor(props: {
        id: number;
        parkingSpots: Array<ParkingSpotJson>;
        currentUserId: number;
        reloader: () => void;
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
        this.renderButton = this.renderButton.bind(this);
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
                        selectedIds = new Set<number>();
                    } else if (selectedIds.has(currentUserSpot.id)) {
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
        const selectedIdsString: string = Array.from(
            this.state.selectedParkingSpotIds
        ).join();
        fetch(`http://localhost:8080/MOSPark/rest/notifications/notify`, {
            method: "POST",
            body:
                "{ \"userID\": " +
                this.props.currentUserId +
                ", \"ids\": [" +
                selectedIdsString +
                "]}",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => console.log("Success"))
            .catch(error => console.log("Error:", error));
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
            // free
            fetch(`http://localhost:8080/MOSPark/rest/parkingspots/free`, {
                method: "POST",
                body: "{ \"parkingSpotId\": " + selectedParkingSpot.id + " }",
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => {
                    console.log("Success");
                    selectedParkingSpot.userId = -1;
                })
                .catch(error => console.log("Error:", error));
        } else if (selectedParkingSpot.userId === -1) {
            // occupy
            for (let i: number = 0; i < this.props.parkingSpots.length; i++) {
                const spot: ParkingSpotJson = this.props.parkingSpots[i];
                if (spot.userId === this.props.currentUserId) {
                    return;
                }
            }
            fetch(`http://localhost:8080/MOSPark/rest/parkingspots/occupy`, {
                method: "POST",
                body:
                    "{ \"userId\": " +
                    this.props.currentUserId +
                    ", \"parkingSpotId\": " +
                    selectedParkingSpot.id +
                    " }",
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => {
                    console.log("Success");
                    selectedParkingSpot.userId = this.props.currentUserId;
                })
                .catch(error => console.log("Error:", error));
        }
        this.setState({
            selectedParkingSpotIds: new Set([])
        });
        this.props.reloader()
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

    renderButton(): JSX.Element {
        if (this.state.selectedParkingSpotIds.size > 0) {
            const selectedSpot: ParkingSpotJson | null = this.getParkingSpotById(
                this.state.selectedParkingSpotIds.values().next().value
            );
            if (selectedSpot == null) {
                return <div>Please select a field.</div>;
            }

            const currentUserSpot: ParkingSpotJson | null = this.getParkingSpotByUserId(
                this.props.currentUserId
            );
            if (currentUserSpot == null) {
                if (selectedSpot.userId === -1) {
                    return <a onClick={this.toggleParking}>Occupy</a>;
                } else {
                    return <div>Please select a free parking spot.</div>;
                }
            } else {
                if (this.state.selectedParkingSpotIds.has(currentUserSpot.id)) {
                    return <a onClick={this.toggleParking}>Free</a>;
                } else {
                    if (selectedSpot.userId === -1) {
                        return <div>You have already parked.</div>;
                    }
                    return <a onClick={this.notifySelectedUsers}>Notify</a>;
                }
            }
        }
        return <div>Please select a field.</div>;
    }

    render() {
        return (
            <div className={"parkingArea"}>
                <div className={"parkingSpotsContainer"}>
                    <div className={"numberrow col0"}>1</div>
                    <div className={"numberrow col1"}>2</div>
                    <div className={"numberrow col2"}>3</div>
                    <div className={"numberrow col3"}>4</div>
                    <div className={"numbercol row0"}>01</div>
                    <div className={"numbercol row1"}>02</div>
                    <div className={"numbercol row2"}>03</div>
                    <div className={"numbercol row3"}>04</div>
                    <div className={"numbercol row4"}>05</div>
                    <div className={"numbercol row5"}>06</div>
                    <div className={"numbercol row6"}>07</div>
                    <div className={"numbercol row7"}>08</div>
                    <div className={"numbercol row8"}>09</div>
                    <div className={"numbercol row9"}>10</div>
                    {this.createParkingSpots()}
                </div>
                <div>{this.renderButton()}</div>
            </div>
        );
    }
}

export default ParkingArea;
