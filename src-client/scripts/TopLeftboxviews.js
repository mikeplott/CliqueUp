const React = require('react')
const SelfView = require('./Button-self.js')
const ConciergeView = require('./Button-conc.js')
const Button3View = require('./Button-3.js')
const Button4View = require('./Button-4.js')
const STORE = require('./store.js')





class TLBoxViews extends React.Component{

   constructor () {
      super();
      // this. _handleClick = this. _handleClick.bind(this);
      // this.State = STORE.getState()
      // console.log(this)
   }

   // componentWillMount(){
      // let self = this
      // STORE.onChange(function(){
         // console.log('app State Changed')
         // let updatedState = STORE.getStoreData
      // })
   // }

   render(){
      switch(this.props.viewType){

         case "self":
            return <SelfView daMap={this.props.myMap}/>
            break;

         case "conceierge":
            return <ConciergeView daMap={this.props.myMap}/>
            break;

         case "mystery":
            return <Button3View />
            break;

         case "other":
            return <Button4View />
            break;

         default:
            return (
               <div>

               </div>
            )
      }
   }

}


module.exports= TLBoxViews
