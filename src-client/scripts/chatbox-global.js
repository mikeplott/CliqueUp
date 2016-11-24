const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const $ = require ('jquery')


// var chatMessages = ''


const GlobalChatView = React.createClass({

  getInitialState: function(){
    let theNewState = {
      texts: [],
      socketInfo: ''
    }
    return theNewState
  },


  componentDidMount: function(){
    let self = this

    this.connectToSocket()
    $.getJSON('/chat',{groupname: "global"} ,function(oldChat){
      console.log("hey look here >>>>>>>>>>>>>>>>>>>>>>",oldChat)
      oldChat.map(function(crntMess){

        let theMessgs = self.state.texts

        let textBlock = (
           <div className="well">
             <div className="messPic">
               <p>{crntMess.user.username}</p>
               <img className="chatPics" src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg"/>
             </div>
             <div className="messBox">
               <h4>{crntMess.message}</h4>
             </div>
             <p>12:00</p>
           </div>
        )

        theMessgs.push(textBlock)
        self.setState({text: theMessgs})

      })
    })
  },

  componentWillUnmount: function(){
    this.state.socketInfo.unsubscribe()
  },


  connectToSocket: function(){
    let self = this

    let ws = new SockJS("/socket")
    STORE.setStore('socket', Stomp.over(ws))
    let socket = STORE.getStoreData()
    socket = socket.socket

    socket.connect({}, self.onSocketConnect)


    },

  onSocketConnect: function(){
    let self = this
    let socket = STORE.getStoreData()
    socket = socket.socket

    let unkwnInfo = socket.subscribe("/global", self.onReceivedMessage )

    this.setState({socketInfo: unkwnInfo})




   },


   onChatConnect: function(message){
    //  console.log("hey idk if this will even display cause it didnt last time")
    //  console.log(message, message.body)
    //  console.log(JSON.parse(message.body))
   },

   onReceivedMessage: function(message){
     let data = JSON.parse(message.body)

     console.log(data)
    //  console.log(chatMessages)

     let textBlock = (
        <div className="well">
          <div className="messPic">
            <p>{data.username}</p>
            <img className="chatPics" src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg"/>
          </div>
          <div className="messBox">
            <h4>{data.message}</h4>
          </div>
          <p>12:00</p>
        </div>
     )


    let prevMess = this.state.texts
    // console.log(this.state.texts)
    if(prevMess === []){
      if(data.channel === "global"){
        console.log('i got here')
      } else {
        prevMess.push(textBlock)
        this.setState({texts: prevMess})
      }
    } else {
      if(data.channel === "global"){
        console.log('i got here')
      }else {
        prevMess.push(textBlock)
        this.setState({texts: prevMess})
      }

    }


   },

   sendMessage: function(chtMess){
     let socket = STORE.getStoreData()
    //  console.log(socket)
     let user = socket.loginData
     socket = socket.socket

     let theMess = {
       message: chtMess,
       username: user
     }

     socket.send('/global', {} ,JSON.stringify(theMess))





   },



  _sendChatMessage: function(){
    this.sendMessage(this.refs.chatMessage.value)
    this.refs.chatMessage.value = ''
  },



  render: function(){


    let textCollection = this.state.texts



    return(
      <div className="chatBoxHolder">
        <div className="chatMessageBox">

          {textCollection}
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
