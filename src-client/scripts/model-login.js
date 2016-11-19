const Backbone = require('backbone')

const loginModel = Backbone.Model.extend({

   url: "/gettoken",


   initialize: function(){
   },
})


const loginCollection = Backbone.Collection.extend({

   model: loginModel,

   url: "",

  sync : function(method, collection, options) {

    options.dataType = "jsonp";
    return Backbone.sync(method, collection, options);
}


})


module.exports = {loginModel, loginCollection}
