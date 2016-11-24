const React = require('react')
const TitleView = require('./title-view.js')
const HomeView = require('./home-view.js')
const AuthView = require('./auth-view.js')
const GlobalChatView = require('./chatbox-global.js')



const ChatController = React.createClass({
  render: function(){
    switch(this.props.selctTab){
      case "Global":
        return <GlobalChatView/>
        break;
      case "home":
        return <HomeView/>
        break;
      case "auth":
        return <AuthView/>
        break;
      default:
        return (<div></div>)
        break;
    }
  }
})


module.exports = ChatController;
