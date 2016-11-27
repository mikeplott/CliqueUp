const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const ACTIONS = require('./actions.js')

class ConciergeView extends React.Component{

   constructor () {
      super();

   }

   render(){

     var self = this

       let nestingBastards = STORE.getStoreData().conciergeData.results
      // console.log(nestingBastards)
      console.log( STORE.getStoreData().conciergeData)

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

module.exports = ConciergeView
