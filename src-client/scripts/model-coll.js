const Backbone = require('backbone')

const eventsModel = Backbone.Model.extend({

   url: "",


   initialize: function(){
   },
})


const eventsCollection = Backbone.Collection.extend({

   model: eventsModel,
   // set route to get route on java side
   url: "https://api.meetup.com/self/events?photo-host=public&page=20&sig_id=216728604&sig=577c73ea04e56a32f36b6b862e3d6562a9ab99e6",
//ask travis for better maybe "?callback=?"
  sync : function(method, collection, options) {

    options.dataType = "jsonp";
    return Backbone.sync(method, collection, options);
}


})


module.exports = {eventsModel, eventsCollection}
