import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Main from './components/Main';
import Authentication from './components/Authentication';
import Profile from './components/Profile';

const App: React.FC = () => {
  return (
    <div className="App">
      <Router>
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Authentication/>
              </li>
            </ul>
          </nav>
          <Route path="/" exact component={Main} />
          <Route path="/profile/" component={Profile} />
        </div>
      </Router>
    </div>
  );
};

export default App;
