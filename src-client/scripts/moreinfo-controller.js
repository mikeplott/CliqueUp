const React = require('react')
const MoreInfoBox = require('./moreinfo-view.js')


const MoreInfoView = React.createClass({
  render: function(){
    switch(this.props.currentView){
      case "closed":
        return (
          <div></div>
        )
        break;
      default:
        return <MoreInfoBox/>
        break;
    }
  }
})


module.exports = MoreInfoView;
