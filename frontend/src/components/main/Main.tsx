import React from "react";
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
        this.renderAreaChoice = this.renderAreaChoice.bind(this);
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
        const jsonString: string[] = [
            '{"id": 0, "userId": 420, "areaId": 0, "column": 0, "row": 0},',
            '{"id": 1, "userId": 2, "areaId": 0, "column": 0, "row": 1},',
            '{"id": 2, "userId": 3, "areaId": 0, "column": 0, "row": 2},',
            '{"id": 3, "userId": -1, "areaId": 0, "column": 0, "row": 3},',
            '{"id": 4, "userId": -1, "areaId": 0, "column": 0, "row": 4},',
            '{"id": 5, "userId": -1, "areaId": 0, "column": 0, "row": 5},',
            '{"id": 6, "userId": -1, "areaId": 0, "column": 0, "row": 6},',
            '{"id": 7, "userId": -1, "areaId": 0, "column": 0, "row": 7},',
            '{"id": 8, "userId": -1, "areaId": 0, "column": 0, "row": 8},',
            '{"id": 9, "userId": 3, "areaId": 0, "column": 0, "row": 9},',
            '{"id": 10, "userId": -1, "areaId": 0, "column": 1, "row": 0},',
            '{"id": 11, "userId": -1, "areaId": 0, "column": 1, "row": 1},',
            '{"id": 12, "userId": -1, "areaId": 0, "column": 1, "row": 2},',
            '{"id": 13, "userId": -1, "areaId": 0, "column": 1, "row": 3},',
            '{"id": 14, "userId": -1, "areaId": 0, "column": 1, "row": 4},',
            '{"id": 15, "userId": -1, "areaId": 0, "column": 2, "row": 0},',
            '{"id": 16, "userId": -1, "areaId": 0, "column": 2, "row": 1},',
            '{"id": 17, "userId": -1, "areaId": 0, "column": 2, "row": 2},',
            '{"id": 18, "userId": -1, "areaId": 0, "column": 2, "row": 3},',
            '{"id": 19, "userId": -1, "areaId": 0, "column": 2, "row": 4},',
            '{"id": 20, "userId": -1, "areaId": 0, "column": 3, "row": 0},',
            '{"id": 21, "userId": -1, "areaId": 0, "column": 3, "row": 1},',
            '{"id": 22, "userId": -1, "areaId": 0, "column": 3, "row": 2},',
            '{"id": 23, "userId": -1, "areaId": 0, "column": 3, "row": 3},',
            '{"id": 24, "userId": -1, "areaId": 0, "column": 3, "row": 4},',
            '{"id": 25, "userId": -1, "areaId": 0, "column": 3, "row": 5},',
            '{"id": 26, "userId": -1, "areaId": 0, "column": 3, "row": 6},',
            '{"id": 27, "userId": -1, "areaId": 0, "column": 3, "row": 7},',
            '{"id": 28, "userId": -1, "areaId": 0, "column": 3, "row": 8},',
            '{"id": 29, "userId": -1, "areaId": 0, "column": 3, "row": 9},',
            '{"id": 30, "userId": 20, "areaId": 1, "column": 0, "row": 0},',
            '{"id": 31, "userId": 12, "areaId": 1, "column": 0, "row": 1},',
            '{"id": 32, "userId": 13, "areaId": 1, "column": 0, "row": 2},',
            '{"id": 33, "userId": -1, "areaId": 1, "column": 0, "row": 3},',
            '{"id": 34, "userId": -1, "areaId": 1, "column": 0, "row": 4},',
            '{"id": 35, "userId": -1, "areaId": 1, "column": 0, "row": 5},',
            '{"id": 36, "userId": 5, "areaId": 1, "column": 0, "row": 6},',
            '{"id": 37, "userId": -1, "areaId": 1, "column": 0, "row": 7},',
            '{"id": 38, "userId": -1, "areaId": 1, "column": 0, "row": 8},',
            '{"id": 39, "userId": 6, "areaId": 1, "column": 0, "row": 9},',
            '{"id": 40, "userId": -1, "areaId": 1, "column": 1, "row": 0},',
            '{"id": 41, "userId": -1, "areaId": 1, "column": 1, "row": 1},',
            '{"id": 42, "userId": -1, "areaId": 1, "column": 1, "row": 2},',
            '{"id": 43, "userId": -1, "areaId": 1, "column": 1, "row": 3},',
            '{"id": 44, "userId": -1, "areaId": 1, "column": 1, "row": 4},',
            '{"id": 45, "userId": -1, "areaId": 1, "column": 2, "row": 0},',
            '{"id": 46, "userId": -1, "areaId": 1, "column": 2, "row": 1},',
            '{"id": 47, "userId": -1, "areaId": 1, "column": 2, "row": 2},',
            '{"id": 48, "userId": -1, "areaId": 1, "column": 2, "row": 3},',
            '{"id": 49, "userId": -1, "areaId": 1, "column": 2, "row": 4},',
            '{"id": 50, "userId": -1, "areaId": 1, "column": 3, "row": 0},',
            '{"id": 51, "userId": 9, "areaId": 1, "column": 3, "row": 1},',
            '{"id": 52, "userId": 7, "areaId": 1, "column": 3, "row": 2},',
            '{"id": 53, "userId": -1, "areaId": 1, "column": 3, "row": 3},',
            '{"id": 54, "userId": -1, "areaId": 1, "column": 3, "row": 4},',
            '{"id": 55, "userId": -1, "areaId": 1, "column": 3, "row": 5},',
            '{"id": 56, "userId": -1, "areaId": 1, "column": 3, "row": 6},',
            '{"id": 57, "userId": -1, "areaId": 1, "column": 3, "row": 7},',
            '{"id": 58, "userId": -1, "areaId": 1, "column": 3, "row": 8},',
            '{"id": 59, "userId": -1, "areaId": 1, "column": 3, "row": 9}'
        ];

        return JSON.parse("[" + jsonString.join("") + "]");
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

    renderAreaChoice(): JSX.Element {
        let areas: Array<JSX.Element> = [];
        for (let i: number = 0; i < this.state.parkingAreas.length; i++) {
            areas.push(
                <a key={i} onClick={this.changeArea}>
                    Area {this.state.parkingAreas[i].id}
                </a>
            );
        }
        return <div className={"areaChoiceContainer"}>{areas}</div>;
    }

    changeArea(event: React.MouseEvent<HTMLAnchorElement>): void {
        const id: number = parseInt(
            event.currentTarget.innerHTML.split(" ")[1]
        );
        this.setState({
            currentAreaId: id
        });
    }

    render() {
        if (this.state.currentUserId !== 0) {
            return (
                <div className={"parkingAreaContainer"}>
                    <div>{this.renderAreaChoice()}</div>
                    <div>{this.renderParkingArea()}</div>
                </div>
            );
        } else {
            return <div />;
        }
    }
}

export default Main;
