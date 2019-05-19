import React, {ChangeEvent} from 'react';
import ParkingArea from "./ParkingArea";
import ParkingAreaJson from "./ParkingAreaJson";

class Main extends React.Component<{}, {parkingAreas: Array<ParkingAreaJson>, currentAreaId: number}> {
    constructor(props: {}) {
        super(props);
        Main.fetchParkingAreas = Main.fetchParkingAreas.bind(this);
        this.state = {
            parkingAreas: Main.fetchParkingAreas(),
            currentAreaId: 0
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

    renderParkingArea(): JSX.Element {
        return <ParkingArea id={this.state.currentAreaId}/>;
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
                    <div>
                        notify
                    </div>
                </div>
            )
        }
    }
}

export default Main;