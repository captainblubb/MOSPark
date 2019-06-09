import React from "react";
import "../App.css";

class ParkingSpot extends React.Component<
    {
        id: number;
        userId: number;
        column: number;
        row: number;
        occupiedByCurrentUser: boolean;
        selectionHandler: (parkingSpotId: number) => void;
        selected: boolean;
    },
    {
        classes: string;
        selected: boolean;
    }
> {
    constructor(props: {
        id: number;
        userId: number;
        column: number;
        row: number;
        occupiedByCurrentUser: boolean;
        selected: boolean;
        selectionHandler: (parkingSpotId: number) => void;
    }) {
        super(props);
        this.state = {
            classes:
                "parkingSpot" +
                (this.props.occupiedByCurrentUser ? " occupiedByUser" : ""),
            selected: this.props.selected
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
                    this.state.classes +
                    (this.state.selected ? " selected" : "")
                }
            >
                <div>{this.props.id}</div>
                <div>{this.props.userId === -1 ? "free" : "occ"}</div>
                <div>({this.props.userId})</div>
                <div>{this.props.selected ? "selected" : ""}</div>
            </div>
        );
    }
}

export default ParkingSpot;
