const React = require('react')
const MoreInfoBox = require('./moreinfo-view.js')


const MoreInfoView = React.createClass({
  render: function(){
    switch(this.props.boxDisplay){
      case "closed":
        return (
          <div className="it-is-closed"></div>
        )
        break;
      default:
        return <MoreInfoBox/>
        break;
    }
  }
})


module.exports = MoreInfoView;
