
/////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// BOARD SETUP //////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////

var NUMBER_OF_COLS = 8, NUMBER_OF_ROWS = 8, SQUARE_SIZE = 50;

var SQUARE_COLOR_1 = '#01A9DB', SQUARE_COLOR_2 = '#CEECF5', HIGHLIGHT_COLOR = '#F2F5A9', MOVE_COLOR_1 = '#00CC66', MOVE_COLOR_2 = '#99FFCC';

// team id's
var RED_TEAM = 0, GREEN_TEAM = 1, YELLOW_TEAM = 2, BLACK_TEAM = 3;

// piece values
var PIECE_PAWN = 0, PIECE_BOAT = 3, PIECE_KNIGHT = 2, PIECE_ELEPHANT = 1, PIECE_KING = 5;

//setup pieces by individual piece id and corresponding String description

//teamid * 5 + piece id
var pieceIds = new Array(20);
	pieceIds[0] = "redPawn";
	pieceIds[1] = "redElephant";
	pieceIds[2] = "redKnight";
	pieceIds[3] = "redBoat";
	pieceIds[4] = "redKing";
	pieceIds[5] = "greenPawn";
	pieceIds[6] = "greenElephant";
	pieceIds[7] = "greenKnight";
	pieceIds[8] = "greenBoat";
	pieceIds[9] = "greenKing";
	pieceIds[10] = "yellowPawn";
	pieceIds[11] = "yellowElephant";
	pieceIds[12] = "yellowKnight";
	pieceIds[13] = "yellowBoat";
	pieceIds[14] = "yellowKing";
	pieceIds[15] = "blackPawn";
	pieceIds[16] = "blackElephant";
	pieceIds[17] = "blackKnight";
	pieceIds[18] = "blackBoat";
	pieceIds[19] = "blackKing";



//create board representation default
var board = new Array(NUMBER_OF_COLS * NUMBER_OF_ROWS);
board = 	[13, 10, -1, -1, 19, 16, 17, 18,
			 12, 10, -1, -1, 15, 15, 15, 15,
			 11, 10, -1, -1, -1, -1, -1, -1,
			 14, 10, -1, -1, -1, -1, -1, -1,
			 -1, -1, -1, -1, -1, -1,  0,  4,
			 -1, -1, -1, -1, -1, -1,  0,  1,
			  5,  5,  5,  5, -1, -1,  0,  2,
			  8,  7,  6,  9, -1, -1,  0,  3] ;
			  
			  

// hardcoded rules 			  
var rules = 
{
	"boat": [[2,2],[2,-2],[-2,2],[-2,-2]],
	"knight": [[1,2],[1,-2],[-1,2],[-1,-2],[2,1],[2,-1],[-2,1],[-2,-1]],
	"king": [[1,1],[1,-1],[-1,1],[-1,-1],[1,0],[0,-1],[-1,0],[0,1]],
	"elephant": [[1,0],[0,1],[-1,0],[0,-1]],
	"pawn": [[0,-1],[-1,0],[0,1],[1,0],[-1,-1],[1,-1],[-1,-1],[-1,1],[-1,1],[1,1],[1,1],[1,-1]] // direction vectors per team (total 4), plus two attack vectors each (total 8), makes 12
};			  
			  
			  
			  
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// INSTANCE VARIABLES //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////			  
			  
var canvas = null;			  
var ctx = null;  //holds canvas reference			  

//this player id
var thisPlayerId;
var currentTurn;

var selectedSquare;

var activePlayers = new Array(4);
	activePlayers[0] = 1;
	activePlayers[1] = 1;
	activePlayers[2] = 1;
	activePlayers[3] = 1;
	
var scores = new Array(4);




//////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// PUBLIC FUNCTIONS //////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////


/** output move in the moveLog object **/
function notifyMoveLog(msg){
	document.getElementById("moveLog").value += msg + "\n";
	
		//scrollDownLog();
	
}


/** output message in the statusLog object **/
function notifyStatusLog(msg){
	document.getElementById("statusLog").value += msg + "\n";
	
	//scrollDownLog();
}



/** set this client's id **/
function setPlayerId(id){
	thisPlayerId = id;
	notifyStatusLog("Connected to server. Your Player ID: " + id + ". Your color: " + getColorFromPlayerId(thisPlayerId));
}


/** start the game and notify all players **/
function startGame(){
	currentTurn = 0;
	
	//notification
	notifyStatusLog("The game begins");

	if(currentTurn == thisPlayerId){
		notifyStatusLog("It's your turn");
	}else{
		notifyStatusLog("It's " + getColorFromPlayerId(currentTurn) + "'s turn now.");
	}
}

