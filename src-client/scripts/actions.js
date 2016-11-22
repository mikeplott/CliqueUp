const Backbone = require('backbone');
const {eventsModel, eventsCollection} = require('./model-coll.js');
const {loginModel, loginCollection} = require('./model-login.js')
const {tokenModel, tokenCollection} = require('./model-gettoken.js')
const {userModel, userCollection} = require('./model-userInfo.js')
const {eventModel, eventCollection} = require('./model-events.js')
const $ = require('jquery')
const STORE = require('./store.js');


var theCrntUser;


const ACTIONS = {

   authenticateUser: function(userDataObj){
        let userMod = new UserModel()
        userMod.set(userDataObj)

        userMod.save().then(function(serverRes){
          location.hash="loginHome"
        })
  },


  fetchAuthToken: function(){

    let token = new tokenModel()

    token.fetch().then(function(){
      let wholeStrng = token.attributes.access_token
      let strngArry = wholeStrng.split('"')
      let theRealToken = strngArry[3].split('"')

      STORE.setStore("token", theRealToken)

      let usr = document.cookie;
      // let usr = window.localStorage.getItem('username')
      console.log(usr)
      STORE.setStore('loginData', usr)

      


    })
  },



  fetchUserData: function(){

      let theData = STORE.getStoreData()
      let myToken = theData.token[0]
      console.log('hopefully')
      let theUserModel = new userCollection(myToken)
      console.log('maybe??')
      theUserModel.fetch().then(function(){
      STORE.setStore("userData", theUserModel.models[0].attributes)
    })
  },

  fetchUserEventColl: function(){
    let theData = STORE.getStoreData()
    let myToken = theData.token[0]
    let events = new eventCollection(myToken)

    events.fetch().then(function(){
      STORE.setStore('userEvents', events.models[0].attributes)
    })
  },

  connectToSocket: function(){

    let ws = new SockJS("/socket")
    STORE.setStore('socket', Stomp.over(ws))
    let socket = STORE.getStoreData()
    socket = socket.socket

    socket.connect({}, this.onSocketConnect)

    },

  onSocketConnect: function(){
    console.log('hey i got here so now what??')
    let socket = STORE.getStoreData()
    socket = socket.socket

    socket.subscribe("/global", this.onChatConnect)


   },


   onChatConnect: function(message){
     console.log("hey idk if this will even display cause it didnt last time")
   },

   onReceivedMessage: function(message){
     let data = JSON.parse(message.body)

     let textBlock = (
        <div>
          <div>
            <img src="profile Pic"/>
          </div>
          <div>
            <h4>text message sent</h4>
          </div>
          <p>time stamp</p>
        </div>
     )
   },

   sendMessage: function(chtMess){
     let socket = STORE.getStoreData()
     console.log(socket)
     let user = socket.loginData
     socket = socket.socket

     let theMess = {
       message: chtMess,
       username: user
     }

     socket.send('/global', {} ,theMess)





   },


  handleUserLogin: function(usrInfo){
    theCrntUser = usrInfo

     let usrLogin = new loginModel()

      usrLogin.set(usrInfo)



      usrLogin.save().then(function(serverRes){


        //  console.log( "tickle me" ,serverRes)

        //  console.log(serverRes)


      })


   },


  changeView: function(viewInput){
      STORE.setStore('currentView', viewInput)
   }
}

module.exports = ACTIONS
