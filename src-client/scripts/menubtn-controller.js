const React = require('react')
const MoreInfoBox = require('./moreinfo-view.js')


const MenuBtnView = React.createClass({
  render: function(){
    switch(this.props.menuDisplay){
      case "closed":
        return (
          <div></div>
        )
        break;
      default:
        return (
          <div>
            <div>
              <span className="fa fa-bell" aria-hidden="true"></span>
              <span  className="fa fa-address-book" aria-hidden="true"></span>
              <span className="fa fa-cog" aria-hidden="true"></span>
            </div>
          </div>

        )
        break;
    }
  }
})


  module.exports = MenuBtnView;
