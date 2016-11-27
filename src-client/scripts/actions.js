const Backbone = require('backbone');
const {eventsModel, eventsCollection} = require('./model-coll.js');
const {loginModel, loginCollection} = require('./model-login.js')
const {tokenModel, tokenCollection} = require('./model-gettoken.js')
const {userModel, userCollection} = require('./model-userInfo.js')
const {eventModel, eventCollection} = require('./model-events.js')
const {buttonModel, buttonCollection, button3Coll, button4Coll} = require('./Button-Colls.js')
const $ = require('jquery')
const STORE = require('./store.js');
const MenuView = require('./menu-view.js')



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

      $.getJSON('/user', function(daData){
          STORE.setStore("loginData", daData.username)
      })

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
      ACTIONS.storeEventLocs
    })

  },

  fetchConciergeInfo: function(){

      let theData = STORE.getStoreData()
      let myToken = theData.token[0]

      let thebuttonModel = new buttonCollection(myToken)

      thebuttonModel.fetch().then(function(){
      STORE.setStore("conciergeData", thebuttonModel.models[0].attributes)
    })
  },

  fetchTopics: function(){

      let theData = STORE.getStoreData()
      let myToken = theData.token[0]

      let thebuttonModel = new button3Coll(myToken)

      thebuttonModel.fetch().then(function(){
      STORE.setStore("topics", thebuttonModel.models[0].attributes)
    })
  },

  fetchFindEvents: function(){

      let theData = STORE.getStoreData()
      let myToken = theData.token[0]

      let thebuttonModel = new button4Coll(myToken)

      thebuttonModel.fetch().then(function(){
      STORE.setStore("findEventsData", thebuttonModel.models[0].attributes)
    })
  },

  storeEventLocs: function(){
    let theData = STORE.getStoreData()
    theData = theData.userEvents

    theData.data.map(function(element){

      let pos = {
        lat: element.venue.lat,
        lng: element.venue.lon
      }
    })
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
