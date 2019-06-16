/*global google*/
import React from 'react';
import SearchBox from 'react-google-maps/lib/components/places/SearchBox';
import withScriptjs from "react-google-maps/lib/withScriptjs";
import withGoogleMap from "react-google-maps/lib/withGoogleMap";
import Marker from "react-google-maps/lib/components/Marker";
import GoogleMap from "react-google-maps/lib/components/GoogleMap";
const _ = require("lodash");

class WrappedMapComponent extends React.Component {
    componentWillMount() {
        const refs = {};

        this.setState({
            bounds: null,
            center: {
                lat: 53.888449, lng: 27.544441
            },
            markers: [
                {
                    name: "Current position",
                    position: {
                        lat: 53.888449, lng: 27.544441
                    }
                }
            ],
            onMapMounted: ref => {
                refs.map = ref;
            },
            onBoundsChanged: () => {
                this.setState({
                    bounds: refs.map.getBounds(),
                    center: refs.map.getCenter(),
                })
            },
            onSearchBoxMounted: ref => {
                refs.searchBox = ref;
            },

            onPlacesChanged: () => {
                const places = refs.searchBox.getPlaces();
                const bounds = new google.maps.LatLngBounds();

                places.forEach(place => {
                    if (place.geometry.viewport) {
                        bounds.union(place.geometry.viewport)
                    } else {
                        bounds.extend(place.geometry.location)
                    }
                });
                const nextMarkers = places.map(place => ({
                    position: place.geometry.location,
                }));
                const nextCenter = _.get(nextMarkers, '0.position', this.state.center);
                let lat = nextMarkers.map(x => x.position.lat());
                let lng = nextMarkers.map(x => x.position.lng());

                document.getElementById("latitude").value = lat;
                document.getElementById("longitude").value = lng;

                this.setState({
                    center: nextCenter,
                    markers: nextMarkers,
                });
            },
        })
    }

    render() {
        const {
            onMapMounted,
            onBoundsChanged,
            onSearchBoxMounted,
            bounds,
            center,
            onPlacesChanged,
            markers,
        } = this.state;
        return (
            <GoogleMap
                ref={onMapMounted}
                defaultZoom={15}
                center={center}
                onBoundsChanged={onBoundsChanged}
            >
                <SearchBox
                    ref={onSearchBoxMounted}
                    bounds={bounds}
                    controlPosition={google.maps.ControlPosition.TOP_LEFT}
                    onPlacesChanged={onPlacesChanged}
                >
                    <input
                        id="SearchBox"
                        type="text"
                        placeholder="Search"
                        style={{
                            boxSizing: `border-box`,
                            border: `1px solid transparent`,
                            width: `240px`,
                            height: `32px`,
                            marginTop: `27px`,
                            padding: `0 12px`,
                            borderRadius: `3px`,
                            boxShadow: `0 2px 6px rgba(0, 0, 0, 0.3)`,
                            fontSize: `14px`,
                            outline: `none`,
                            textOverflow: `ellipses`,
                        }}
                    />
                </SearchBox>
                {markers.map((marker, index) =>
                    <Marker key={index}
                            position={marker.position}
                            name={marker.name}
                    />
                )}
            </GoogleMap>
        );
    }
}

const MapComponent = withScriptjs(withGoogleMap(WrappedMapComponent));

MapComponent.defaultProps = {
    googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyB2JLf08WBJ9takbdrl8DQhoS-mBK_XA_0&v=3.exp&libraries=geometry,drawing,places",
    loadingElement:
        <div style={{height: `100%`}}/>,
    containerElement:
        <div style={{height: `400px`}}/>,
    mapElement:
        <div style={{height: `100%`, width: `250%`, padding: `0`}}/>,
};

export default MapComponent;