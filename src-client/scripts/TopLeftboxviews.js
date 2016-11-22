const React = require('react')
const SelfView = require('./Button-self.js')
const STORE = require('./store.js')



console.log("page wired")

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
            return <SelfView/>
            break;

         case "conceierge":
            return (<div className="h"><h1>get me shit</h1></div>)
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
