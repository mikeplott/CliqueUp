const Backbone = require('backbone')

const buttonModel = Backbone.Model.extend({
   url: "",

   initialize:function(){
   },
})

const buttonCollection = Backbone.Collection.extend({
   model:  buttonModel,
   url: "" ,

   initialize:function(theToken){
      this.url = "https://api.meetup.com/2/concierge/?access_token=" + theToken + ""
   },

   sync:function(method, collection, options){
      options.dataType="jsonp";
      return Backbone.sync(method,collection,options);
   }
})

const button3Coll = Backbone.Collection.extend({
   model:  buttonModel,
   url: "" ,

   initialize:function(theToken){
      
      this.url = "https://api.meetup.com/2/categories/?access_token=" + theToken + ""
   },

   sync:function(method, collection, options){
      options.dataType="jsonp";
      return Backbone.sync(method,collection,options);
   }
})

const button4Coll = Backbone.Collection.extend({
   model:  buttonModel,
   url: "" ,

   initialize:function(theToken){
      this.url = "https://api.meetup.com/find/events/?access_token=" + theToken + ""
   },

   sync:function(method, collection, options){
      options.dataType="jsonp";
      return Backbone.sync(method,collection,options);
   }
})

module.exports = {buttonModel, buttonCollection, button3Coll, button4Coll}
