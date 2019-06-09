import React, { ChangeEvent } from "react";
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";
import ParkingSpotJson from "./ParkingSpotJson";
import { thisTypeAnnotation } from "@babel/types";

class Main extends React.Component<
    {},
    {
        parkingAreas: Array<ParkingAreaJson>;
        parkingSpots: Array<ParkingSpotJson>;
        currentAreaId: number;
        selectedParkingSpotIds: Set<number>;
        currentUserId: number;
    }
> {
    constructor(props: {}) {
        super(props);
        Main.fetchParkingAreas = Main.fetchParkingAreas.bind(this);
        Main.fetchParkingSpots = Main.fetchParkingSpots.bind(this);

        this.state = {
            parkingAreas: Main.fetchParkingAreas(),
            parkingSpots: Main.fetchParkingSpots(),
            currentAreaId: 0,
            selectedParkingSpotIds: new Set([]),
            currentUserId: Main.getCurrentUserId()
        };

        this.renderParkingArea = this.renderParkingArea.bind(this);
        this.renderOptions = this.renderOptions.bind(this);
        this.changeArea = this.changeArea.bind(this);
        this.handleParkingSpotSelection = this.handleParkingSpotSelection.bind(
            this
        );
        this.notifySelectedUsers = this.notifySelectedUsers.bind(this);
        this.toggleParking = this.toggleParking.bind(this);
    }

    static fetchParkingAreas(): Array<ParkingAreaJson> {
        /*
        fetch(`http://localhost:8080/parkingAreas/`)
            .then(result => result.json())
            .then(areas => this.setState({
                parkingAreas: JSON.parse(areas)
            }));
        */
        return JSON.parse('[{"id": 0}, {"id": 1}]');
    }

    static fetchParkingSpots(): Array<ParkingSpotJson> {
        /*
        const parkingAreaId: number = this.props.id;
        fetch(`http://localhost:8080/parkingAreaSpots/` + parkingAreaId)
            .then(result => result.json())
            .then(spots => this.setState({
                parkingSpots: JSON.parse(spots)
            }));
        */
        return JSON.parse(
            '[{"id": 0, "userId": 420, "areaId": 0, "column": 0, "row": 0},{"id": 1, "userId": -1, "areaId": 0, "column": 0, "row": 1},{"id": 2, "userId": -1, "areaId": 0, "column": 0, "row": 2},{"id": 3, "userId": -1, "areaId": 0, "column": 1, "row": 0},{"id": 4, "userId": -1, "areaId": 0, "column": 2, "row": 0},{"id": 5, "userId": -1, "areaId": 0, "column": 3, "row": 0},{"id": 6, "userId": -1, "areaId": 0, "column": 3, "row": 1},{"id": 7, "userId": -1, "areaId": 0, "column": 3, "row": 2}]'
        );
    }

    static getCurrentUserId(): number {
        const currentSessionUserId: string | null = sessionStorage.getItem(
            "id"
        );
        return currentSessionUserId != null
            ? parseInt(currentSessionUserId)
            : 0;
    }

    renderParkingArea(): JSX.Element {
        return (
            <ParkingArea
                key={this.state.currentAreaId}
                id={this.state.currentAreaId}
                parkingSpots={this.state.parkingSpots}
                selectionHandler={this.handleParkingSpotSelection}
                selectedUserIds={this.state.selectedParkingSpotIds}
            />
        );
    }

    renderOptions(): JSX.Element {
        let options: Array<JSX.Element> = [];
        for (let i: number = 0; i < this.state.parkingAreas.length; i++) {
            options.push(
                <option key={i} value={i}>
                    Area {this.state.parkingAreas[i].id}
                </option>
            );
        }
        return <select onChange={this.changeArea}>{options}</select>;
    }

    changeArea(event: ChangeEvent<HTMLSelectElement>): void {
        this.setState({
            currentAreaId: parseInt(event.currentTarget.value),
            selectedParkingSpotIds: new Set([])
        });
    }

    handleParkingSpotSelection(parkingSpotId: number): void | undefined {
        const parkingSpot: ParkingSpotJson | null = this.getParkingSpotById(
            parkingSpotId
        );
        if (parkingSpot == null) {
            return;
        }

        let selectedIds: Set<number> = new Set(
            this.state.selectedParkingSpotIds
        );
        if (selectedIds.has(parkingSpot.id)) {
            selectedIds.delete(parkingSpot.id);
        } else {
            selectedIds.add(parkingSpot.id);
        }

        this.setState({
            selectedParkingSpotIds: selectedIds,
            currentAreaId: this.state.currentAreaId
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
        if (this.state.selectedParkingSpotIds.size > 1) {
            return;
        }

        const selectedId: number = this.state.selectedParkingSpotIds
            .values()
            .next().value;
        const selectedParkingSpot: ParkingSpotJson | null = this.getParkingSpotByUserId(
            selectedId
        );
        if (selectedParkingSpot == null) {
            return;
        }

        if (selectedParkingSpot.userId === this.state.currentUserId) {
            // mock user signifying a parking spot is not occupied
            selectedParkingSpot.userId = -1;
            this.setState({
                selectedParkingSpotIds: new Set([])
            });
        } else if (selectedParkingSpot.userId === -1) {
            for (let i: number = 0; i < this.state.parkingSpots.length; i++) {
                const spot: ParkingSpotJson = this.state.parkingSpots[i];
                if (spot.userId === this.state.currentUserId) {
                    return;
                }
            }
            console.log("ha");
            selectedParkingSpot.userId = this.state.currentUserId;
            this.setState({
                selectedParkingSpotIds: new Set([])
            });
        }
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
        for (let i: number = 0; i < this.state.parkingSpots.length; i++) {
            const parkingSpot: ParkingSpotJson = this.state.parkingSpots[i];
            if (parkingSpot.userId === userId) {
                return this.state.parkingSpots[i];
            }
        }
        return null;
    }

    getParkingSpotById(parkingSpotId: number): ParkingSpotJson | null {
        for (let i: number = 0; i < this.state.parkingSpots.length; i++) {
            const parkingSpot: ParkingSpotJson = this.state.parkingSpots[i];
            if (parkingSpot.id === parkingSpotId) {
                return this.state.parkingSpots[i];
            }
        }
        return null;
    }

    render() {
        if (this.state.currentUserId !== 0) {
            return (
                <div>
                    <div>{this.renderOptions()}</div>
                    <div className={"parkingAreaContainer"}>
                        {this.renderParkingArea()}
                    </div>
                    <button onClick={this.notifySelectedUsers}>notify</button>
                    <button onClick={this.toggleParking}>Occupy/Free</button>
                </div>
            );
        } else {
            return <div />;
        }
    }
}

export default Main;
