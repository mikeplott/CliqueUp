const Backbone = require('backbone')

const eventModel = Backbone.Model.extend({

   url: "",


   initialize: function(){

   },
})


const eventCollection = Backbone.Collection.extend({

   model: eventModel,

   url: "",


   initialize: function(theToken){
     this.url = "https://api.meetup.com/self/events/?access_token=" + theToken + ""
   },

  sync : function(method, collection, options) {

    options.dataType = "jsonp";
    return Backbone.sync(method, collection, options);
}


})


module.exports = {eventModel, eventCollection}
