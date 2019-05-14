import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Main from './components/Main';
import Login from './components/Login';
import Profile from './components/Profile';

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
      </header>
      <Router>
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/login/">Login</Link>
              </li>
              <li>
                <Link to="/profile/">Profile</Link>
              </li>
            </ul>
          </nav>
          <Route path="/" exact component={Main} />
          <Route path="/login/" component={Login} />
          <Route path="/profile/" component={Profile} />
        </div>
      </Router>
    </div>
  );
};

export default App;
