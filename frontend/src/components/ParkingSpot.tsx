import React from 'react';

class ParkingSpot extends React.Component<{
    id: number,
    occupied: boolean,
    userId: number,
    occupiedByCurrentUser: boolean,
    selectionHandler: (parkingSpotId: number, selected: boolean) => void,
    selected: boolean
}, {
    classes: string
}> {
    constructor(props: {id: number, occupied: boolean, userId: number, occupiedByCurrentUser: boolean, selected: boolean, selectionHandler: () => void}) {
        super(props);
        this.state = {
            classes: this.props.occupiedByCurrentUser ? "parkingSpot occupiedByUser" : "parkingSpot"
        };

        this.toggleSelection = this.toggleSelection.bind(this);
    }

    toggleSelection() {
        this.props.selectionHandler(this.props.id, this.props.selected)
    }

    render() {
        return (
            <div onClick={this.toggleSelection} className={this.props.selected ? this.state.classes + " selected" : this.state.classes}>
                {this.props.id} - {this.props.occupied ? "true" : "false"} - {this.props.userId}
            </div>
        )
    }
}

export default ParkingSpot;