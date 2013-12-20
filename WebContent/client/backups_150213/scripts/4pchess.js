
/**
4pchess.j
authors: Song, Ji and Nass, Florian

draws the board and pieces on a canvas, holds some game logic to highlight possible moves

**/


//SETUP BOARD SPECIFICATION//
var NUMBER_OF_COLS = 8, NUMBER_OF_ROWS = 8, BLOCK_SIZE = 50;

var BLOCK_COLOUR_1 = '#01A9DB', BLOCK_COLOUR_2 = '#CEECF5', HIGHLIGHT_COLOUR = '#F2F5A9', MOVE_COLOUR = '#333333';

var piecePositions = null;

// piece values and id's
var PIECE_PAWN = 0, PIECE_BOAT = 3, PIECE_HORSE = 2, PIECE_ELEPHANT = 1, PIECE_KING = 5;

// piece status, in game or taken
var IN_PLAY = 0, TAKEN = 1;

// initialize objects
var document = null, pieces = null, ctx = null, json = null, canvas = null;

// team id's
var RED_TEAM = 0, GREEN_TEAM = 1, YELLOW_TEAM = 2, BLACK_TEAM = 3;

var SELECT_LINE_WIDTH = 5;
var POSSIBLE_MOVE_SIZE = 10;


// this client player id
var thisPlayerId;


// turn is held in this variable	
var currentTurn = null, selectedPiece = null;
var pBlocksArr;

var rule = 
{
	"boat": [[2,2],[2,-2],[-2,2],[-2,-2]],
	"horse": [[1,2],[1,-2],[-1,2],[-1,-2],[2,1],[2,-1],[-2,1],[-2,-1]],
	"king": [[1,1],[1,-1],[-1,1],[-1,-1],[1,0],[0,-1],[-1,0],[0,1]],
	"elephant": [[1,0],[0,1],[-1,0],[0,-1]]
};

// setup default board	
function defaultPositions()
{
	json = 
	{
		"black": 
		[
			{
				"team": BLACK_TEAM,
				"piece": PIECE_KING,
				"row": 0,
				"col": 4,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_ELEPHANT,
				"row": 0,
				"col": 5,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_HORSE,
				"row": 0,
				"col": 6,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_BOAT,
				"row": 0,
				"col": 7,
				"status": IN_PLAY
			},	
			{
				"team": BLACK_TEAM,
				"piece": PIECE_PAWN,
				"row": 1,
				"col": 4,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_PAWN,
				"row": 1,
				"col": 5,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_PAWN,
				"row": 1,
				"col": 6,
				"status": IN_PLAY
			},
			{
				"team": BLACK_TEAM,
				"piece": PIECE_PAWN,
				"row": 1,
				"col": 7,
				"status": IN_PLAY
			}
		],
		"red": 
		[
			{
				"team": RED_TEAM,
				"piece": PIECE_KING,
				"row": 4,
				"col": 7,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_ELEPHANT,
				"row": 5,
				"col": 7,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_HORSE,
				"row": 6,
				"col": 7,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_BOAT,
				"row": 7,
				"col": 7,
				"status": IN_PLAY
			},	
			{
				"team": RED_TEAM,
				"piece": PIECE_PAWN,
				"row": 4,
				"col": 6,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_PAWN,
				"row": 5,
				"col": 6,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_PAWN,
				"row": 6,
				"col": 6,
				"status": IN_PLAY
			},
			{
				"team": RED_TEAM,
				"piece": PIECE_PAWN,
				"row": 7,
				"col": 6,
				"status": IN_PLAY
			}	
		],
		"green": 
		[
			{
				"team": GREEN_TEAM,
				"piece": PIECE_KING,
				"row": 7,
				"col": 3,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_ELEPHANT,
				"row": 7,
				"col": 2,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_HORSE,
				"row": 7,
				"col": 1,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_BOAT,
				"row": 7,
				"col": 0,
				"status": IN_PLAY
			},	
			{
				"team": GREEN_TEAM,
				"piece": PIECE_PAWN,
				"row": 6,
				"col": 3,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_PAWN,
				"row": 6,
				"col": 2,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_PAWN,
				"row": 6,
				"col": 1,
				"status": IN_PLAY
			},
			{
				"team": GREEN_TEAM,
				"piece": PIECE_PAWN,
				"row": 6,
				"col": 0,
				"status": IN_PLAY
			}	
		],
		"yellow": 
		[
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_KING,
				"row": 3,
				"col": 0,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_ELEPHANT,
				"row": 2,
				"col": 0,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_HORSE,
				"row": 1,
				"col": 0,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_BOAT,
				"row": 0,
				"col": 0,
				"status": IN_PLAY
			},	
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_PAWN,
				"row": 3,
				"col": 1,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_PAWN,
				"row": 2,
				"col": 1,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_PAWN,
				"row": 1,
				"col": 1,
				"status": IN_PLAY
			},
			{
				"team": YELLOW_TEAM,
				"piece": PIECE_PAWN,
				"row": 0,
				"col": 1,
				"status": IN_PLAY
			}
		]		
	};
}


