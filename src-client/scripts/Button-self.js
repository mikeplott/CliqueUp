const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const STORE = require('./store.js')
const ACTIONS = require('./actions.js')
const {HomeView, thisOne} = require('./home-view.js')
// const theMap = document.querySelector("#map")


class SelfView extends React.Component{

   constructor () {
      super();

   }




   render(){
    //  if(!this.props.daMap){
    //    return(
    //      <p>waiting</p>
    //    )
    //  }

     var self = this
      let nestingBastards = STORE.getStoreData().userEvents.data
      console.log(nestingBastards)


      let UsefulStuff = nestingBastards.map(function(element, i){
        //  console.log(i)
        //  console.log(element.group.name)
        //  console.log(element.venue)
         let daData = STORE.getStoreData()
         daData = daData.eventLocs

         let pos = {}

         if (element.venue.lat === undefined){
            pos = {lat: 32.776475, lng: -79.931051}
         } else {
            pos = {
             lat: element.venue.lat,
             lng: element.venue.lon
           }
         }



         daData.push(pos)

         let markerIndex = (daData.length - 1)
        //  console.log("hey did this run 3 times",markerIndex)
        //  console.log(self.props.daMap)

         STORE.setStore({eventLocs: daData})




        let throwMarker = function(evt){
          // console.log(evt.target.dataset.index)
          self.props.daMap(evt.target.dataset.index)
          Backbone.Events.trigger('openBox', {
             name: 'passData',
             json: {data: element, type: "event"}
          })
        }

         return(


           <a className="list-group-item"  key={i} data-index={markerIndex} onClick={throwMarker}>
              <div className="eventPicHolder" data-index={markerIndex}><img data-index={markerIndex} src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg" className="eventPics"/></div>
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


module.exports = SelfView
