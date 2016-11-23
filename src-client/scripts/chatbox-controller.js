const React = require('react')
const TitleView = require('./title-view.js')
const HomeView = require('./home-view.js')
const AuthView = require('./auth-view.js')


const ChatView = React.createClass({
  render: function(){
    switch(this.props.currentView){
      case "title":
        return <TitleView/>
        break;
      case "home":
        return <HomeView/>
        break;
      case "auth":
        return <AuthView/>
        break;
      default:

        break;
    }
  }
})


module.exports = ChatView;