/* get block according to mouseclick */
function screenToBlock(x, y)
{
	var block = 
	{
		"row": Math.floor(y / BLOCK_SIZE),
		"col": Math.floor(x / BLOCK_SIZE)
	}; 
	
	return block;	
}

/** getJsonObjectOfTeam takes standardized team specifier and returns json object holding information about a specific team **/
function getJsonObjectOfTeam(teamName) {
	var team = null;
    if (teamName==RED_TEAM) team = json.red;
	else
	if (teamName==GREEN_TEAM) team = json.green;
	else
	if (teamName==YELLOW_TEAM) team = json.yellow;
	else
	if (teamName==BLACK_TEAM) team = json.black;
	
	return team;
}





function getPieceAtBlockForTeam(teamOfPieces, clickedBlock)

{
	var curPiece = null,
		iPieceCounter = 0,
		pieceAtBlock = null;
	
	for (iPieceCounter = 0; iPieceCounter < teamOfPieces.length; iPieceCounter++) 
	{
		curPiece = teamOfPieces[iPieceCounter];
		
		if (curPiece.status === IN_PLAY &&
			curPiece.col === clickedBlock.col &&
			curPiece.row === clickedBlock.row)
		{
			curPiece.position = iPieceCounter;
			
			pieceAtBlock = curPiece;
			iPieceCounter = teamOfPieces.length;
			//break
		}
	}
	
	return pieceAtBlock;
}

function blockOccupied(clickedBlock)
{
	var pieceAtBlock=getPieceAtBlock(clickedBlock);
	return (pieceAtBlock !== null);
}

function canSelectedMoveToBlock(clickedBlock)
{
	for(var i=0;i<pBlockArr.length;i++){
		if(clickedBlock.row==pBlockArr[i].row && clickedBlock.col==pBlockArr[i].col)
			return true;
	}
	return false;
}

/** returns piece that is located on a particular square **/
function getPieceAtBlock(clickedBlock)
{
	var pieceAtBlock = getPieceAtBlockForTeam(json.red, clickedBlock);
	
	if (pieceAtBlock === null)
	{
		pieceAtBlock = getPieceAtBlockForTeam(json.green, clickedBlock);
	}
	if (pieceAtBlock === null)
	{
		pieceAtBlock = getPieceAtBlockForTeam(json.yellow, clickedBlock);
	}
	if (pieceAtBlock === null)
	{
		pieceAtBlock = getPieceAtBlockForTeam(json.black, clickedBlock);
	}
	
	return pieceAtBlock;
}


//get a block from row and column
function getBlock(row,col)
{
	var pBlock = 
	{
		"row": row,
		"col": col
	}; 
	return pBlock;
}

// is the block position in board or not
function isBlockInBoard(block){
	if( block.row>=0 && block.row<=7
			&& block.col>=0 && block.col<=7)
		return true;
	else
		return false;
}

