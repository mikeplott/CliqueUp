const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');

const TitleView = React.createClass({

  _testFunction: function(){
    ACTIONS.fetchUserEventColl()
  },

  _handleClick: function(){

    let userLogin = {
      username: this.refs.username.value,
      password: this.refs.password.value
    }
    console.log(userLogin)

     ACTIONS.handleUserLogin(userLogin)

  },


  render: function(){
    return(
      <div className="titleScreenHolder">
        <img src="./images/CliqueUpLogo.png" className="titleScreenImg"/>
        <br/>
        <form>
          <input ref="username" type="text" placeholder="E-mail"/>
          <br/>
          <input ref="password" type="text" placeholder="Password"/>
          <br/>

          <a href="#auth">
            <button className="btn btn-warning titleScreenBtn" onClick={this._handleClick}>Login</button>
          </a>
        </form>
          <button className="btn btn-warning titleScreenBtn" onClick={this._handleClick}>Login no reroute</button>
        <button className="btn btn-warning titleScreenBtn" onClick={this._testFunction}>Test Token 10</button>
      </div>
    )
  }

})


module.exports = TitleView
