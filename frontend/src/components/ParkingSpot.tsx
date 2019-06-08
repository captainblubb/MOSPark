import React from "react";

class ParkingSpot extends React.Component<
    {
        id: number;
        occupied: boolean;
        userId: number;
        occupiedByCurrentUser: boolean;
        selectionHandler: (parkingSpotId: number, selected: boolean) => void;
        selected: boolean;
    },
    {
        classes: string;
    }
> {
    constructor(props: {
        id: number;
        occupied: boolean;
        userId: number;
        occupiedByCurrentUser: boolean;
        selected: boolean;
        selectionHandler: () => void;
    }) {
        super(props);
        this.state = {
            classes:
                "parkingSpot" +
                (this.props.occupiedByCurrentUser ? " occupiedByUser" : "")
        };

        this.toggleSelection = this.toggleSelection.bind(this);
    }

    toggleSelection() {
        this.props.selectionHandler(this.props.id, this.props.selected);
    }

    render() {
        return (
            <div
                onClick={this.toggleSelection}
                className={
                    this.state.classes +
                    (this.props.selected ? " selected" : "")
                }
            >
                {this.props.id} - {this.props.occupied ? "true" : "false"} -{" "}
                {this.props.userId}
            </div>
        );
    }
}

export default ParkingSpot;