//get an array of blocks for next possible moves of a piece
function getPossibleMoves(pieceAtBlock)
{
	pBlockArr= new Array();
	var block;
	if(pieceAtBlock.piece==PIECE_PAWN){
		if(currentTurn==RED_TEAM) pBlockArr.push(getBlock(pieceAtBlock.row,pieceAtBlock.col-1));
		if(currentTurn==GREEN_TEAM) pBlockArr.push(getBlock(pieceAtBlock.row-1,pieceAtBlock.col));
		if(currentTurn==YELLOW_TEAM) pBlockArr.push(getBlock(pieceAtBlock.row,pieceAtBlock.col+1));
		if(currentTurn==BLACK_TEAM) pBlockArr.push(getBlock(pieceAtBlock.row+1,pieceAtBlock.col));
	}
	if(pieceAtBlock.piece==PIECE_HORSE || pieceAtBlock.piece==PIECE_BOAT || pieceAtBlock.piece==PIECE_KING){
		var ruleList=rule.horse;
		if(pieceAtBlock.piece==PIECE_BOAT) ruleList=rule.boat;
		if(pieceAtBlock.piece==PIECE_KING) ruleList=rule.king;
		for(var i=0; i <ruleList.length;i++){
			block=getBlock(pieceAtBlock.row+ruleList[i][0],pieceAtBlock.col+ruleList[i][1]);
			var piece=getPieceAtBlock(block);
			//var piece=null;
			if( (isBlockInBoard(block)) && ( piece==null || piece.team!=currentTurn ) ){
				pBlockArr.push(block);
			}
		}
	}
	if(pieceAtBlock.piece==PIECE_ELEPHANT){
		var ruleList=rule.elephant;
		for(var i=0; i<4;i++){
			var row=pieceAtBlock.row;
			var col=pieceAtBlock.col;
			var end=false;
			while(!end){
				row+=ruleList[i][0];
				col+=ruleList[i][1];
				block=getBlock(row,col);
				if(isBlockInBoard(block)){
					var piece=getPieceAtBlock(block);
					if(piece!=null){
						end=true;
						if(piece.team!=currentTurn) 
							pBlockArr.push(block);
					}
					else
						pBlockArr.push(block);
				}
				else
					end=true;
			}
		}
	}

	return pBlockArr;
}

/** return the colour of a square according to its position on the board. **/
function getBlockColour(iRowCounter, iBlockCounter)
{
	var cStartColour;
	
	// Alternate the block colour
	if (iRowCounter % 2)
	{
		cStartColour = (iBlockCounter % 2 ? BLOCK_COLOUR_1:BLOCK_COLOUR_2);
	}
	else
	{
		cStartColour = (iBlockCounter % 2 ? BLOCK_COLOUR_2:BLOCK_COLOUR_1);
	}
	
	return cStartColour;
}

/** draws one suqare in the color specified by the square's position on the board **/

function drawBlock(iRowCounter, iBlockCounter)
{	
	// Set the background
	ctx.fillStyle = getBlockColour(iRowCounter, iBlockCounter);
	
	// Draw rectangle for the background
	ctx.fillRect(iRowCounter * BLOCK_SIZE, iBlockCounter * BLOCK_SIZE, 
		BLOCK_SIZE, BLOCK_SIZE);

	//ctx.stroke();	not necessary
}

/** getSvgImagePath returns image path and filename from pieceCode [0,1,2,3,5] and team **/
function getSvgImagePath(pieceCode, team)
{
	
	var colorCodeStr;
	var pieceNameStr;

	//get String from piece Code
	switch(pieceCode)
	{
		case 0:
		  pieceNameStr = "Pawn";
		  break;
		case 1:
		  pieceNameStr = "Elephant";
		  break;
		case 2:
		  pieceNameStr = "Knight";
		  break;
		case 3:
		  pieceNameStr = "Boat";
		  break; 
		case 5:
		  pieceNameStr = "King";
		  break;
		  
		default:
		  break;
	}
	
	
	// convert team into string
	
	var colorCodeStr = getColorStringFromId(team);
    
		
	// assemble location path and file name String	
	var pieceLocationStr = "img/" + colorCodeStr + pieceNameStr + ".svg";	
		
	
       
	
	return pieceLocationStr;
}