/** game has ended. winner(s) are announced */
function endGame(outcome, winner){
	currentTurn = null;
	var notification;
	if(outcome == "win"){
		notification = getColorFromPlayerId(winner-1) + " has won."; // map player ids [1,2,3,4] to [0,1,2,3]
	}else if (outcome == "draw"){
		notification = "The two winners are: " +  getColorFromPlayerId(winner[0]-1) + " and " + getColorFromPlayerId(winner[1]-1);
	}	
	notifyStatusLog("The game has finished. " + notification);
}


/** notify players about sudden death **/
function startSuddenDeath(moveLimit){
	notifyStatusLog("Sudden Death! Move Limit: " + moveLimit);

}


/** moves piece from a square given by its row and column numbers to another represented in the same way **/
function processMove(from_row, from_col, to_row, to_col){
	
	//get board index of square by row and col
	var fromSquare = getBoardIndex(from_row, from_col);
	var toSquare = getBoardIndex(to_row, to_col);
	
	//move piece
	board[toSquare] = board[fromSquare];
	board[fromSquare] = -1;
	
	//redraw affected squares
	drawSquareInColor(to_row, to_col, getSquareColor(to_row,to_col));
	drawSquareInColor(from_row,from_col, getSquareColor(from_row,from_col));
	drawPiece(to_row,to_col);
	
	//next player's turn
	currentTurn = getNextTurn(currentTurn, activePlayers);
	
	
	if(currentTurn == thisPlayerId){
		notifyStatusLog("It's your turn");
	}else{
		notifyStatusLog("It's " + getColorFromPlayerId(currentTurn) + "'s turn now.");
	}
	
}

/** update scores of all players and output notification**/
function updateScores(p0, p1, p2, p3){
	scores[0] = p0;
	scores[1] = p1;
	scores[2] = p2;
	scores[3] = p3;
	
	notifyStatusLog("Scores ++ red: " + scores[0] + ", green: " + scores[1] + ", yellow: " + scores[2] + ", black" + scores[3]);
}


/** player is out and not considered active anymore **/
function setPlayerInactive(id){
	activePlayers[id] = 0;
}

/** player is activated **/
function setPlayerActive(id){
	activePlayers[id] = 1;
}



/////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// AUXILIARY FUNCTIONS //////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////


/** returns the id of the next player. Fix needed if a player is OUT **/
function getNextTurn(curTurn, activeP){
	
	if(curTurn < 0 || curTurn > 3 || typeof(curTurn) != 'number') return -1;
	curTurn = Math.floor(curTurn); //in case curTurn is not an int but a float or double
	var result;
	var found = false;
	var i = 0;
	while(i < 4 && !found){
		if(playerActive(((curTurn + 1 + i)%4), activeP)){
			result = (curTurn+1 + i)%4 ; // 0->1->2->3->0->1....
			found = true;
		}else{
		 i++;
		}
	}

	return result;
}


/** returns color String of player with particular id **/
function getColorFromPlayerId(id){
	var color = -1;
	switch(id){
		case 0: color = "red";
		break;
		case 1: color = "green";
		break;
		case 2: color = "yellow";
		break;
		case 3: color = "black";
		break;
	}
	return color;
}


function playerActive(id, activeP){
	if(activeP[id] == 1){
		return true;
	}else{
		return false;
	}
}





////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// GAME LOGIC //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

