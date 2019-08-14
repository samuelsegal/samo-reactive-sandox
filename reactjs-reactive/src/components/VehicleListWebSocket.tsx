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
/*
 * Use Web Socket and addEventListener to update existing list with new vehicles as they are created.
 */
class VehicleListWebSocket extends Component<VehicleListProps, VehicleListState> {
	constructor(props: VehicleListProps) {
		super(props);
		this.state = {
			vehicles: [],
			isLoading: false,
		};
	}
	async componentDidMount() {
		this.setState({ isLoading: true });
		const headers = {
			headers: { Authorization: 'Bearer ' + (await this.props.auth.getAccessToken()) },
		};
		const response = await fetch('http://localhost:3000/vehicles', headers);

		const data = await response.json();
		this.setState({ vehicles: data, isLoading: false });

		/*
		 * Connect to websocket @/ws/vehicles as defined on webflux server
		 * listen for updates and add the new vehicle created to the existing list
		 * Hence: No Need to grab whole list like in rxjs and poll example
		 */
		const socket = new WebSocket('ws://localhost:3000/ws/vehicles');
		socket.addEventListener('message', async (event: any) => {
			const vehicle_id = JSON.parse(event.data);
			const request = await fetch(`http://localhost:8080/vehicles/api/${vehicle_id.id}`, headers);
			const vehicle = await request.json();
			let updatedList = this.state.vehicles.concat(vehicle);
			console.log(`updated list ${updatedList}`);
			this.setState({ vehicles: updatedList });
		});
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
export default VehicleListWebSocket;
