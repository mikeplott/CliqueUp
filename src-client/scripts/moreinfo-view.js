const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');
const STORE = require('./store.js');

const MoreInfoBox = React.createClass({

   getInitialState: function(){
     let theView = {
      boxType: "",
      boxData: {}
     }
     return theView
   },


   componentDidMount: function(){
      let self = this
      Backbone.Events.on('passData', function(data){
         let type = data.type
         let theData = data.data

         self.setState({
            boxType: type,
            boxData: theData
         })

      })
   },



  render: function(){

    switch (this.state.boxType){
      case "event":
         return (
            <div className="moreInfoViewBox">
               <h1>see it its working</h1>
               <h1>see it its working</h1>
            </div>
         )
         break;
      default :
         return(
            <div className="nope-try-again"></div>
         )
         break;
   }

  }

})


module.exports = MoreInfoBox
