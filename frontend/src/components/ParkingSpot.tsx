import React from 'react';

class ParkingSpot extends React.Component<{id: number, occupied: boolean, userId: number}, {selected: boolean}> {
    constructor(props: {id: number, occupied: boolean, userId: number}) {
        super(props);
        this.state = {
            selected: false
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
            <div onClick={this.toggleSelection} className={this.state.selected ? "parkingSpot selected" : "parkingSpot"}>
                {this.props.id} - {this.props.occupied ? "true" : "false"} - {this.props.userId}
            </div>
        )
    }
}

export default ParkingSpot;