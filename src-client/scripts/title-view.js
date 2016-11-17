const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')

const TitleView = React.createClass({


  render: function(){
    return(
      <div className="titleScreenHolder">
        <img src="./images/CliqueUpLogo.png" className="titleScreenImg"/>
        <br/>
        <a href="/auth">
          <button className="btn btn-warning titleScreenBtn">Login</button>
        </a>
      </div>
    )
  }

})


module.exports = TitleView
