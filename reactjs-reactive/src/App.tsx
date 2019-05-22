import React from 'react';
import './App.css';
import Home from './components/Home';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Security, ImplicitCallback } from '@okta/okta-react';
/*
 * ouath2 server configuration for okta
 */
const config = {
	issuer: 'https://dev-548917.okta.com/oauth2/default',
	redirect_uri: window.location.origin + '/implicit/callback',
	client_id: '0oamnkle1ThYQGPix356',
};

export interface Auth {
	login(redirectUri: string): {};
	logout(redirectUri: string): {};
	isAuthenticated(): boolean;
	getAccessToken(): string;
}
const App: React.FC = () => {
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
