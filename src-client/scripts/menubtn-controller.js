const React = require('react')
const MoreInfoBox = require('./moreinfo-view.js')
const $ = require('jquery')


const MenuBtnView = React.createClass({
  _testLogout: function(){
    $.post( "/logout", function(  ) {
      window.location = 'http://127.0.0.1:8080'
    })
  },
  _theFriends: function(){
    $.getJSON("/friends", function(frans){
      STORE.setStore('onlineFrans', frans)
      Backbone.Events.trigger('openBox', {
         name: 'passData',
         json: {data: frans, type: "friends"}
      })
    })
  },
  render: function(){
    switch(this.props.menuDisplay){
      case "closed":
        return (
          <div></div>
        )
        break;
      default:
        return (
          <div>
            <div>
              <span className="fa fa-bell menuBtns" aria-hidden="true"></span>
              <span onClick={this._theFriends} className="fa fa-user-plus menuBtns" aria-hidden="true"></span>
              <span className="fa fa-cog menuBtns" aria-hidden="true"></span>
              <span onClick={this._testLogout} className="fa fa-sign-out menuBtns" aria-hidden="true"></span>
            </div>
          </div>

        )
        break;
    }
  }
})


  module.exports = MenuBtnView;
