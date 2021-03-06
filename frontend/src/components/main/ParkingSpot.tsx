import React from "react";

class ParkingSpot extends React.Component<
    {
        id: number;
        userId: number;
        currentUserId: number;
        column: number;
        row: number;
        selectionHandler: (parkingSpotId: number) => void;
        selected: boolean;
    },
    {
        classes: string;
        selected: boolean;
        occupied: boolean;
    }
> {
    constructor(props: {
        id: number;
        userId: number;
        currentUserId: number;
        column: number;
        row: number;
        selectionHandler: (parkingSpotId: number) => void;
        selected: boolean;
    }) {
        super(props);
        this.state = {
            classes:
                "parkingSpot" +
                (this.props.userId !== -1 ? " occupied" : "") +
                (this.props.userId === this.props.currentUserId
                    ? " byUser"
                    : "") +
                (this.props.selected ? " selected" : "") +
                (" col" + this.props.column) +
                (" row" +
                    (this.props.column === 1 || this.props.column === 2
                        ? this.props.row + "" + (this.props.row + 1)
                        : this.props.row)),
            selected: this.props.selected,
            occupied: this.props.userId !== -1
        };

        this.toggleSelection = this.toggleSelection.bind(this);
    }

    toggleSelection() {
        this.props.selectionHandler(this.props.id);
        this.setState({
            selected: !this.state.selected
        });
    }

    render() {
        return (
            <div
                onClick={this.toggleSelection}
                className={
                    "parkingSpot" +
                    (this.props.userId !== -1 ? " occupied" : "") +
                    (this.props.userId === this.props.currentUserId
                        ? " byUser"
                        : "") +
                    (this.props.selected ? " selected" : "") +
                    (" col" + this.props.column) +
                    (" row" +
                        (this.props.column === 1 || this.props.column === 2
                            ? this.props.row + "" + (this.props.row + 1)
                            : this.props.row))
                }
            >
                <div>
                    {this.props.userId === this.props.currentUserId
                        ? "You"
                        : ""}
                </div>
            </div>
        );
    }
}

export default ParkingSpot;
