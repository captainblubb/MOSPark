import React from "react";
import "./App.css";

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Authentication from "./components/app/Authentication";
import Profile from "./components/profile/Profile";
import Main from "./components/main/Main";

const App: React.FC = () => {
    return (
        <div className="App">
            <h1>MOSPark</h1>
            <Router>
                <div>
                    <nav>
                        <div>
                            <Link to="/">Home</Link>
                        </div>
                        <div>
                            <Authentication />
                        </div>
                    </nav>
                    <div className="mainContainer">
                        <Route path="/" exact component={Main} />
                        <Route path="/profile/" component={Profile} />
                    </div>
                </div>
            </Router>
        </div>
    );
};

export default App;
