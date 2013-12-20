
window.log = function() {
    log.history = log.history || []; // store logs to an array for reference
    log.history.push(arguments);
    if (this.console) {
        console.log(Array.prototype.slice.call(arguments));
    }
}



var selectedField = [
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0]
	             ];






getPos = function(obj,e){
var row = e.target.offsetLeft/50;
var column = e.target.offsetTop/50;


document.getElementById('board').innerHTML = "";


selectedField = [
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0],
		     [0,0,0,0,0,0,0,0]
	             ];

selectedField[column][row] = 1;
$(function(){

    drawBoard(board);
    
});

}


var BLACK_KING = 1;
var BLACK_ELEPHANT = 2;
var BLACK_KNIGHT = 3;
var BLACK_BOAT = 4;
var BLACK_KNIGHT = 5;
var BLACK_PAWN = 6;

var RED_KING = 7;
var RED_ELEPHANT = 8;
var RED_KNIGHT = 9;
var RED_BOAT = 10;
var RED_KNIGHT = 11;
var RED_PAWN = 12;

var GREEN_KING = 13;
var GREEN_ELEPHANT = 14;
var GREEN_KNIGHT = 15;
var GREEN_BOAT = 16;
var GREEN_KNIGHT = 17;
var GREEN_PAWN = 18;

var YELLOW_KING = 19;
var YELLOW_ELEPHANT = 20;
var YELLOW_KNIGHT = 21;
var YELLOW_BOAT = 22;
var YELLOW_KNIGHT = 23;
var YELLOW_PAWN = 24;


var board = [[BLACK_BOAT, BLACK_PAWN, 0, 0, RED_KING, RED_ELEPHANT, RED_KNIGHT, RED_BOAT],
             [BLACK_KNIGHT, BLACK_PAWN, 0, 0, RED_PAWN, RED_PAWN, RED_PAWN, RED_PAWN],
             [BLACK_ELEPHANT, BLACK_PAWN ,0,0,0,0,0,0],
             [BLACK_KING,BLACK_PAWN,0,0,0,0,0,0],
             [0,0,0,0,0,0,GREEN_PAWN, GREEN_KING],
             [0,0,0,0,0,0,GREEN_PAWN, GREEN_ELEPHANT],
	     [YELLOW_PAWN,YELLOW_PAWN,YELLOW_PAWN,YELLOW_PAWN,0,0,GREEN_PAWN,GREEN_KNIGHT],
             [YELLOW_BOAT,YELLOW_KNIGHT,YELLOW_ELEPHANT,YELLOW_KING,0,0,GREEN_PAWN,GREEN_BOAT]];



function getPieceName(pieceValue){
    switch (pieceValue) {
        case BLACK_KING:
            return 'BLACK_KING';
            break;
        case BLACK_ELEPHANT:
            return 'BLACK_ELEPHANT';
            break;
        case BLACK_KNIGHT:
            return 'BLACK_KNIGHT';
            break;
        case BLACK_BOAT:
            return 'BLACK_BOAT';
            break;
        case BLACK_PAWN:
            return 'BLACK_PAWN';
            break;
 
       case RED_KING:
            return 'RED_KING';
            break;
        case RED_ELEPHANT:
            return 'RED_ELEPHANT';
            break;
        case RED_KNIGHT:
            return 'RED_KNIGHT';
            break;
        case RED_BOAT:
            return 'RED_BOAT';
            break;
        case RED_PAWN:
            return 'RED_PAWN';
            break;
        
       case GREEN_KING:
            return 'GREEN_KING';
            break;
        case GREEN_ELEPHANT:
            return 'GREEN_ELEPHANT';
            break;
        case GREEN_KNIGHT:
            return 'GREEN_KNIGHT';
            break;
        case GREEN_BOAT:
            return 'GREEN_BOAT';
            break;
        case GREEN_PAWN:
            return 'GREEN_PAWN';
            break;

       case YELLOW_KING:
            return 'YELLOW_KING';
            break;
        case YELLOW_ELEPHANT:
            return 'YELLOW_ELEPHANT';
            break;
        case YELLOW_KNIGHT:
            return 'YELLOW_KNIGHT';
            break;
        case YELLOW_BOAT:
            return 'YELLOW_BOAT';
            break;
        case YELLOW_PAWN:
            return 'YELLOW_PAWN';
            break;

        default:
            return 'EMPTY';
            break;
    }
}


$(function(){

    drawBoard(board);
    
});

function drawBoard(board){

    var str = '';
    for( var i = 0 ; i < 8 ; i++ ){
        str += '<div class="row">';
        for( var j = 0 ; j < 8 ; j++ ){
		
	    
            str += '<div class="column ' +
            ( (i + j) % 2 === 0 ? 'light': 'dark') + '" ' + ( (selectedField[i][j] === 1) && (board[i][j] != 0) ? 'style="background:#F2F5A9"' : '') +'">' +
            '<div class="' + getPieceName(board[i][j]) + '"></div>' +
            '</div>';
        }
        str += '</div>';
    }
    $('#board').append(str);
}