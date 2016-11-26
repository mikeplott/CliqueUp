const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');
const STORE = require('./store.js');

var photoLink

const MenuView = React.createClass({

  componentWillMount: function(){
    let thaData = STORE.getStoreData()

    if(thaData.userData.photo === undefined){
      photoLink = 'http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg'
    } else {
      photoLink = thaData.userData.photo.photo_link
    }

  },

  componentDidMount: function(){
    let self = this
    Backbone.Events.on('picLoad', function(){
      self.forceUpdate()
    })
  },

  _testLogout: function(){
    $.post( "/logout", function(  ) {
      console.log("oooh it tickled the server")
    })
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
  toggleUpdate: function(){
    this.forceUpdate()
  },

  render: function(){
      let thaData = STORE.getStoreData()

      if(thaData.userData.photo === undefined){
        photoLink = 'http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg'
      } else {
        photoLink = thaData.userData.photo.photo_link
      }

    if(thaData.userData.photo === undefined){

      return(<div></div>)
    } else {
      return(
        <div className="nav nav-bar homeNav">
          <button className="btn btn-warning" onClick={this._testLogout}>Logout</button>
          <span className="glyphicon glyphicon-option-vertical navMoreBtn" onClick={this._getToken}></span>
          <img src={photoLink} className="homeNavPic"/>
        </div>
      )
    }


  }

})


module.exports = MenuView
