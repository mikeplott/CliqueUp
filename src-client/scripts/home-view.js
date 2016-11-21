const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const ACTIONS = require('./actions.js')
const map = document.querySelector("#map")



var map2;
var marker;



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

        map2.setTilt(45);
        marker = new google.maps.Marker({
          animation: google.maps.Animation.DROP,
          position: {lat: 32.776475, lng: -79.931051}
        });

        google.maps.event.addListenerOnce(map2, 'idle', function(){
          let theLoader = document.querySelector('.loader')
          theLoader.style.display = "none";
        });
      });
    }




const HomeView = React.createClass({



  componentDidMount: function(){
    ACTIONS.fetchAuthToken()
    setTimeout(function(){
      ACTIONS.fetchUserData()
      ACTIONS.fetchUserEventColl()
    },500)

    initMap();

  },


  _getToken: function(){

    let theSUPERDATA = STORE.getStoreData()

    console.log(theSUPERDATA)


  },


  _toggleMenuDisplay: function(){
    let menuState = STORE.getStoreData()
    console.log(menuState.homeMenuDisplay)
    if(menuState.homeMenuDisplay === false){
      STORE.setStore('homeMenuDisplay', true )
    } else {
        STORE.setStore('homeMenuDisplay', false )
    }
  },

  _placeFirstMarker: function(){
    marker.setMap(map2);
    map2.setZoom(15);
    map2.panTo(marker.getPosition());
  },


  render: function(){

    map.innerHTML = ''

    // <div className="tab-pane fade active in" id="home">
    //   <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.</p>
    // </div>
    // <div className="tab-pane fade" id="profile">
    //   <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.</p>
    // </div>
    // <div className="tab-pane fade" id="dropdown1">
    //   <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade. Messenger bag gentrify pitchfork tattooed craft beer, iphone skateboard locavore carles etsy salvia banksy hoodie helvetica. DIY synth PBR banksy irony. Leggings gentrify squid 8-bit cred pitchfork.</p>
    // </div>
    // <div className="tab-pane fade" id="dropdown2">
    //   <p>Trust fund seitan letterpress, keytar raw denim keffiyeh etsy art party before they sold out master cleanse gluten-free squid scenester freegan cosby sweater. Fanny pack portland seitan DIY, art party locavore wolf cliche high life echo park Austin. Cred vinyl keffiyeh DIY salvia PBR, banh mi before they sold out farm-to-table VHS viral locavore cosby sweater.</p>
    // </div>

    // <ul className="nav nav-tabs nav-justified">
    //   <li role="presentation" className="active">Global</li>
    //   <li role="presentation">More</li>
    //   <li role="presentation">More</li>
    // </ul>
    // <div className="tab-content"></div>
    // <form>
    //   <input className="form-group" type="text"/>
    //   <input type="submit"/>
    // </form>

    // <button className="btn btn-warning homeScreenBtn" onClick={this._placeFirstMarker}>Place Marker</button>
    // <button className="btn btn-warning homeScreenBtn" onClick={this._getToken}>Test Token2</button>

    return(
      <div className="homeScreenHolder">
        <div className="nav nav-bar homeNav">
          <span className="glyphicon glyphicon-option-vertical navMoreBtn" onClick={this._getToken}></span>
          <img src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg" className="homeNavPic"/>
        </div>
        <div className="homeMeetupBox" ref="homeMeetupBox">
          <nav className="navbar navbar-inverse homeMeetupNav">
            <div className="">
              <button className="btn btn-warning homeNavTabs">Find</button>
              <button className="btn btn-warning homeNavTabs">Find</button>
              <button className="btn btn-warning homeNavTabs">Find</button>
              <button className="btn btn-warning homeNavLastTabs">Find</button>
            </div>
          </nav>
        </div>
        <div className="homeChatBox">
          <div>
          <ul className="nav nav-tabs homeChatNav">
            <li><a data-toggle="tab">Global</a></li>
            <li><a data-toggle="tab">+</a></li>
            <li><a data-toggle="tab">V</a></li>
            </ul>
            <div id="myTabContent" className="tab-content">

            </div>
          </div>
        </div>
      </div>
    )
  }

})


// console.log(google);
//
// console.log('the googs', google)



  // google.maps.event.trigger(map, 'resize');








module.exports = HomeView
