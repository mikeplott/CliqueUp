// var socket;
// var sender;
//
// function start() {
//     var ws = new SockJS("/socket")
//     socket = Stomp.over(ws)
//
//     socket.connect({}, onSocketConnected)
// }
//
// function onSocketConnected() {
//     socket.subscribe("/topic/chat/"+$('#conversation').val(), onReceiveMessage)
// }
//
// function onReceiveMessage(mess) {
//     data = JSON.parse(mess.body);
//     //$('#message-block').prepend("<div id='divlabel'>" + data.name + "<br />" + data.time + "</div><div class='talk-bubble tri-left round right-in'><div class='talktext'><p>" + data.body +  "</p></div></div><br /></div>");
//     var possession;
//     if (sender === data.name)
//     {
//         possession = "talk-bubble-mine";
//     }
//     else
//     {
//         possession = "talk-bubble-theirs";
//     }
//     var element = "<div>" +
//                     "<div class='divlabel'>" +
//                         data.name + "<br/>" +
//                         data.time +
//                     "</div>" +
//                     "<div class='" + possession + "'>" +
//                         "<div class='talktext'>" +
//                             "<p>" +
//                                data.body +
//                             "</p>" +
//                         "</div>" +
//                     "</div>" +
//                 "</div>" +
//                 "<br />";
//
//     $('#message-block').prepend(element);
//
//     if (mess === undefined)
//     {
//         return;
//     }
// }
//
// function sendMessage() {
//     sender = $('#name').val();
//     var t = timeNow();
//     var s = JSON.stringify({body: $('#body').val(), time: t, itemid: $('#itemid').val(), conversation: $('#conversation').val(), receiverid: $('#receiverid').val(), name: $('#name').val()});
//     socket.send("/topic/chat/"+$('#conversation').val(), {}, s);
//     $("#body").val('');
// }
//
// function timeNow() {
//   var d = new Date(),
//       mo = ((d.getMonth()+1)<10?'0':'') + (d.getMonth() + 1),
//       day = (d.getDate()<10?'0':'') + d.getDate(),
//       h = (d.getHours()<10?'0':'') + d.getHours(),
//       m = (d.getMinutes()<10?'0':'') + d.getMinutes();
//   return mo + '/' + day + '-' + h + ':' + m;
// }
//
// start();



//////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\

    // <div className="tab-pane fade active in" id="home">
    //   <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.</p>
    // </div>
    // <div className="tab-pane fade" id="profile">
    //   <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.</p>
    // </div>
    // <div className="tab-pane fade" id="dropdown1">
    //   <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade. Messenger bag gentrify pitchfork tattooed craft beer, iphone skateboard locavore carles etsy salvia banksy hoodie helvetica. DIY synth PBR banksy irony. Leggings gentrify squid 8-bit cred pitchfork.</p>
    // </div>
    // <div className="tab-pane fade" id="dropdown2">
    //   <p>Trust fund seitan letterpress, keytar raw denim keffiyeh etsy art party before they sold out master cleanse gluten-free squid scenester freegan cosby sweater. Fanny pack portland seitan DIY, art party locavore wolf cliche high life echo park Austin. Cred vinyl keffiyeh DIY salvia PBR, banh mi before they sold out farm-to-table VHS viral locavore cosby sweater.</p>
    // </div>

    // <ul className="nav nav-tabs nav-justified">
    //   <li role="presentation" className="active">Global</li>
    //   <li role="presentation">More</li>
    //   <li role="presentation">More</li>
    // </ul>
    // <div className="tab-content"></div>
    // <form>
    //   <input className="form-group" type="text"/>
    //   <input type="submit"/>
    // </form>

    // <button className="btn btn-warning homeScreenBtn" onClick={this._placeFirstMarker}>Place Marker</button>
    // <button className="btn btn-warning homeScreenBtn" onClick={this._getToken}>Test Token2</button>


    // <div className="homeChatBox">
    //   <div>
    //   <ul className="nav nav-tabs homeChatNav">
    //     <li className="active"><a data-toggle="tab">Global</a></li>
    //     <li><a data-toggle="tab">+</a></li>
    //     <li><a data-toggle="tab">V</a></li>
    //   </ul>
    //     <div id="myTabContent" className="tab-content">
    //       <div className="tab-pane fade active in">
    //         <div className="chatboxBody"></div>
    //         <div className="input-group chatInputBox">
    //           <input type="text" className="chatInput form-control" ref="chatMessage"/>
    //           <button className="btn btn-warning input-group-addon chatSend" onClick={this._sendChatMessage}>Send</button>
    //         </div>
    //       </div>
    //     </div>
    //   </div>
    // </div>
