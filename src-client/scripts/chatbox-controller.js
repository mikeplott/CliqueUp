const React = require('react')
const TitleView = require('./title-view.js')
const {HomeView, thisOne} = require('./home-view.js')
const AuthView = require('./auth-view.js')
const GlobalChatView = require('./chatbox-global.js')
const DynamicChatView = require('./chatbox-dynamic.js')



const ChatController = React.createClass({
  render: function(){
    switch(this.props.selctTab){
      case "Global":
        return <GlobalChatView/>
        break;
      case "closed":
        return (<div></div>)
        break;
      default:
        return <DynamicChatView theChannel={this.props.selctTab}/>
        break;
    }
  }
})


module.exports = ChatController;
