import React from 'react';

import ParkingSpot from './ParkingSpot';
import ParkingSpotJson from './ParkingSpotJson';

class ParkingArea extends React.Component<{id: number}, {parkingSpots: Array<ParkingSpotJson>}> {

    constructor(props: {id: number}) {
        super(props);
        ParkingArea.fetchParkingSpots = ParkingArea.fetchParkingSpots.bind(this);
        this.state = {
            parkingSpots: ParkingArea.fetchParkingSpots()
        };

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
        return JSON.parse('[{"id": 0, "occupied": false, "userId": 420},{"id": 1, "occupied": false, "userId": 1},{"id": 2, "occupied": false, "userId": 2},{"id": 3, "occupied": false, "userId": 3}]');
    }

    createParkingSpots(): Array<JSX.Element> {
        let spots: Array<JSX.Element> = [];
        const currentUserIdSession: string | null = sessionStorage.getItem("id");
        const currentUserId: string = currentUserIdSession != null ? currentUserIdSession : "";

        for (let i: number = 0; i < this.state.parkingSpots.length; i++) {
            const currentSpot: ParkingSpotJson = this.state.parkingSpots[i];
            spots.push(<ParkingSpot key={i} id={currentSpot.id} occupied={currentSpot.occupied} userId={currentSpot.userId} occupiedByCurrentUser={currentUserId != ""}/>)
        }
        return spots;
    }

    render() {
        return (
            <div className={"parkingArea"}>
                {this.createParkingSpots()}
            </div>
        )
    }
}

export default ParkingArea;