/** calculates all possible moves for a piece at a certain square **/
function possibleMoves(boardIndex, playerId){
	var moves = new Array();
	var coords = getCoordinatesFromIndex(boardIndex);
	var row = coords[0];
	var col = coords[1];
	
	var pieceId = board[boardIndex];
	var pieceType = pieceId % 5; // get piece type, e.g. pawn, knight, etc. from pieceId [0:19]
	
	if(playerId < 0 ||playerId > 3) return -1;
	
	//pawn
	if(pieceType == 0){
		var ruleList = rules.pawn[playerId];
		
		//move straight
		var squareByRules = getBoardIndex(row + ruleList[0], col + ruleList[1]);
		
		//check if possible square is on board	 
		if(squareByRules <= 63 && squareByRules >= 0){
			if(board[squareByRules] == -1){
				moves.push(squareByRules);
				
			//check if  piece on board and NOT a piece of the same team, then pawn cant move straight
			}else if(getPlayerFromPiece(board[squareByRules]) != playerId){
				//do nothing
			}
		}
		
		
		// consider squares holding opposing pieces that can be attacked
		var attackList = new Array();
		
			// access corresponding rules in rule set
			attackList.push(rules.pawn[4 + playerId * 2]);
			attackList.push(rules.pawn[4 + playerId * 2 + 1]);
		
			for(i=0;i<attackList.length;i++){
				var squareByRules = getBoardIndex(row + attackList[i][0], col + attackList[i][1]);
				if(squareByRules <= 63 && squareByRules >= 0 && (board[squareByRules] != -1) && (getPlayerFromPiece(board[squareByRules]) != playerId)){
					moves.push(squareByRules);
				}
			}	
		
	}
	
	// knight, boat or king
	if(pieceType == 2 || pieceType == 3 || pieceType == 4){
	
		if(pieceType == 2){
			var ruleList = rules.knight;
		}else if(pieceType == 3){
			var ruleList = rules.boat;
		}else if(pieceType == 4){
			var ruleList = rules.king;
		}
				
			for(i=0;i<ruleList.length;i++){
			
				 var squareByRules = getBoardIndex(row + ruleList[i][0], col + ruleList[i][1]);
					//check if possible square is on board
				 
					if(squareByRules <= 63 && squareByRules >= 0){
						
						//check if square is empty
						if(board[squareByRules] == -1){
							moves.push(squareByRules);
						
						//check if  piece on board and NOT a piece of the same team
						}else if(getPlayerFromPiece(board[squareByRules]) != playerId){
						
							moves.push(squareByRules);
							
						}
					}
			}
	}
	// elephant
	
	if(pieceType == 1){
		
		var ruleList=rules.elephant;
		for(var i=0; i< ruleList.length;i++){
			var accRow = row;
			var accCol = col;
			var end=false;
			while(!end){
				accRow += ruleList[i][0];
				accCol += ruleList[i][1];
				var squareByRules = getBoardIndex(accRow, accCol);
				
				if(squareByRules <= 63 && squareByRules >= 0){
										
					//check if piece is on possible square
					if(board[squareByRules] != -1){
						end=true;
						if(getPlayerFromPiece(board[squareByRules]) != thisPlayerId) 
							moves.push(squareByRules);
						
					}
					else
						moves.push(squareByRules);
						
				}
				else{
					end=true;
				}
			}
		}
	}
	
	
	
	
		
	return moves;

}





//////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// ARITHMETIC FUNCTIONS //////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////

function getCoordinatesFromIndex(boardIndex){
	var coords = new Array(2);
		coords[0] = Math.floor(boardIndex/8); //row
		coords[1] = boardIndex % 8; //col	
	return coords;
}


		  
			  
function getPlayerFromPiece(pieceIndex){
	if(pieceIndex < 0 || pieceIndex > 19) return -1; // fail safe
	return Math.floor(pieceIndex / 5);
}			  
			  

/** returns the square (board index) from coordinates on the screen **/
function screenToSquare(x, y)
{
	var row = Math.floor(y / SQUARE_SIZE);
	var col = Math.floor(x / SQUARE_SIZE);
	var result = getBoardIndex(row,col);
	return result;
}

			  
/** return the colour of a square according to its position on the board. **/
function getSquareColor(row, col)
{
	if(row < 0 || col < 0 || row > 7 || col > 7) return -1; // square not on board
	
	var cStartColor;
	
	if((row+col)%2){
		cStartColor = SQUARE_COLOR_2;
	}else{
		cStartColor = SQUARE_COLOR_1;
	}
	
	return cStartColor;
}

/** return the color of a square that is being highlighted **/
function getHighlightMoveColor(row, col)
{
	if(row < 0 || col < 0 || row > 7 || col > 7) return -1; // square not on board
	var highlightColor;

	if((row+col)%2){
		highlightColor = MOVE_COLOR_2;
	}else{
		highlightColor = MOVE_COLOR_1;
	}
	
	return highlightColor;
}				  


function getBoardIndex(row,col){
	// sanity check
	if((row >= 0 && row <= 7) && (col >= 0 && col <= 7)){
		return row * 8 + col;
	}else{
		return -1;
	}
}



 
//////////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////// EVENT HANDLERS //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
 
 
/** event listener for a click on the board, calculates the click coordinates for further processing **/
function boardClick(ev) 
{
	//only evaluate click events if it's the player's turn
	if(currentTurn == thisPlayerId){
			var x = ev.clientX - canvas.offsetLeft,
			y = ev.clientY - canvas.offsetTop,
			square = screenToSquare(x, y);  // square here as board index
		if(getPlayerFromPiece(board[square]) == thisPlayerId){
			if(selectedSquare != null){
				removePossibleMoves(selectedSquare);
				deselectSquare(selectedSquare);
				
			}
				selectSquare(square);
				//highlight possible moves
				highlightPossibleMoves(square);
				
		}else
			if(selectedSquare != null){
			
				var moves = possibleMoves(selectedSquare, thisPlayerId);
				for(i=0; i < moves.length; i++){
					if(moves[i] == square){
						var fromCoords = getCoordinatesFromIndex(selectedSquare);
						var toCoords = getCoordinatesFromIndex(square);
						removePossibleMovesAfterAttack(selectedSquare);
						//deselectSquare(selectedSquare); //not really needed
						//processMove(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);
						processMoveToServer(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);
						
					}
				}
		}
	}
	return "click processed";
}






