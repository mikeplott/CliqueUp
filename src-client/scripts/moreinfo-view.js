const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js');
const STORE = require('./store.js');
const {selfModel, selfCollection} = require('./model-profs.js')
const $ = require('jquery')

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

   _addChat: function(){
     let everything = STORE.getStoreData().chatGroups

     let myData = this.state.boxData

     everything.push(myData.group.name)

     STORE.setStore('chatGroups', everything)
     Backbone.Events.trigger('newChat')
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
                <button className="btn btn-warning" onClick={this._addChat}>Chat</button>
               </ul>
               <div dangerouslySetInnerHTML={ {__html: daElemement.description} }></div>
            </div>
         )
         break;
      case "friends":
        let daPeople = daElemement.onlineUsers.map(function(peeps, i){

          console.log(peeps)

          let closerProf = function(){
            Backbone.Events.trigger('openBox', {
               name: 'passData',
               json: {data: peeps, type: "prof"}
            })
          }


          return(
            <div onClick={closerProf} className="friendHolder" key={i}>
              <h5 className="friendName">{peeps.username}</h5>
              <img className="friendPic" src={peeps.image}/>
            </div>
          )
        })
        let daFrans = daElemement.userFriends.map(function(maPeeps, i){
          return(
            <div className="friendHolder" key={i}>
              <h5 className="friendName">{maPeeps.friendName}</h5>
              <img className="friendPic" src={maPeeps.friendImage}/>
            </div>
          )
        })
        return(
          <div className="moreInfoViewBox">
            <div>
              <div>
                <h2>Online</h2>
              </div>
              <div>
                {daPeople}
              </div>

            </div>
            <hr/>
            {daFrans}
          </div>
        )
        break;
      case "prof":
        console.log(daElemement)
        let theData = STORE.getStoreData()
        let myToken = theData.token[0]
        let self = new selfModel(myToken, daElemement.meetupId)

        let everythingData = {}

        self.fetch().then(function(){
          // console.log(self)
          everythingData = self.attributes.data

        })

        let addFran = function(){
          $.post('/friends', {friendName: daElemement.username})
        }
        // console.log(self.attributes.data.name)
        return(
          <div className="moreInfoViewBox profBox">
            <div>
              <img className="profPic" src={daElemement.image}/>
              <h3>{daElemement.username}</h3>
            </div>
            <div>
              <button onClick={addFran} className="btn btn-warning">Add</button>
              <button className="btn btn-success">Chat</button>
              <p>They are online</p>
            </div>
          </div>
        )
        break;
      default :
         return(
            <div className="moreInfoViewBox">
              <img className="boxImage" src="../images/CliqueUpLogo.png"/>
            </div>
         )
         break;
   }

  }

})


module.exports = MoreInfoBox
