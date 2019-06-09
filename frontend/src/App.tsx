import React from "react";
import "./App.css";

import { BrowserRouter as Router, Route } from "react-router-dom";
import Navigation from "./components/navigation/Navigation";
import Profile from "./components/profile/Profile";
import Main from "./components/main/Main";
import Authentication from "./components/navigation/Authentication";

const App: React.FC = () => {
    const currentSessionUser: string | null = sessionStorage.getItem("user");
    const currentUser: string =
        currentSessionUser != null ? currentSessionUser : "";

    return (
        <div className="App">
            <h1>MOSPark</h1>
            <Router>
                <div>
                    <Navigation />
                    <div className="mainContainer">
                        <Route path="/" exact component={Main} />
                        <Route path="/login/" component={Authentication} />
                        <Route path="/profile/" component={Profile} />
                    </div>
                </div>
            </Router>
        </div>
    );
};

export default App;
