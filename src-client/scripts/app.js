const ReactDOM = require('react-dom');
const React = require('react')
const Backbone = require('backbone');
const View = require('./view-controller.js')


const AppRouter = Backbone.Router.extend({
  routes: {
    "" : "showTitlePage",
    "homePage" : "showHomePage"
  },

  showTitlePage: function(){
    console.log('test me')
    ReactDOM.render(<View currentView="title"/>, document.querySelector("#ui"))
  },

  showHomePage: function(){

    ReactDOM.render(<View currentView="home"/>, document.querySelector("#ui"))
  },

  initialize: function(){
    Backbone.history.start()
  }


})

new AppRouter()
