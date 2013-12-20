
/**
* connects to a specified server 
**/

//"ws://shell1.doc.ic.ac.uk:8080/Chaturaji/WSHandler"




var ws;

function connectToServer(address){

 var isConnected = false;
 
 if ("WebSocket" in window && !isConnected)
  {	
	
     ws = new WebSocket(address);
     ws.onopen = function()
     {
        // Web Socket is connected, send data using send()
		isConnected = true;
		
		
	};
     ws.onmessage = function (evt) 
     {    
        handleIncoming(evt.data);
     };
     ws.onclose = function()
     { 
        // websocket is closed.
        alert("Connection is closed..."); 
     };
  }
  else
  {
     // The browser doesn't support WebSocket
     alert("WebSocket NOT supported by your Browser!");
  }
}


/** 
*	handles incoming messages by parsing them to JSON object, evaluating the type of the message and calling respective interface functions 
**/
function handleIncoming(msg){
	var jsonMsg = JSON.parse(msg);
	var jsonData = jsonMsg.data;
	
	switch(jsonMsg.type){
		case 2:
			//incoming move that has to be displayed
			alert(msg);
			processIncomingMove(jsonData.from_sq, jsonData.to_sq);
			break;
		case 4:	
			//update the score of all players
			updateScores(jsonData.p1, jsonData.p2, jsonData.p3, jsonData.p4);
			break;
		case 5:
			// player has left the game
			playerLeftGame(jsonData.player);
			break;
		case 6:
			// game end msg
			gameHasEnded(jsonData.outcome, jsonData.player);
			break;
		case 7:
			//sudden death mode enabled, moveLimit is set
			startSuddenDeath(jsonData.moveLimit);
			break;
		case 8:
			// pawn promotion
			processPawnPromotion(jsonData.sq, jsonData.pieceType);
			break;
		case 10:
			// start game message, includes Strings indicating player names/colours
			
			processStartGame(jsonData.p1, jsonData.p2, jsonData.p3, jsonData.p4);
			break;
		case 11:
			// boat triumph
			processBoatTriumph(jsonData.tBoat, jsonData.boats);
			break;
		case 50:
			//set player id
			setPlayerId(jsonData.Id);
			
	}

}

/** 
*	sends a JSON object to the server via the websocket 
**/
function sendJSON(msg){
	
	ws.send(JSON.stringify(msg));
	alert("send: " + JSON.stringify(msg));
}

function send() {
	var JSONObject = {"Color":"0", "Move":"It works !!"};
	ws.send(JSON.stringify(JSONObject));
	alert(JSON.stringify(JSONObject));
}