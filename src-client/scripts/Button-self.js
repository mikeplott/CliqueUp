const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')


class SelfView extends React.Component{

   constructor () {
      super();

   }


   render(){

      console.log(STORE.getStoreData())
      let nestingBastards = STORE.getStoreData().userEvents.data
      console.log(nestingBastards)

      let UsefulStuff = nestingBastards.map(function(element){
         console.log(element.group.name)

         return(
            <li>
               <div>
                  <h2>{element.name}</h2>
                  <p>{element.group.name}</p>
               </div>
               <div>
                  <h1>{element.yes_rsvp_count}</h1>
               </div>
            </li>
         )


      })


      return (

         <ul className="self-eventsList">
            {UsefulStuff}
         </ul>
      )

   }
}


module.exports = SelfView
