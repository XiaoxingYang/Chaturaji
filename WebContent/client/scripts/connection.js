
/**
* connects to a specified server 
**/



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
	 return 1;
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
			
			processIncomingMove(jsonData.from_sq, jsonData.to_sq);
			return 2;
			break;
		case 4:	
			//update the score of all players
			updateScores(jsonData.p1, jsonData.p2, jsonData.p3, jsonData.p4);
			return 4;
			break;
		case 5:
			// player is out (king taken)
			setPlayerInactive(jsonData.player);
			return 5;
			break;
		case 6:
			// game end msg
			endGame(jsonData.outcome, jsonData.player);
			return 6;
			break;
		case 7:
			//sudden death mode enabled, moveLimit is set
			startSuddenDeath(jsonData.moveLimit);
			return 7;
			break;
		case 8:
			// pawn promotion
			processPawnPromotion(jsonData.sq, jsonData.pieceType);
			return 8;
			break;
		case 10:
			// start game message, includes Strings indicating player names/colours
			processStartGame(jsonData.p1, jsonData.p2, jsonData.p3, jsonData.p4);
			return 10;
			break;
		case 11:
			// boat triumph
			processBoatTriumph(jsonData.tBoat, jsonData.boats);
			
			return 11;
			break;
		case 50:
			//set player id
			setPlayerId(jsonData.Id);
			return 50;
			break;	
		case 51:
			// chat message received
			processChatMsg(jsonData.sender, jsonData.msg);
			return 51;
			break;
	}

}

/** 
*	sends a JSON object to the server via the websocket 
**/
function sendJSON(msg){
	ws.send(JSON.stringify(msg));
}
