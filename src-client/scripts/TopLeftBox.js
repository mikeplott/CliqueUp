const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js')
const STORE = require('./store.js')
const TLBoxViews = require('./TopLeftboxviews.js')

console.log('fuckYOU')
//const BoxStuff = React.createClass
class BoxStuff extends React.Component{

   constructor(props) {
      console.log('hellow')
      super(props);
      this._handleChange = this._handleChange.bind(this)
      this.state = {
         topBoxView: ''
     };
   }

   _handleChange(evt){



      let theValue = evt.target.className.split('#')
      console.log(theValue[1])
      this.setState({
         topBoxView: theValue[1]
      })
         


      console.log(this.state)

   }

   render(){
      //const value = this.state.value
      return(
      <div className="homeMeetupBox" ref="homeMeetupBox">
         <nav className="navbar navbar-inverse homeMeetupNav">
           <div className="">
             <button className="btn btn-warning homeNavTabs #self"  onClick={this._handleChange}>Self</button>
             <button className="btn btn-warning homeNavTabs #conceierge"  onClick={this._handleChange}>Concierge</button>
             <button className="btn btn-warning homeNavTabs #mystery"  onClick={this._handleChange}>Mystery</button>
             <button className="btn btn-warning homeNavLastTabs #other"  onClick={this._handleChange}>Other</button>
           </div>
         </nav>
         <div className="ClickDisplay">
            <TLBoxViews viewType={this.state.topBoxView}/>
         </div>
      </div>
      )
   }

}


module.exports = BoxStuff
