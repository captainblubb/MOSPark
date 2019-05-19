import React from 'react';
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";

class ParkingAreaContainer extends React.Component<{}, {parkingAreas: Array<ParkingAreaJson>}> {
    constructor(props: {}) {
        super(props);
        ParkingAreaContainer.fetchParkingAreas = ParkingAreaContainer.fetchParkingAreas.bind(this);
        this.state = {
            parkingAreas: ParkingAreaContainer.fetchParkingAreas()
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
        return (
            <div>
                {this.createParkingAreas()}
            </div>
        )
    }
}

export default ParkingAreaContainer;