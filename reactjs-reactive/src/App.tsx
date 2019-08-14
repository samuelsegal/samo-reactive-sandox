import React from 'react';
import './App.css';
import Home from './components/Home';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Security, ImplicitCallback } from '@okta/okta-react';
/*
 * ouath2 server configuration for okta
 */
const config = {
	issuer: process.env.REACT_APP_OKTA_ISSUER_URI, //dev-548917.okta.com/oauth2/default',
	redirect_uri: window.location.origin + '/implicit/callback',
	client_id: process.env.REACT_APP_SPRING_REACTIVE_SANDBOX_CLIENT_ID,
};

export interface Auth {
	login(redirectUri: string): {};
	logout(redirectUri: string): {};
	isAuthenticated(): boolean;
	getAccessToken(): string;
}

const App: React.FC = () => {
	console.log('WTF');
	console.log(`${config.issuer}`);
	return (
		<Router>
			<Security {...config}>
				<Route path="/" exact={true} component={Home} />
				<Route path="/implicit/callback" component={ImplicitCallback} />
			</Security>
		</Router>
	);
};

export default App;
