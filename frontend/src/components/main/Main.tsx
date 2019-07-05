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
        let fetchedAreas: Array<ParkingAreaJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/parkingareas/all/`)
            .then(result => result.json())
            .then(areas => {
                fetchedAreas = JSON.parse(areas);
            });
        return fetchedAreas;
    }

    static fetchParkingSpots(): Array<ParkingSpotJson> {
        let fetchedSpots: Array<ParkingSpotJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/parkingspots/all`)
            .then(result => result.json())
            .then(spots => {
                fetchedSpots = JSON.parse(spots);
            });
        return fetchedSpots;
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
                    Area {this.state.parkingAreas[i].name}
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
