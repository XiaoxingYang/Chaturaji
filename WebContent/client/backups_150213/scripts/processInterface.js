/**
processes incoming messages by evaluating the coordinates and calling respective functions in 4pchess
**/


function processIncomingMove(from, to){
	
	// get integer coordinates from String combination
	var fromCoords = getCoordinatesFromString(from);
	var toCoords = getCoordinatesFromString(to);
	//process move
	processMove(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);   
}


function processStartGame(p1, p2, p3, p4){

	startGame();

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
	var row = sq.substring(1) - 1;		// map [1:8] to [0:7]
	var col = sq.charCodeAt(0) - 65; 	// map [A:H] to [0:7]
	var result = new Array(2);
	result[0] = row;
	result[1] = col;
	return result;
}

/**  gets a String from board coordinates **/
function getStringFromCoordinates(row, col){
	var row1 = row + 1;
	var result = String.fromCharCode(col + 65) + row1.toString(); // [0:7] mapped to ["A":"H"] concatenated to [0:7] mapped to ["1:8"]
	return result;
}

