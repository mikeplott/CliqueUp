const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')


const AuthView = React.createClass({





  render: function(){



    return(
      <div className="titleScreenHolder">
        <img src="./images/CliqueUpLogo.png" className="titleScreenImg"/>
        <br/>
          <p>For this app to work properly, we need you give us access to your MeetUp Account information. Do so by clicking the button below..</p>
          <a href="/auth">
            <button className="btn btn-alert titleScreenBtn">Authencate</button>
          </a>
      </div>
    )
  }



})



module.exports = AuthView
