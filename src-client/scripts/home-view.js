const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')

const HomeView = React.createClass({


  render: function(){
    return(
      <div className="homeScreenHolder">
        <div className="nav nav-bar homeNav">
          <span className="glyphicon glyphicon-option-vertical navMoreBtn"></span>
          <img src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg" className="homeNavPic"/>
        </div>
        <div className="homeMeetupBox"></div>
        <div className="homeChatBox"></div>
      </div>
    )
  }

})


module.exports = HomeView
