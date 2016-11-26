const React = require('react')
const ReactDOM = require('react-dom')
const Backbone = require('backbone')
const ACTIONS = require('./actions.js')
const STORE = require('./store.js')
const TLBoxViews = require('./TopLeftboxviews.js')

var clas1 = 'active'
var clas2 = 'btn btn-warning'

var clasSelf = "homeNavTabs #self"
var clasConceierge = "homeNavTabs #conceierge"
var clasMystery = "homeNavTabs #mystery"
var clasOther = "homeNavLastTabs #other"
//const BoxStuff = React.createClass
class BoxStuff extends React.Component{

   constructor(props) {

      super(props);
      this._handleChange = this._handleChange.bind(this)
      this.state = {
         topBoxView: ''
     };
   }

   _handleChange(evt){

     let theChecker = evt.target.className.split(' ')




     this.refs.navSelfBtn.className = clas2 + " " + clasSelf
     this.refs.navConBtn.className = clas2 + " " + clasConceierge
     this.refs.navMysBtn.className = clas2 + " " + clasMystery
     this.refs.navOthBtn.className = clas2 + " " + clasOther

     evt.target.className = clas1 + " " + evt.target.className


      let theValue = evt.target.className.split('#')


      if(theChecker[0] === 'active'){
        theChecker.shift()
        let newValue = theChecker
        evt.target.className = newValue.join(' ')
        this.setState({
          topBoxView: ''

        })

      } else {
        this.setState({
          topBoxView: theValue[1]

        })
      }




   }

   render(){
      //const value = this.state.value
      return(
      <div className="homeMeetupBox" ref="homeMeetupBox">
         <nav className="navbar navbar-inverse homeMeetupNav">
           <div className="homeMeetupNavBox">
             <button className={clas2 + " " + clasSelf}  ref="navSelfBtn" onClick={this._handleChange}><span className="fa fa-map-marker" aria-hidden="true"></span></button>
             <button className={clas2 + " " + clasConceierge} ref="navConBtn" onClick={this._handleChange}><span className="fa fa-street-view" aria-hidden="true"></span></button>
             <button className={clas2 + " " + clasMystery} ref="navMysBtn" onClick={this._handleChange}><span className="fa fa-users" aria-hidden="true"></span></button>
             <button className={clas2 + " " + clasOther} ref="navOthBtn" onClick={this._handleChange}><span className="fa fa-binoculars" aria-hidden="true"></span></button>
           </div>
         </nav>
         <div className="ClickDisplay">
            <TLBoxViews viewType={this.state.topBoxView} myMap={this.props.theAllMap}/>
         </div>
      </div>
      )
   }

}


module.exports = BoxStuff
