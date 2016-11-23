const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')


const ChatView = React.createClass({


  componentWillMount: function(){

  },




   render: function(){
     return(
       <div className="homeChatBox">
         <div>
         <ul className="nav nav-tabs homeChatNav">
           <li><a data-toggle="tab">Global</a></li>
           <li><a data-toggle="tab">+</a></li>
           <li><a data-toggle="tab">V</a></li>
         </ul>
           <div id="myTabContent" className="tab-content">
             <div className="tab-pane fade active in">
               <div className="chatboxBody"></div>
               <div className="input-group chatInputBox">
                 <input type="text" className="chatInput form-control" ref="chatMessage"/>
                 <button className="btn btn-warning input-group-addon chatSend" onClick={this._sendChatMessage}>Send</button>
               </div>
             </div>
           </div>
         </div>
       </div>
     )
   }
})


module.exports = ChatView
