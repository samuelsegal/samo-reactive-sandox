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
class VehicleListEventSource extends Component<VehicleListProps, VehicleListState> {
	constructor(props: VehicleListProps) {
		super(props);
		this.state = {
			vehicles: [],
			isLoading: false,
		};
	}

	/*
	 * Connect an Event Source to the Server Send Event URL /sse/vehicles
	 * THis will receive events as they are published.
	 * Similar to web sockets this provides us updates as they are published
	 * Hence lighter on traffic all around
	 */
	async componentDidMount() {
		this.setState({ isLoading: true });

		const response = await fetch('http://localhost:3000/vehicles');
		const data = await response.json();
		this.setState({ vehicles: data, isLoading: false });

		const eventSource = new EventSource('http://localhost:8080/sse/vehicles');
		eventSource.onopen = (event: any) => console.log('open', event);
		eventSource.onmessage = (event: any) => {
			const vehicle = JSON.parse(event.data).source;
			let updatedList = this.state.vehicles.concat(vehicle);
			console.log(`updated list ${updatedList}`);
			this.setState({ vehicles: updatedList });
		};
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
export default VehicleListEventSource;
