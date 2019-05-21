import React, {ChangeEvent} from 'react';
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";
import ParkingSpotJson from "./ParkingSpotJson";

class Main extends React.Component<{}, {
    parkingAreas: Array<ParkingAreaJson>,
    parkingSpots: Array<ParkingSpotJson>,
    currentAreaId: number,
    selectedUserIds: Array<number>,
    currentUserId: number
}> {
    constructor(props: {}) {
        super(props);
        Main.fetchParkingAreas = Main.fetchParkingAreas.bind(this);
        Main.fetchParkingSpots = Main.fetchParkingSpots.bind(this);

        this.state = {
            parkingAreas: Main.fetchParkingAreas(),
            parkingSpots: Main.fetchParkingSpots(),
            currentAreaId: 0,
            selectedUserIds: [],
            currentUserId: Main.getCurrentUserId()
        };

        this.renderParkingArea = this.renderParkingArea.bind(this);
        this.renderOptions = this.renderOptions.bind(this);
        this.changeArea = this.changeArea.bind(this);
        this.handleParkingSpotSelection = this.handleParkingSpotSelection.bind(this);
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
        return JSON.parse('[{"id": 0, "occupied": true, "userId": 420, "areaId": 0},{"id": 1, "occupied": false, "userId": 1, "areaId": 0},{"id": 2, "occupied": false, "userId": 2,"areaId": 1},{"id": 3, "occupied": false, "userId": 3, "areaId": 1}]');
    }

    static getCurrentUserId(): number {
        const currentSessionUserId: string | null = sessionStorage.getItem("id");
        return currentSessionUserId != null ? parseInt(currentSessionUserId) : 0;
    }

    renderParkingArea(): JSX.Element {
        return <ParkingArea id={this.state.currentAreaId}
                            parkingSpots={this.state.parkingSpots}
                            selectionHandler={this.handleParkingSpotSelection}
                            selectedUserIds={this.state.selectedUserIds}/>;
    }

    renderOptions(): JSX.Element {
        let options: Array<JSX.Element> = [];
        for (let i: number = 0; i < this.state.parkingAreas.length; i++) {
            options.push(<option key={i} value={i}>Area {this.state.parkingAreas[i].id}</option>)
        }
        return <select onChange={this.changeArea}>{options}</select>;
    }

    changeArea(event: ChangeEvent<HTMLSelectElement>): void {
        this.setState({
            currentAreaId: parseInt(event.currentTarget.value),
            selectedUserIds: []
        })
    }

    handleParkingSpotSelection(parkingSpotId: number, selected: boolean): void {
        const parkingSpot: ParkingSpotJson | null = this.getParkingSpotById(parkingSpotId);
        if (parkingSpot == null) {
            return
        }

        let selectedUserIds: Array<number> = this.state.selectedUserIds;

        let foundUserIdIndex: number = 0;
        let userFound: boolean = false;
        for (let i: number = 0; i < selectedUserIds.length; i++) {
            if (selectedUserIds[i] === parkingSpot.userId) {
                foundUserIdIndex = i;
                userFound = true;
            }
        }

        if (selected && userFound) {
            selectedUserIds.splice(foundUserIdIndex, 1)
        } else if (!selected && !userFound) {
            selectedUserIds.push(parkingSpot.userId);
        }

        this.setState({
            selectedUserIds: selectedUserIds
        });
        console.log("selected user ids: " + this.state.selectedUserIds);
    }

    notifySelectedUsers(): void {
        alert(this.state.selectedUserIds)
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
        if (this.state.selectedUserIds.length > 1) {
            return
        }

        const selectedParkingSpot: ParkingSpotJson | null = this.getParkingSpotByUserId(this.state.selectedUserIds[0]);
        if (selectedParkingSpot == null) {
            return;
        }
        if (selectedParkingSpot.occupied && selectedParkingSpot.userId === this.state.currentUserId) {
            selectedParkingSpot.occupied = !selectedParkingSpot.occupied;
            // mock user signifying a parking spot is not occupied
            selectedParkingSpot.userId = 0;
            this.setState({
                selectedUserIds: []
            })
        } else if (!selectedParkingSpot.occupied && selectedParkingSpot.userId === 0) {
            selectedParkingSpot.occupied = !selectedParkingSpot.occupied;
            selectedParkingSpot.userId = this.state.currentUserId;
            this.setState({
                selectedUserIds: []
            })
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
                    <div>
                        {this.renderOptions()}
                    </div>
                    <div className={"parkingAreaContainer"}>
                        {this.renderParkingArea()}
                    </div>
                    <button onClick={this.notifySelectedUsers}>notify</button>
                    <button onClick={this.toggleParking}>Occupy/Free</button>
                </div>
            )
        } else {
            return (
                <div>

                </div>
            )
        }
    }
}

export default Main;