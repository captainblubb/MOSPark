import React from 'react';
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";

class Main extends React.Component<{}, {parkingAreas: Array<ParkingAreaJson>}> {
    constructor(props: {}) {
        super(props);
        Main.fetchParkingAreas = Main.fetchParkingAreas.bind(this);
        this.state = {
            parkingAreas: Main.fetchParkingAreas()
        }
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

    createParkingAreas(): Array<JSX.Element> {
        let areas: Array<JSX.Element> = [];

        for (let i: number = 0; i < this.state.parkingAreas.length; i++) {
            const currentArea: ParkingAreaJson = this.state.parkingAreas[i];
            areas.push(<ParkingArea key={i} id={currentArea.id}/>)
        }
        return areas;
    }

    render() {
        if (sessionStorage.getItem("user") != null) {
            return (
                <div>
                    <div>
                        {this.createParkingAreas()}
                    </div>
                    <div>
                        notify
                    </div>
                </div>
            )
        }
    }
}

export default Main;