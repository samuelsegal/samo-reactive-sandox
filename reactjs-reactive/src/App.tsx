import React from 'react';
import logo from './logo.svg';
import './App.css';
import VehicleListPoll from './components/VehicleListPoll';
import VehicleListRxJs from './components/VehicleListRxJs';
import VehicleListWebSocket from './components/VehicleListWebSocket';

const App: React.FC = () => {
	return (
		<div className="App">
			<header className="App-header">
				<img src={logo} className="App-logo" alt="logo" />
				{
					//<VehicleListPoll />
					//<VehicleListRxJs />
				}
				<VehicleListWebSocket />
			</header>
		</div>
	);
};

export default App;
