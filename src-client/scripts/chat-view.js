const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const GlobalChatView = require('./chatbox-global.js')
const ChatController = require('./chatbox-controller.js')


const ChatView = React.createClass({


  getInitialState: function(){
    let theView = {
      chatTab: ""
    }
    return theView
  },


  componentDidMount: function(){

  },

  _changeTabs: function(evt){
    let self = this

    console.log(evt.target.parentNode)

    if(this.state.chatTab === evt.target.innerHTML){
      evt.target.parentNode.className = ''
      self.setState({chatTab: ''})
      

    } else {
      evt.target.parentNode.className = 'active'
      self.setState({chatTab: evt.target.innerHTML})
    }


  },




   render: function(){
     return(
       <div className="homeChatBox">
         <div>
         <ul className="nav nav-tabs homeChatNav">
           <li className="" onClick={this._changeTabs} ref="global"><a data-toggle="tab">Global</a></li>
         </ul>
           <div id="myTabContent" className="tab-content">
              <ChatController selctTab={this.state.chatTab}/>
           </div>
         </div>
       </div>
     )
   }
})


module.exports = ChatView



// <div className="tab-pane fade active in">
//   <div className="chatboxBody"></div>
//   <div className="input-group chatInputBox">
//     <input type="text" className="chatInput form-control" ref="chatMessage"/>
//     <button className="btn btn-warning input-group-addon chatSend" onClick={this._sendChatMessage}>Send</button>
//   </div>
// </div>
