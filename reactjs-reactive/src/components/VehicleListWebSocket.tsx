import React, { Component } from 'react';

interface Vehicle {
	id: string;
	name: string;
	description: string;
}

interface VehicleListProps {}

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

		const response = await fetch('http://localhost:3000/vehicles');
		const data = await response.json();
		this.setState({ vehicles: data, isLoading: false });

		/*
		 * COnnect to websocket @/ws/vehicles as defined on webflux server
		 * listen for updates and add the new vehicle created to the existing list
		 * Hence: No Need to grabwhole list like in rxjs and poll example
		 */
		const socket = new WebSocket('ws://localhost:3000/ws/vehicles');
		socket.addEventListener('message', async (event: any) => {
			console.log('sdfgsdfgbsdfg');
			const vehicle = JSON.parse(event.data);
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
