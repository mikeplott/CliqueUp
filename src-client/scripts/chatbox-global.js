const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')


const GlobalChatView = React.createClass({



  _sendChatMessage: function(){
    ACTIONS.sendMessage(this.refs.chatMessage.value)
    this.refs.chatMessage.value = ''
  },



  render: function(){



    return(
      <div className="chatBoxHolder">
        <div className="chatMessageBox">
          {"textCollection"}
        </div>
        <div className="input-group chatInputBox">
          <input type="text" className="chatInput form-control" ref="chatMessage"/>
          <button className="btn btn-warning input-group-addon chatSend" onClick={this._sendChatMessage}>Send</button>
        </div>
      </div>
    )
  }



})



module.exports = GlobalChatView


//
// connectToSocket: function(){
//
//   let ws = new SockJS("/socket")
//   STORE.setStore('socket', Stomp.over(ws))
//   let socket = STORE.getStoreData()
//   socket = socket.socket
//
//   socket.connect({}, ACTIONS.onSocketConnect)
//
//
//   },
//
// onSocketConnect: function(){
//   console.log('hey i got here so now what??')
//   let socket = STORE.getStoreData()
//   socket = socket.socket
//
//   socket.subscribe("/global", ACTIONS.onChatConnect   )
//
//
//
//  },
//
//
//  onChatConnect: function(message){
//   //  console.log("hey idk if this will even display cause it didnt last time")
//    console.log(message, message.body)
//    console.log(JSON.parse(message.body))
//  },
//
//  onReceivedMessage: function(message){
//    let data = JSON.parse(message.body)
//
//    let textBlock = (
//       <div>
//         <div>
//           <img src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg"/>
//         </div>
//         <div>
//           <h4>{data.message}</h4>
//         </div>
//         <p>12:00</p>
//       </div>
//    )
//
//
//  },
//
//  sendMessage: function(chtMess){
//    let socket = STORE.getStoreData()
//    console.log(socket)
//    let user = socket.loginData
//    socket = socket.socket
//
//    let theMess = {
//      message: chtMess,
//      username: user
//    }
//
//    socket.send('/global', {} ,JSON.stringify(theMess))
//
//
//
//
//
//  },
