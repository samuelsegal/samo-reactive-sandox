import React from 'react';
import '../App.css';
import VehicleListWebSocket from './VehicleListWebSocket';
import { withAuth } from '@okta/okta-react';
import { Auth } from '../App';
import logo from '../logo.svg';

interface HomeProps {
	auth: Auth;
}

interface HomeState {
	authenticated: boolean;
}
class Home extends React.Component<HomeProps, HomeState> {
	constructor(props: HomeProps) {
		super(props);

		this.state = {
			authenticated: false,
		};
		this.checkAuthentication = this.checkAuthentication.bind(this);
		this.login = this.login.bind(this);
		this.logout = this.logout.bind(this);
	}
	async checkAuthentication() {
		const authenticated = await this.props.auth.isAuthenticated();
		if (authenticated !== this.state.authenticated) {
			this.setState({ authenticated });
		}
	}

	async componentDidMount() {
		await this.checkAuthentication();
	}

	async componentDidUpdate() {
		await this.checkAuthentication();
	}
	async login() {
		this.props.auth.login('/');
	}
	async logout() {
		this.props.auth.logout('/');
	}
	render() {
		const { authenticated } = this.state;

		let body = null;
		if (authenticated) {
			body = (
				<div className="Buttons">
					<button onClick={this.logout}>Logout</button>
					<VehicleListWebSocket auth={this.props.auth} />
				</div>
			);
		} else {
			body = (
				<div className="Buttons">
					<button onClick={this.login}>Login</button>
				</div>
			);
		}
		return (
			<div className="App">
				<header className="App-header">
					<img src={logo} className="App-logo" alt="logo" />

					{body}
				</header>
			</div>
		);
	}
}

export default withAuth(Home);
