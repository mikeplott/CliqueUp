const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const map = document.querySelector("#map")

console.log(map)
var map2;
var marker;

var crntLat = new google.maps.LatLng(32.776, -79.931);

function initMap() {

    navigator.geolocation.getCurrentPosition(function(position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          }



        map2 = new google.maps.Map(document.getElementById('map'), {
          center: pos,
          zoom: 12,
          disableDefaultUI: true,
          styles: [
          {
          "featureType": "all",
          "elementType": "labels",
          "stylers": [
            {
                "visibility": "on"
            }
          ]
          },
          {
          "featureType": "all",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "saturation": 36
            },
            {
                "color": "#000000"
            },
            {
                "lightness": 40
            }
          ]
          },
          {
          "featureType": "all",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
                "visibility": "on"
            },
            {
                "color": "#000000"
            },
            {
                "lightness": 16
            }
          ]
          },
          {
          "featureType": "all",
          "elementType": "labels.icon",
          "stylers": [
            {
                "visibility": "off"
            }
          ]
          },
          {
          "featureType": "administrative",
          "elementType": "geometry.fill",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 20
            }
          ]
          },
          {
          "featureType": "administrative",
          "elementType": "geometry.stroke",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 17
            },
            {
                "weight": 1.2
            }
          ]
          },
          {
          "featureType": "administrative.locality",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "color": "#c4c4c4"
            }
          ]
          },
          {
          "featureType": "administrative.neighborhood",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "color": "#707070"
            }
          ]
          },
          {
          "featureType": "landscape",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 20
            }
          ]
          },
          {
          "featureType": "poi",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 21
            },
            {
                "visibility": "on"
            }
          ]
          },
          {
          "featureType": "poi.business",
          "elementType": "geometry",
          "stylers": [
            {
                "visibility": "on"
            }
          ]
          },
          {
          "featureType": "road.highway",
          "elementType": "geometry.fill",
          "stylers": [
            {
                "color": "#be2026"
            },
            {
                "lightness": "0"
            },
            {
                "visibility": "on"
            }
          ]
          },
          {
          "featureType": "road.highway",
          "elementType": "geometry.stroke",
          "stylers": [
            {
                "visibility": "off"
            }
          ]
          },
          {
          "featureType": "road.highway",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "visibility": "off"
            }
          ]
          },
          {
          "featureType": "road.highway",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
                "visibility": "off"
            },
            {
                "hue": "#ff000a"
            }
          ]
          },
          {
          "featureType": "road.arterial",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 18
            }
          ]
          },
          {
          "featureType": "road.arterial",
          "elementType": "geometry.fill",
          "stylers": [
            {
                "color": "#575757"
            }
          ]
          },
          {
          "featureType": "road.arterial",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "color": "#ffffff"
            }
          ]
          },
          {
          "featureType": "road.arterial",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
                "color": "#2c2c2c"
            }
          ]
          },
          {
          "featureType": "road.local",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 16
            }
          ]
          },
          {
          "featureType": "road.local",
          "elementType": "labels.text.fill",
          "stylers": [
            {
                "color": "#999999"
            }
          ]
          },
          {
          "featureType": "road.local",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
                "saturation": "-52"
            }
          ]
          },
          {
          "featureType": "transit",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#000000"
            },
            {
                "lightness": 19
            }
          ]
          },
          {
          "featureType": "water",
          "elementType": "geometry",
          "stylers": [
            {
                "color": "#141b1f"
            },
            {
                "lightness": 17
            }
          ]
          }
          ]
        });
        // map.setOptions({
        //    disableDoubleClickZoom: true,
        //    navigationControl: false,
        //    mapTypeControl: false,
        //    scaleControl: false,
        //    draggable: false,
        //    scrollwheel: false
        // });
        map2.setTilt(45);
        marker = new google.maps.Marker({
          animation: google.maps.Animation.DROP,
          position: {lat: 32.776475, lng: -79.931051}
        });
      });
    }




const HomeView = React.createClass({

  componentDidMount: function(){
    initMap();

  },

  _placeFirstMarker: function(){
    marker.setMap(map2);
    map2.setZoom(15);
    map2.panTo(marker.getPosition());
  },


  render: function(){

    map.innerHTML = ''



    return(
      <div className="homeScreenHolder">
        <div className="nav nav-bar homeNav">
          <span className="glyphicon glyphicon-option-vertical navMoreBtn"></span>
          <img src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg" className="homeNavPic"/>
        </div>
        <div className="homeMeetupBox" ref="homeMeetupBox">
          <button className="btn btn-warning homeScreenBtn" onClick={this._placeFirstMarker}>Place Marker</button>
        </div>
        <div className="homeChatBox"></div>
      </div>
    )
  }

})


// console.log(google);
//
// console.log('the googs', google)



  // google.maps.event.trigger(map, 'resize');








module.exports = HomeView
