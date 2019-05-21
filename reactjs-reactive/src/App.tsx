import React from 'react';
import logo from './logo.svg';
import './App.css';
import VehicleListPoll from './components/VehicleListPoll';
import VehicleListRxJs from './components/VehicleListRxJs';

const App: React.FC = () => {
	return (
		<div className="App">
			<header className="App-header">
				<img src={logo} className="App-logo" alt="logo" />
				{
					//<VehicleListPoll />
				}
				<VehicleListRxJs />
			</header>
		</div>
	);
};

export default App;
