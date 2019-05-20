import React, {ChangeEvent} from 'react';
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";
import ParkingSpotJson from "./ParkingSpotJson";

class Main extends React.Component<{}, {
    parkingAreas: Array<ParkingAreaJson>,
    parkingSpots: Array<ParkingSpotJson>,
    currentAreaId: number,
    selectedUserIds: Array<number>
}> {
    constructor(props: {}) {
        super(props);
        Main.fetchParkingAreas = Main.fetchParkingAreas.bind(this);
        Main.fetchParkingSpots = Main.fetchParkingSpots.bind(this);
        this.state = {
            parkingAreas: Main.fetchParkingAreas(),
            parkingSpots: Main.fetchParkingSpots(),
            currentAreaId: 0,
            selectedUserIds: []
        };

        this.renderParkingArea = this.renderParkingArea.bind(this);
        this.renderOptions = this.renderOptions.bind(this);
        this.changeArea = this.changeArea.bind(this);
        this.handleParkingSpotSelection = this.handleParkingSpotSelection.bind(this);
        this.notifySelectedUsers = this.notifySelectedUsers.bind(this);
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
        return JSON.parse('[{"id": 0, "occupied": false, "userId": 420, "areaId": 0},{"id": 1, "occupied": false, "userId": 1, "areaId": 0},{"id": 2, "occupied": false, "userId": 2,"areaId": 1},{"id": 3, "occupied": false, "userId": 3, "areaId": 1}]');
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
            currentAreaId: parseInt(event.currentTarget.value)
        })
    }

    handleParkingSpotSelection(parkingSpotId: number, selected: boolean): void {
        let selectedUserIds: Array<number> = this.state.selectedUserIds;
        let index: number = 0;
        for (let i: number = 0; i < selectedUserIds.length; i++) {
            if (selectedUserIds[i] === parkingSpotId) {
                index = i;
            }
        }

        if (selected) {
            selectedUserIds.splice(index, 1)
        } else {
            selectedUserIds.push(parkingSpotId);
        }

        this.setState({
            selectedUserIds: selectedUserIds
        })
    }

    notifySelectedUsers(): void {
        alert(this.state.selectedUserIds)
        /*
        fetch(`http:localhost:8080/notifyUsers`, {
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

    render() {
        if (sessionStorage.getItem("user") != null) {
            return (
                <div>
                    <div>
                        {this.renderOptions()}
                    </div>
                    <div className={"parkingAreaContainer"}>
                        {this.renderParkingArea()}
                    </div>
                    <button onClick={this.notifySelectedUsers}>notify</button>
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