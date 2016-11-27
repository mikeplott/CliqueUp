const React = require('react')
const SelfView = require('./Button-self.js')
const ConciergeView = require('./Button-conc.js')
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
            return(<div><h1>mysterious!!!</h1></div>)
            break;

         case "other":
            return(<div><h1>theOtherButton!!!</h1></div>)
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
