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

             let UsefulStuff = nestingBastards.map(function(element, i){
                // console.log(i)
                // console.log(element.group.name)
                // console.log(element.venue, "look here please little one")
                let daData = STORE.getStoreData()
                daData = daData.eventLocs

                let pos = {}

                if (element.venue === undefined){
                   pos = {lat: 32.776475, lng: -79.931051}
                } else {
                   pos = {
                    lat: element.venue.lat,
                    lng: element.venue.lon
                  }
                }

                daData.push(pos)

                let markerIndex = (daData.length - 1)
                // console.log("hey did this run 3 times",markerIndex)
               //  console.log(self.props.daMap)

                STORE.setStore({eventLocs: daData})




               let throwMarker = function(evt){
                //  console.log(evt.target.dataset.index)
                 self.props.daMap(evt.target.dataset.index)
                 Backbone.Events.trigger('openBox', {
                    name: 'passData',
                    json: {data: element, type: "event"}
                 })
               }

                return(


                  <a className="list-group-item"  key={i} data-index={markerIndex} onClick={throwMarker}>

                     <div className="eventDetailHolder" data-index={markerIndex}>
                       <h4 className="list-group-item-heading" data-index={markerIndex}>{element.name}</h4>
                       <p className="list-group-item-text" data-index={markerIndex}>{element.group.name}</p>
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
