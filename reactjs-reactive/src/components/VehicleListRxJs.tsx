import React, { Component } from 'react';
import { interval } from 'rxjs';
import { switchMap, startWith } from 'rxjs/operators';
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
class VehicleListRxJs extends Component<VehicleListProps, VehicleListState> {
	constructor(props: VehicleListProps) {
		super(props);
		this.state = {
			vehicles: [],
			isLoading: false,
		};
	}
	async componentDidMount() {
		this.setState({ isLoading: true });
		const request = interval(1000).pipe(
			startWith(0),
			switchMap(async () => fetch('http://localhost:3000/vehicles').then(response => response.json()))
		);
		request.subscribe(data => {
			this.setState({ vehicles: data, isLoading: false });
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
export default VehicleListRxJs;
