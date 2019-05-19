import React from 'react';

class ParkingSpot extends React.Component<{id: number, occupied: boolean, userId: number}, {occupied: boolean}> {
    constructor(props: {id: number, occupied: boolean, userId: number}) {
        super(props);
    }

    render() {
        return (
            <div>
                {this.props.id} - {this.props.occupied} - {this.props.userId}
            </div>
        )
    }
}

export default ParkingSpot;