const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')


class SelfView extends React.Component{

   constructor () {
      super();

   }

   _testingwithMike(){
      ACTIONS.fetchUserData()
   }


   render(){
      console.log(STORE.getStoreData())
      return (
         <div className="self-eventsList">
            <h1>hey maybe this will work</h1>
         </div>
      )

   }
}

module.exports = SelfView
