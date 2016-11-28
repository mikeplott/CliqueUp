const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');
const STORE = require('./store.js');

const MoreInfoBox = React.createClass({



  render: function(){
    return(
      <div>
        <h1>MORE INFO</h1>
      </div>
    )
  }

})


module.exports = MoreInfoBox
