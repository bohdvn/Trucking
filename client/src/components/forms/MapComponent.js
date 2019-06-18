/*global google*/
import React from 'react';
import SearchBox from 'react-google-maps/lib/components/places/SearchBox';
import withScriptjs from "react-google-maps/lib/withScriptjs";
import withGoogleMap from "react-google-maps/lib/withGoogleMap";
import Marker from "react-google-maps/lib/components/Marker";
import GoogleMap from "react-google-maps/lib/components/GoogleMap";
import reactTriggerChange from "react-trigger-change";
import _ from "lodash"

class WrappedMapComponent extends React.Component {
    componentWillMount() {
        const refs = {};

        this.setState({
            bounds: null,
            center: {
                lat: parseFloat(this.props.lat) || 53.888449,
                lng: parseFloat(this.props.lng) || 27.544441
            },
            markers: [
                {
                    name: "Current position",
                    position: {
                        lat: parseFloat(this.props.lat),
                        lng: parseFloat(this.props.lng)
                    },
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

            handleClick: (event) => {
                let lat = event.latLng.lat();
                let lng = event.latLng.lng();
                document.getElementById("latitude").value = lat;
                reactTriggerChange(document.getElementById("latitude"));
                document.getElementById("longitude").value = lng;
                reactTriggerChange(document.getElementById("longitude"));
                this.setState({
                    markers: [{position: {lat: parseFloat(lat), lng: parseFloat(lng)}}],
                });
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
                reactTriggerChange(document.getElementById("latitude"));
                document.getElementById("longitude").value = lng;
                reactTriggerChange(document.getElementById("longitude"));


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
                zoom={10}
                center={center}
                onBoundsChanged={onBoundsChanged}
                onClick={(e) => this.state.handleClick(e)}
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
                {markers.map((marker, index) => (
                    <Marker key={index}
                            position={marker.position}
                            draggable={true}
                            onDragEnd={(e) => this.state.handleClick(e)}
                            name={marker.name}
                    />
                ))}
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