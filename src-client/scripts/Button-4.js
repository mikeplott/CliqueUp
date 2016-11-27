const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const ACTIONS = require('./actions.js')


class Button4View extends React.Component{

   constructor () {
      super();

   }
   render(){

     var self = this

       let nestingBastards = STORE.getStoreData().findEventsData.data
      // console.log(nestingBastards)
      console.log( STORE.getStoreData())

      let UsefulStuff = nestingBastards.map(function(element, i){



         return(


           <a className="list-group-item" >

              <div className="eventDetailHolder" >
                <h4 className="list-group-item-heading" >{element.name}</h4>
                <p className="list-group-item-text" >{element.group.name}</p>
              </div>
           </a>

         )


      })


      return (


        <div className="list-group">
          {UsefulStuff}
        </div>
      )

   }
}

module.exports = Button4View
