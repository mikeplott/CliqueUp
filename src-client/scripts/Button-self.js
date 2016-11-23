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



          //  <a href="#" class="list-group-item">
          //    <h4 class="list-group-item-heading">List group item heading</h4>
          //    <p class="list-group-item-text">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
          //  </a>
          //  <a href="#" class="list-group-item">
          //    <h4 class="list-group-item-heading">List group item heading</h4>
          //    <p class="list-group-item-text">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
          //  </a>



           <a className="list-group-item">
              <div className="eventPicHolder"><img src="http://facebookcraze.com/wp-content/uploads/2010/10/fake-facebook-profile-picture-funny-batman-pic.jpg" className="eventPics"/></div>
              <div className="eventDetailHolder">
                <h4 className="list-group-item-heading">{element.name}</h4>
                <p className="list-group-item-text">{element.group.name}</p>
              </div>
           </a>



            // <li>
            //    <div>
            //       <h2>{element.name}</h2>
            //       <p>{element.group.name}</p>
            //    </div>
            //    <div>
            //       <h1>{element.yes_rsvp_count}</h1>
            //    </div>
            // </li>
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
