const Backbone = require('backbone')

const userModel = Backbone.Model.extend({

   url: "",


   initialize: function(theToken){
     url: "https://api.meetup.com/2/member/self/?access_token={" + theToken + "}"
   },
})


const userCollection = Backbone.Collection.extend({

   model: userModel,

   url: "",


   initialize: function(theToken){
     this.url = "https://api.meetup.com/2/member/self/?access_token=" + theToken + ""
   },

  sync : function(method, collection, options) {

    options.dataType = "jsonp";
    return Backbone.sync(method, collection, options);
}


})


module.exports = {userModel, userCollection}
