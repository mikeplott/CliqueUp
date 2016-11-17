const Backbone = require('backbone');
const STORE = require('./store.js');


const ACTIONS = {

   authenticateUser: function(userDataObj){
        let userMod = new UserModel()
        userMod.set(userDataObj)

        userMod.save().then(function(serverRes){
          location.hash="loginHome"
        })
  },

  fetchUserEventColl: function(){

  },

  createNewmsg: function(){


   })

 },


   changeView: function(viewInput){
      STORE.setStore('currentView', viewInput)
   }
}

module.exports = ACTIONS
