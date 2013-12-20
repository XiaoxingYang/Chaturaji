/**
processes incoming messages by evaluating the coordinates and calling respective functions in 4pchess
**/

  
function processIncomingMove(from, to){
	
	// get integer coordinates from String combination
	var fromCoords = getCoordinatesFromString(from);
	var toCoords = getCoordinatesFromString(to); 
	//show move in moveLog
	notifyMoveLog(getColorFromPlayerId(currentTurn) + ": " + from + " - " + to);
	//process move
	processMove(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);  
		
}


function processStartGame(p1, p2, p3, p4){

	startGame();

}

/** processes a pawn promotion.**/
function processPawnPromotion(square, newPiece){
	var coords = getCoordinatesFromString(square);
	pawnPromotion(coords, newPiece);
	notifyStatusLog("Pawn promotion: " + getColorFromPlayerId(currentTurn) + "'s pawn on " + square + " promoted to  a " + newPiece);

}

/** processes boat triumph **/
function processBoatTriumph(boat, boats){
	
	var triumphBoat = getCoordinatesFromString(boat); // Array[row,col]
	var takenBoats = new Array(boats.length * 2);
	for(i=0; i < boats.length; i++){
		var takenBoatCoords = getCoordinatesFromString(boats[i]);
		takenBoats[i*2] = takenBoatCoords[0];
		takenBoats[i*2 + 1] = takenBoatCoords[1];
	}
	
	boatTriumph(triumphBoat, takenBoats);

}



function processChatMsg(sender, msg){
	incomingChatMsg(sender,msg);
}


function processChatMsgToServer(sender, msg){
	var chatMsg = {
		"type": 51,
		"data": {
			 "sender" : sender,
			 "msg" : msg
		}
	}
	sendJSON(chatMsg);
	
}


/** gets two squares, builds a JSON object and passes it to websocket **/
function processMoveToServer(from_row, from_col, to_row, to_col){
	
	//get strings
	var from = getStringFromCoordinates(from_row, from_col);
	var to = getStringFromCoordinates(to_row, to_col);
	
	var submitMoveMsg = {
		"type": 1,
		"data": {
			"from_sq": from, //square strings included for example "A1"
			"to_sq": to
		}
	}
	//push to connection.js to handle the json object and send it via websocket
	
	sendJSON(submitMoveMsg);
	
}


/** evalutes a String combination of letter and number and returns into two integer coordinates **/
function getCoordinatesFromString(sq){
	var number = sq.substring(1) - 1;		// map [1:8] to [0:7]
	var letter = sq.charCodeAt(0) - 65; 	// map [A:H] to [0:7]
	
	var row;
	var row1;
	var row2;
	var col;
	var col1;
	var col2;
	var result;
	switch(thisPlayerId){
		case 0:  // nothing to be reversed
				
				col = number;
				row = letter;
			
			break;
		case 1: // reverse rows ( = numbers)
		
				col = letter;
				row = (-1) * (number-7); //map [0:7] to [7:0] for rows
			break;
		case 2: //reverse rows (= letters) and columns  (=numbers)
		
				col = (-1) * (number-7);
				row = (-1) * (letter-7);
			break;
		case 3: // reverse columns (=letters)
			
				col = (-1) * (letter-7);
				row = number
			break;
	}

	var result = new Array(2);
	result[0] = row;
	result[1] = col;
	return result;
}

/**  gets a String from board coordinates **/
function getStringFromCoordinates(row, col){
	
	var row1;
	var row2;
	var col1;
	var col2;
	var result;
	switch(thisPlayerId){
		case 0:  // nothing to be reversed
				
				col2 = col + 1; // map [0:7] to [1:8]
				result = String.fromCharCode(row + 65) + col2.toString();
			break;
		case 1: // reverse rows ( = numbers)
				row1 = (-1) * (row -7);  //map [0:7] to [7:0] for rows
				row2 = row1 + 1;
				result = String.fromCharCode(col + 65) + row2.toString(); // [0:7] mapped to ["A":"H"] concatenated to [0:7] mapped to ["1:8"]
			break;
		case 2: //reverse rows (= letters) and columns  (=numbers)
				col1 = (-1) * (col-7); //map [0:7] to [7:0] for columns
				col2 = col1 + 1;
				row2 = (-1) * (row -7);  //map [0:7] to [7:0] for rows
				result = String.fromCharCode(row2 + 65) + col2.toString();
			break;
		case 3: // reverse columns (=letters)
				col2 = (-1) * (col-7); //map [0:7] to [7:0] for columns
				row2 = row + 1;
				result = String.fromCharCode(col2 + 65) + row2.toString();
				
			break;
	}

	return result;
}

