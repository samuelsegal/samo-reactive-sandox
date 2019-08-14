import React, { Component } from 'react';

import { Auth } from '../App';
interface Vehicle {
	id: string;
	name: string;
	description: string;
}
interface VehicleListProps {
	auth: Auth;
}
interface VehicleListState {
	vehicles: Array<Vehicle>;
	isLoading: boolean;
}
class VehicleListPoll extends Component<VehicleListProps, VehicleListState> {
	private interval: any;
	constructor(props: VehicleListProps) {
		super(props);
		this.state = {
			vehicles: [],
			isLoading: false,
		};
	}
	/* 	componentDidMount() {
		this.setState({ isLoading: true });
		fetch('//localhost:3000/vehicles')
			.then(response => response.json())
			.then(data => this.setState({ vehicles: data, isLoading: false }));
		const requetr = interva(1000).pipe
	} */
	async fetchVehicles() {
		this.setState({ isLoading: true });

		const response = await fetch('http://localhost:3000/vehicles');
		const vehicles = await response.json();
		this.setState({ vehicles: vehicles, isLoading: false });
	}

	async componentDidMount() {
		await this.fetchVehicles();
		this.interval = setInterval(() => this.fetchVehicles(), 1000);
	}
	componentWillUnmount() {
		clearInterval(this.interval);
	}
	render() {
		const { vehicles, isLoading } = this.state;
		if (isLoading) {
			return <p>Loading...</p>;
		}
		return (
			<div>
				<h2>Vehicles:</h2>
				{vehicles.map((vehicle: Vehicle) => (
					<div key={vehicle.id}>{vehicle.name}</div>
				))}
			</div>
		);
	}
}
export default VehicleListPoll;
