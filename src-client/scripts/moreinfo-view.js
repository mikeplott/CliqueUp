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
   let daElemement = this.state.boxData


    switch (this.state.boxType){
      case "event":
         let time = daElemement.time
         let fullDate = Date(time)
         let realTime = fullDate

         return (
            <div className="moreInfoViewBox">
               <h3>{daElemement.name}</h3>
               <h5>{daElemement.group.name}</h5>
               <ul>
                <li>{daElemement.status} - {daElemement.visibility}</li>
                <li>{realTime}</li>
                <li>RSVPS: {daElemement.yes_rsvp_count}</li>
                <li>Address: {daElemement.venue.address_1} </li>
               </ul>
               <div dangerouslySetInnerHTML={ {__html: daElemement.description} }></div>
            </div>
         )
         break;
      default :
         return(
            <div className="moreInfoViewBox">
              <h1>Choose an event..</h1>
            </div>
         )
         break;
   }

  }

})


module.exports = MoreInfoBox
