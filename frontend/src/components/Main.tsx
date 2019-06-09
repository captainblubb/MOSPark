import React, { ChangeEvent } from "react";
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";
import ParkingSpotJson from "./ParkingSpotJson";

class Main extends React.Component<
    {},
    {
        parkingAreas: Array<ParkingAreaJson>;
        parkingSpots: Array<ParkingSpotJson>;
        currentAreaId: number;
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
            currentUserId: Main.getCurrentUserId()
        };

        this.renderParkingArea = this.renderParkingArea.bind(this);
        this.renderOptions = this.renderOptions.bind(this);
        this.changeArea = this.changeArea.bind(this);
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
            '[{"id": 0, "userId": 420, "areaId": 0, "column": 0, "row": 0},{"id": 1, "userId": 2, "areaId": 0, "column": 0, "row": 1},{"id": 2, "userId": -1, "areaId": 0, "column": 0, "row": 2},{"id": 3, "userId": -1, "areaId": 0, "column": 1, "row": 0},{"id": 4, "userId": -1, "areaId": 0, "column": 2, "row": 0},{"id": 5, "userId": -1, "areaId": 0, "column": 3, "row": 0},{"id": 6, "userId": -1, "areaId": 0, "column": 3, "row": 1},{"id": 7, "userId": -1, "areaId": 0, "column": 3, "row": 2}]'
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
        let areaParkingSpots: Array<ParkingSpotJson> = [];
        for (let i: number = 0; i < this.state.parkingSpots.length; i++) {
            if (
                this.state.parkingSpots[i].areaId === this.state.currentAreaId
            ) {
                areaParkingSpots.push(this.state.parkingSpots[i]);
            }
        }
        return (
            <ParkingArea
                key={this.state.currentAreaId}
                id={this.state.currentAreaId}
                parkingSpots={areaParkingSpots}
                currentUserId={this.state.currentUserId}
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
            currentAreaId: parseInt(event.currentTarget.value)
        });
    }

    render() {
        if (this.state.currentUserId !== 0) {
            return (
                <div>
                    <div>{this.renderOptions()}</div>
                    <div className={"parkingAreaContainer"}>
                        {this.renderParkingArea()}
                    </div>
                </div>
            );
        } else {
            return <div />;
        }
    }
}

export default Main;
