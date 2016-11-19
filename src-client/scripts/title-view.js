const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');

const TitleView = React.createClass({

  _testFunction: function(){
    ACTIONS.fetchUserEventColl()
  },


  render: function(){
    return(
      <div className="titleScreenHolder">
        <img src="./images/CliqueUpLogo.png" className="titleScreenImg"/>
        <br/>
        <a href="/#homePage">
          <button className="btn btn-warning titleScreenBtn">Login</button>

        </a>
        <button className="btn btn-warning titleScreenBtn" onClick={this._testFunction}>Test Token 8</button>
      </div>
    )
  }

})


module.exports = TitleView
