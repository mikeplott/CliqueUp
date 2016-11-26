const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const GlobalChatView = require('./chatbox-global.js')
const ChatController = require('./chatbox-controller.js')


const ChatView = React.createClass({


  getInitialState: function(){
    let theView = {
      chatTab: "closed"
    }
    return theView
  },


  componentDidMount: function(){

  },

  _changeTabs: function(evt){
    let self = this

    console.log(evt.target.parentNode)
    for (var ref in this.refs) {
      this.refs[ref].className = ''
    }
    console.log(this.refs)


    if(this.state.chatTab === evt.target.innerHTML){
      evt.target.parentNode.className = ''
      evt.target.className = ''
      self.setState({chatTab: 'closed'})


    } else {
      evt.target.parentNode.className = 'active'
      self.setState({chatTab: evt.target.innerHTML})
    }


  },




   render: function(){
     let self = this

     let daTabData = STORE.getStoreData()

     let chatTabs = daTabData.chatGroups.map(function(name){
       return(
         <li className="" data-tab="tab" ref={name} onClick={self._changeTabs}><a data-toggle="tab">{name}</a></li>
       )
     })






     return(
       <div className="homeChatBox">
         <div>
         <ul className="nav nav-tabs homeChatNav">
           {chatTabs}
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
