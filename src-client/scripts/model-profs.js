const Backbone = require('backbone')

const selfModel = Backbone.Model.extend({

   url: "",


   initialize: function(theToken, id){
     this.url = "https://api.meetup.com/members/" + id + "/?access_token=" + theToken + ""
   },

   sync : function(method, collection, options) {

     options.dataType = "jsonp";
     return Backbone.sync(method, collection, options);
   }
})


const selfCollection = Backbone.Collection.extend({

   model: selfModel,

   url: "",


   initialize: function(){

   },


})


module.exports = {selfModel, selfCollection}
