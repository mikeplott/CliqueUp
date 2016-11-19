const Backbone = require('backbone')

const tokenModel = Backbone.Model.extend({

   url: "/gettoken",


   initialize: function(){
   },
})


const tokenCollection = Backbone.Collection.extend({

   model: tokenModel,

   url: "",

  sync : function(method, collection, options) {

    options.dataType = "jsonp";
    return Backbone.sync(method, collection, options);
}


})


module.exports = {tokenModel, tokenCollection}
