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
        this.fetchParkingAreas = this.fetchParkingAreas.bind(this);
        this.fetchParkingSpots = this.fetchParkingSpots.bind(this);

        this.state = {
            parkingAreas: [],
            parkingSpots: [],
            currentAreaId: 0,
            currentUserId: Main.getCurrentUserId()
        };

        this.renderParkingArea = this.renderParkingArea.bind(this);
        this.renderAreaChoice = this.renderAreaChoice.bind(this);
        this.changeArea = this.changeArea.bind(this);
        this.reload = this.reload.bind(this)
    }

    componentWillMount() {
        this.fetchParkingAreas();
        this.fetchParkingSpots();
    }

    fetchParkingAreas(): void {
        let fetchedAreas: Array<ParkingAreaJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/parkingarea/all/`)
            .then(result => result.json())
            .then(areas => {
                fetchedAreas = areas;
                this.setState({
                    parkingAreas: fetchedAreas
                });
            });
    }

    fetchParkingSpots(): void {
        let fetchedSpots: Array<ParkingSpotJson> = [];
        fetch(`http://localhost:8080/MOSPark/rest/parkingspots/all`)
            .then(result => result.json())
            .then(spots => {
                fetchedSpots = spots;
                this.setState({
                    parkingAreas: this.state.parkingAreas,
                    parkingSpots: fetchedSpots
                });
            });
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
                reloader={this.reload}
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

    reload() {
        this.setState({
            parkingSpots: this.state.parkingSpots
        })
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
