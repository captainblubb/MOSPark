import React from 'react';

class ParkingSpot extends React.Component<{id: number, occupied: boolean, userId: number, occupiedByCurrentUser: boolean}, {selected: boolean, classes: string}> {
    constructor(props: {id: number, occupied: boolean, userId: number, occupiedByCurrentUser: boolean}) {
        super(props);
        this.state = {
            selected: false,
            classes: this.props.occupiedByCurrentUser ? "parkingSpot occupiedByUser" : "parkingSpot"
        };

        this.toggleSelection = this.toggleSelection.bind(this);
    }

    toggleSelection(): void {
        this.setState({
            selected: !this.state.selected
        })
    }

    render() {
        return (
            <div onClick={this.toggleSelection} className={this.state.selected ? this.state.classes + " selected" : this.state.classes}>
                {this.props.id} - {this.props.occupied ? "true" : "false"} - {this.props.userId}
            </div>
        )
    }
}

export default ParkingSpot;