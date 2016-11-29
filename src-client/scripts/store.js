const Backbone = require('backbone');
const ACTIONS = require('./actions.js');

const STORE = {
   data:{
     token: '',
     userData: {},
     homeMenuDisplay: false,
     userEvents: {},
     socket: '',
     loginData: {},
     chatGroups: ["Global","test"],
     eventLocs: [],
     conciergeData:{},
     findEventsData:{},
     topics:{},
     usrPhoto: ''

   },

   setStore: function(storeProp, Payload){
      this.data[storeProp] = Payload
      Backbone.Events.trigger('storeChange')
   },

   getStoreData: function(){
      return this.data
   },

   onChange: function(someFunc){
      Backbone.Events.on('storeChange', someFunc)
   }

}

module.exports = STORE