/** draws a specific piece of a particular team on the board **/
function drawPiece(curPiece, team)
{
	var loc = getSvgImagePath(curPiece.piece, team);
		
	var img = new Image();
	// wait for source to be fully loaded (especially for slower mobile devices!)
    img.onload = function(){
    	if(curPiece.status==IN_PLAY)// very important!!!!
    		ctx.drawImage(img, curPiece.col * BLOCK_SIZE, curPiece.row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
	};		
	// Draw the piece onto the canvas
	img.src = loc;	
	
				
}


/** removes the selection highlight by overwriting it with the underlying square and draws the corresponding piece on top **/
function removeSelection(selectedPiece)
{
	drawBlock(selectedPiece.col, selectedPiece.row);
	drawPiece(selectedPiece, selectedPiece.team);
	for(var i=0; i<pBlocksArr.length; i++){
		drawBlock(pBlocksArr[i].col,pBlocksArr[i].row);
		piece=getPieceAtBlock(pBlocksArr[i]);
		if(piece!=null)
			drawPiece(piece,piece.team);
	}
}


/** draws all pieces belonging to one team onto the board **/
function drawTeamOfPieces(teamOfPieces, team)
{
	var iPieceCounter;

	// Loop through each piece and draw it on the canvas	
	for (iPieceCounter = 0; iPieceCounter < teamOfPieces.length; iPieceCounter++) 
	{	
		drawPiece(teamOfPieces[iPieceCounter], team);
	}	
}


/** draws all pieces by all teams onto the board **/
function drawPieces()
{
	drawTeamOfPieces(json.red, RED_TEAM);
	drawTeamOfPieces(json.green, GREEN_TEAM);
	drawTeamOfPieces(json.yellow, YELLOW_TEAM);
    drawTeamOfPieces(json.black, BLACK_TEAM);   
		
}



/** draws one row of the board **/
function drawRow(iRowCounter)
{
	var iBlockCounter;
	
	// Draw 8 block left to right
	for (iBlockCounter = 0; iBlockCounter < NUMBER_OF_ROWS; iBlockCounter++)
	{
		drawBlock(iRowCounter, iBlockCounter);
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
	
	// Draw outline  ++ Removed because gets ovenwritten everything a piece is selected
	/*ctx.lineWidth = 3;
	ctx.strokeRect(0, 0, 
		NUMBER_OF_ROWS * BLOCK_SIZE +1, 
		NUMBER_OF_COLS * BLOCK_SIZE +1);	
		*/
		
	
}

function highlightPiece(pieceAtBlock,color)
{

	//draw background
	ctx.lineWidth = SELECT_LINE_WIDTH;
	ctx.fillStyle = color;
	ctx.fillRect((pieceAtBlock.col * BLOCK_SIZE),
		(pieceAtBlock.row * BLOCK_SIZE), 
		BLOCK_SIZE, 
		BLOCK_SIZE );
	
	drawPiece(pieceAtBlock, pieceAtBlock.team); //draw piece again so piece is on top
	
}

function highlightPossibleBlock(block,color)
{

	//draw background
	ctx.lineWidth = SELECT_LINE_WIDTH;
	ctx.fillStyle = color;
	ctx.fillRect((block.col * BLOCK_SIZE+BLOCK_SIZE/2-POSSIBLE_MOVE_SIZE/2),
		(block.row * BLOCK_SIZE+BLOCK_SIZE/2-POSSIBLE_MOVE_SIZE/2), 
		POSSIBLE_MOVE_SIZE, 
		POSSIBLE_MOVE_SIZE );
}


function selectPiece(pieceAtBlock)
{
	selectedPiece = pieceAtBlock;
	
	highlightPiece(pieceAtBlock,HIGHLIGHT_COLOUR);
	
	pBlocksArr=getPossibleMoves(pieceAtBlock);
	//var piece;
	for(var i=0; i<pBlocksArr.length; i++){
		//piece=getPieceAtBlock(pBlocksArr[i]);
		//if(piece==null)
			highlightPossibleBlock(pBlocksArr[i],MOVE_COLOUR);
		//else
			//highlightPiece(piece,MOVE_COLOUR);
	}
}

/** checks whether a piece is located on a square that has been clicked on **/
function checkIfPieceClicked(clickedBlock)
{
	var pieceAtBlock = getPieceAtBlock(clickedBlock);

	if (pieceAtBlock !== null && pieceAtBlock.team==currentTurn)
	{
		selectPiece(pieceAtBlock);
	}
}

/** calculates the next turn according to the current turn. **/
function getNextTurn(thisTurn){
    var nextTurn = RED_TEAM;
    if (thisTurn === RED_TEAM) nextTurn=GREEN_TEAM;
	else
	if(thisTurn===GREEN_TEAM) nextTurn=YELLOW_TEAM;
	else
	if(thisTurn===YELLOW_TEAM) nextTurn=BLACK_TEAM;
	else
	if(thisTurn===BLACK_TEAM) nextTurn=RED_TEAM;
	
	//notification
	if(nextTurn == thisPlayerId) alert("It's your turn");
	
	return nextTurn;
}


/** moves a piece to a specific square and takes an opposing piece if there is one on this square **/
function movePiece(clickedBlock, enemyPiece)
{
	// Clear the block in the original position
	drawBlock(selectedPiece.col, selectedPiece.row);
	
	var team = getJsonObjectOfTeam(currentTurn);

	alert(clickedBlock.col + clickedBlock.row);
	
/*	team[selectedPiece.position].col = clickedBlock.col;
	team[selectedPiece.position].row = clickedBlock.row;

	alert(team[selectedPiece.position].col + "test");
	alert(team[selectedPiece.position].row + "test");*/
	
	if (enemyPiece != null) 
    {
	    var opposite = getJsonObjectOfTeam(enemyPiece.team);
		// Clear the piece you are about to take
		drawBlock(enemyPiece.col, enemyPiece.row);	
		opposite[enemyPiece.position].status = TAKEN;
	}
	drawBlock(clickedBlock.col,clickedBlock.row);
	// Draw the piece in the new position
    drawPiece(team[selectedPiece.position], currentTurn);
	
	currentTurn = getNextTurn(currentTurn);
	
	selectedPiece = null;
}


function processMove(clickedBlock)
{
    var pieceAtBlock = getPieceAtBlock(clickedBlock);
//    if (canSelectedMoveToBlock(clickedBlock))
//    {
    	removeSelection(selectedPiece);
    	movePiece(clickedBlock, pieceAtBlock);
//    }
    //else
/*	if (pieceAtBlock.team == currentTurn)
	{
		removeSelection(selectedPiece);
		checkIfPieceClicked(clickedBlock);			
	}*/
}




/** event listener for a click on the board, calculates the click coordinates for further processing **/
function board_click(ev) 
{

	//only evaluate click events if it's the player's turn
	if(currentTurn == thisPlayerId){
			var x = ev.clientX - canvas.offsetLeft,
			y = ev.clientY - canvas.offsetTop,
			clickedBlock = screenToBlock(x, y);
		
		if (selectedPiece === null)
		{
			checkIfPieceClicked(clickedBlock);
		}
		else
		{
			
			// if a piece is selected, check if new click hits another piece of the same team
			
			var pieceAtBlock = getPieceAtBlock(clickedBlock);

			if (pieceAtBlock !== null && pieceAtBlock.team==currentTurn)
			{
				//select new piece
				removeSelection(selectedPiece);
				selectPiece(pieceAtBlock);
			}else{
				var fromBlock = getBlock(selectedPiece.row, selectedPiece.col); //get row and col of square with currently selected piece
				processMoveToServer(fromBlock, clickedBlock);
			}
		}
	}
}


/**
 *  gets the team color from team id
 */
function getColorStringFromId(id){
	var colorCodeStr;
	if (id == RED_TEAM) 
		colorCodeStr = "red";
    else 
    if (id == YELLOW_TEAM)
		colorCodeStr = "yellow";
    else 
    if (id == GREEN_TEAM)
		colorCodeStr = "green";
	else
		colorCodeStr = "black";
	return colorCodeStr;
}


function setPlayerId(id){
	thisPlayerId = id;
	var colorCodeStr = getColorStringFromId(id);
	//notification
	alert("You are player " + id + ". So your colour is " + colorCodeStr);
}



function startGame(){
	currentTurn = 0;
	//notification
	alert("The game begins");
	if(currentTurn == thisPlayerId) alert("It's your turn");
	
}



/** draw function is the main drawing function that initiates the composition of the board **/
function draw()
{
	

	canvas = document.getElementById('chess');

	// Canvas supported?
	if (canvas.getContext)
	{
		ctx = canvas.getContext('2d');

		// Calculdate the precise block size
		BLOCK_SIZE = canvas.height / NUMBER_OF_ROWS;
		
		// Draw the background
		drawBoard();

		defaultPositions();

		drawPieces();
		canvas.addEventListener('click', board_click, false);
	}
	else
	{
		//notification
		alert("Canvas not supported!");
	}
}