////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// HIGHLIGHT SQUARE FUNCTIONS //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////


function removePossibleMovesAfterAttack(boardIndex){

	var moves = possibleMoves(boardIndex, thisPlayerId);
	
	for(i=0; i < moves.length; i++){
		
		var coords = getCoordinatesFromIndex(moves[i]);
		var possibleRow = coords[0];
		var possibleCol = coords[1];
		drawSquareInColor(possibleRow, possibleCol, getSquareColor(possibleRow,possibleCol));
		if(board[moves[i]] != -1){ //piece on field
			drawPiece(possibleRow, possibleCol);
		}
		
	}
}

/** removes the previously drawn possible moves from board **/
function removePossibleMoves(boardIndex){
	var moves = possibleMoves(boardIndex, thisPlayerId);
	
	for(i=0; i < moves.length; i++){
		
		var coords = getCoordinatesFromIndex(moves[i]);
		var possibleRow = coords[0];
		var possibleCol = coords[1];
		drawSquareInColor(possibleRow, possibleCol, getSquareColor(possibleRow,possibleCol));
		if(board[moves[i]] != -1){
			drawPiece(possibleRow, possibleCol);
		}
		
	}
}




/** highlights the possible moves of a piece at a certain square on the board **/
function highlightPossibleMoves(boardIndex){
	var moves = possibleMoves(boardIndex, thisPlayerId);
	for(i=0; i < moves.length; i++){
		
		var coords = getCoordinatesFromIndex(moves[i]);
		var possibleRow = coords[0];
		var possibleCol = coords[1];
		drawSquareInColor(possibleRow, possibleCol, getHighlightMoveColor(possibleRow, possibleCol));
		if(board[moves[i]] != -1){
			drawPiece(possibleRow, possibleCol);
		}
		
	}
}



function deselectSquare(boardIndex){
	selectedSquare = null;
	var coords = getCoordinatesFromIndex(boardIndex);
	var row = coords[0];
	var col = coords[1];

	drawSquareInColor(row, col, getSquareColor(row,col));
	drawPiece(row, col);

}


function selectSquare(boardIndex){

	selectedSquare = boardIndex;
	var coords = getCoordinatesFromIndex(boardIndex);
	var row = coords[0];
	var col = coords[1];
	drawSquareInColor(row, col, HIGHLIGHT_COLOR);
	drawPiece(row, col);
}







////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// DRAW FUNCTIONS //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

function drawPiece(row, col){

	var index = getBoardIndex(row,col);
	
	if(board[index] != -1){
		var loc = "img/" + pieceIds[board[index]] + ".svg";
			
		var img = new Image();
		// wait for source to be fully loaded (especially for slower mobile devices!)
		img.onload = function(){
				if(board[index] != -1){
				ctx.drawImage(img, col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				}
		};		
		// Draw the piece onto the canvas
		img.src = loc;	
	}
	
	
}


function drawPieces(){

	for(i=0;i<(NUMBER_OF_COLS * NUMBER_OF_ROWS);i++){
		drawPiece(Math.floor(i/8), i%8);
	}

}


/** draws one Square onto the board **/
function drawSquare(row, col)
{	
	
	// Set the background
	ctx.fillStyle = getSquareColor(row, col);
	
	// Draw rectangle for the background
	ctx.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

}

/** draws one Square in a specific color onto the board **/
function drawSquareInColor(row, col, color)
{	
	if(row < 0 || col < 0 || row > 7 || col > 7) return -1; // square not on board
	// Set the background
	ctx.fillStyle = color;
	
	// Draw rectangle for the background
	ctx.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	return "drawSquareInColor succeeded";
}
			  
			  
			  
/** draws one row of the board **/
function drawRow(row)
{
	var iColCounter;
	
	for (iColCounter = 0; iColCounter < NUMBER_OF_ROWS; iColCounter++)
	{
		drawSquare(row, iColCounter);
	}
}			  

/** draws the entire board row by row **/
function drawBoard()
{	
	var iRowCounter;
	
	for (iRowCounter = 0; iRowCounter < NUMBER_OF_ROWS; iRowCounter++)
	{
		drawRow(iRowCounter);
	}	
}
			  
			  
/** draw function is the main drawing function that initiates the composition of the board **/
function draw()
{
	
	
	canvas = document.getElementById('chess');

	// Canvas supported?
	if (canvas.getContext)
	{
		ctx = canvas.getContext('2d');

		// calculdate the precise block size
		SQUARE_SIZE = canvas.height / NUMBER_OF_ROWS;
		
		// draw the background
		drawBoard();
		// draw all pieces
		drawPieces();
		canvas.addEventListener('click', boardClick, false);
	
	}
	else
	{
		//notification
		alert("Canvas not supported!");
	}
}


 

