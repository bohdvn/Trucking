/*global google*/
import React from 'react';
import {
    withGoogleMap,
    GoogleMap,
    withScriptjs,
    Marker,
    DirectionsRenderer
} from "react-google-maps";

class MapDirectionsRenderer extends React.Component {
    state = {
        directions: null,
        error: null
    };

    componentDidMount() {
        const {origin, destination, waypoints, travelMode} = this.props;

        console.log(origin);
        const directionsService = new google.maps.DirectionsService();
        directionsService.route(
            {
                origin: origin,
                destination: destination,
                travelMode: travelMode,
                waypoints: waypoints
            },
            (result, status) => {
                if (status === google.maps.DirectionsStatus.OK) {
                    this.setState({
                        directions: result
                    });
                } else {
                    this.setState({error: result});
                }
            }
        );
    }

    render() {
        if (this.state.error) {
            return <h1>{this.state.error}</h1>;
        }
        return (this.state.directions && <DirectionsRenderer directions={this.state.directions}/>)
    }
}

class WrappedRouteComponent extends React.Component {
    state = {
        waypoints: this.props.checkpoints.map(p => ({
            location: {lat: parseFloat(p.latitude), lng: parseFloat(p.longitude)},
            stopover: true
        })),
    };

    render() {
        return (
            <GoogleMap
                zoom={10}
                center={this.props.origin}
            >
                {this.props.checkpoints.map((marker, index) => {
                    const position = {lat: parseFloat(marker.latitude), lng: parseFloat(marker.longitude)};
                    return <Marker key={index} position={position}/>;
                })}
                <MapDirectionsRenderer
                    origin={this.props.origin}
                    destination={this.props.destination}
                    waypoints={this.state.waypoints}
                    travelMode={google.maps.TravelMode.DRIVING}
                />
            </GoogleMap>
        );
    }
}

const TempRouteComponent = withScriptjs(withGoogleMap(WrappedRouteComponent));

TempRouteComponent.defaultProps = {
    googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyB2JLf08WBJ9takbdrl8DQhoS-mBK_XA_0&v=3.exp&libraries=geometry,drawing,places",
    loadingElement:
        <div style={{height: `100%`}}/>,
    containerElement:
        <div style={{height: `400px`}}/>,
    mapElement:
        <div style={{height: `100%`, width: `100%`, padding: `0`}}/>,
};

export default TempRouteComponent;