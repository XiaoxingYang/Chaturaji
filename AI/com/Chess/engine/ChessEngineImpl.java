package com.Chess.engine;

import chaturaji.game.Color;

import com.Chess.ply.*;
import com.Chess.board.*;
import com.Chess.bitBoard.*;
import com.Chess.hashtable.*;
//import com.Chess.ply.AnalyzedPly;




/**
 * This interface defines the functionality of a engine
 * to play the game of chess.
 */
public class ChessEngineImpl implements Runnable, ChessEngine{


    /**
     * The maximum search depth.
     */
    private int _maxSearchTime = 5000;
    
    /**
     * The current game.
     */
    private BoardStack _boardStack;
    
    /**
     * The board to operate on.
     */
    private Board _board;
    
    /**
     * Flag to indicate if the engine operates on the AI pieces.
     */
    Color _AIColor;
   
    
    /**
     * The generator for the plies.
     */
    PlyGenerator _plyGenerator;
    
    /**
     * The best computed ply so far.
     */
    AnalyzedPly _bestPly = null;
    
    /**
     * A hashtable for computed plies.
     */
    PlyHashtable _hashtable;

    /**
     * The currently used search depth.
     */
    int _searchDepth;
    
    /**
     * Flag to stop the search.
     */
    boolean _stopSearch;
    
    /**
     * A counter for the analyzed boards.
     */
    long _analyzedBoards;
    
    /**
     * A analyzer for the boards.
     */
    private BitBoardAnalyzer _analyzer;
    
    private byte _playerStatus;
    
    public ChessEngineImpl(Board board, Color AIColor) {
    	_board = board;
    	_AIColor = AIColor;
    }
    
    public byte get_playerStatus(){
    	return _playerStatus;

    }
    
    /* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_maxSearchTime()
	 */
    public int get_maxSearchTime() {
		return _maxSearchTime;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_maxSearchTime(int)
	 */
	public void set_maxSearchTime(int maxSearchTime) {
		_maxSearchTime = maxSearchTime;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_boardStack()
	 */
	public BoardStack get_boardStack() {
		return _boardStack;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_boardStack(com.Chess.bitBoard.BoardStack)
	 */
	public void set_boardStack(BoardStack boardStack) {
		_boardStack = boardStack;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_board()
	 */
	public Board get_board() {
		return _board;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_board(com.Chess.bitBoard.BitBoard)
	 */
	public void set_board(BitBoard board) {
		_board = board;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_AIColor()
	 */
	public Color get_AIColor() {
		return _AIColor;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_AIColor(chaturaji.game.Color)
	 */
	public void set_AIColor(Color aIColor) {
		_AIColor = aIColor;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_plyGenerator()
	 */
	public PlyGenerator get_plyGenerator() {
		return _plyGenerator;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_plyGenerator(com.Chess.engine.PlyGenerator)
	 */
	public void set_plyGenerator(PlyGenerator plyGenerator) {
		_plyGenerator = plyGenerator;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#get_hashtable()
	 */
	public PlyHashtable get_hashtable() {
		return _hashtable;
	}

	/* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#set_hashtable(com.Chess.hashtable.PlyHashtable)
	 */
	public void set_hashtable(PlyHashtable hashtable) {
		_hashtable = hashtable;
	}
	
    /* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#isAIColor(chaturaji.game.Color)
	 */
    public boolean isAIColor(Color color){
    	return _AIColor == color;
    }
    
    /**
     * Create a new engine instance with a given board.
     *
     * @param board The new board.
     * @param white Flag, to indicate if the engine operates on the white pieces.
     */
    public ChessEngineImpl(BitBoard board, Color AIColor, byte playerStatus) {
    	set_board( board);
    	set_AIColor(AIColor);
    	_boardStack = new BoardStackImpl(board);
    	_analyzer = new BitBoardAnalyzerImpl(AIColor);
    	_hashtable = new PlyHashtableImpl(5000);
    	_playerStatus = playerStatus;
    }
    

    /**
     * A thread to search for the best move.
     */
    Thread _searchThread;
    
    /* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#start()
	 */
    public void start() {
    	if( _searchThread == null) {
    	    _stopSearch = false;
    	    _searchThread = new Thread( this);
    	    _searchThread.start();
    	}
    }
    
    /* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#computeBestPly(byte)
	 */
    public Ply computeBestPly(Color AIcolor){
    	
    	_bestPly = null;
    	long startTime = System.currentTimeMillis();
    	
    	start();
    	try {
    	    Thread.sleep( get_maxSearchTime());
    	    _stopSearch = true;
    	    _searchThread.join();      // Wait for the search thread to end the search at this search depth.
    	    _searchThread = null;      // Remove the thread, so it can be recreated for the next move.
    	} catch( InterruptedException ignored) {}
    	long usedTime = System.currentTimeMillis() - startTime;
    	System.out.println("Time spent in this search is "+usedTime+" ms");
    	
    	if(_bestPly != null){
    		return _bestPly.getPly();
    	}
    	return null;
    }
    
    /* (non-Javadoc)
	 * @see com.Chess.engine.ChessEngine#run()
	 */
    public void run() {
	_analyzedBoards = 0;
	_searchDepth = 0;

	// The following search is rather inefficent at the moment, since we should try to get a principal variant
	// from a search, so we can presort the plies for the next search.
	// Thread currentThread = Thread.currentThread();
	do {
	    _searchDepth++;
	    AnalyzedPly searchDepthResult = startMinimaxAlphaBeta(get_AIColor());
	    if( searchDepthResult != null) {
		_bestPly = searchDepthResult;
		System.out.println("Best ply for search depth " + _searchDepth + " is " + _bestPly.getPly().toString() + " with score " + _bestPly.getScore());
	    }
	} while( ! _stopSearch && ( _bestPly != null));
    }
    
    
    
    /**
     * Start a complete Minimax-Alpha-Beta search. This is the search level 1, where we have to store the
     * analyzed ply, so it gets a special method.
     *
     * @param white Flag to indicate, if white is about to move.
     */
    private final AnalyzedPly startMinimaxAlphaBeta( Color AIcolor) {
    
	short curAlpha = AnalyzedPly.MIN_SCORE;
	short curBeta = AnalyzedPly.MAX_SCORE;
	int bestPlyIndex = -1;

	_plyGenerator = new PlyGenerator();
	Ply [] plies = _plyGenerator.getPliesForColor((BitBoard) get_board(), AIcolor);

	    for( int i = 0; i < plies.length; i++) {
			if( _stopSearch && ( _searchDepth > 1)) {  // If the search time is over and at least depth 1 was completed
			    return null;                           // abort the search at search depth 1.
			}                                          // (Deeper levels are still searched).
			//get_boardStack().getLastBoardStatus();
			Board boardBeforePly = get_boardStack().getLastBoard();
			
			get_boardStack().doPly( plies[i]);
			
			short val = minimaxAlphaBeta( plies[i], get_boardStack().getLastBoard(),boardBeforePly, Color.getColorFromInt ((AIcolor.getTurn()+1)%4), 1, curAlpha, curBeta);
			if( val > curAlpha) {
			    curAlpha = val;
			    bestPlyIndex = i;
			}
			if( curAlpha >= curBeta) {
				get_boardStack().undoLastPly();  // Take the last ply back, before the loop is aborted.
			    break;
			}
			get_boardStack().undoLastPly();
	    }

	    // Since this is the best ply so far, we store it in the hashtable. This makes sense,
	    // since the minimax algorithm is started several times, before a move is selected.
	    // So this move is not necessarily applied immediately!
	    get_hashtable().pushEntry( new PlyHashtableEntryImpl( get_board(), plies[bestPlyIndex],AIcolor));

	    return ( bestPlyIndex != -1) ? new AnalyzedPlyImpl( plies[bestPlyIndex], curAlpha) : null;
	
    }

    
    
    /**
     * Perform a alpha-beta minimax search on the board.
     *
     * @param lastPly The ply, that created this board.
     * @param board The board to analyze.
     * @param next move color.
     * @param byte searchLevel The level to search for.
     * @param alpha The current maximum.
     * @param beta The current minimum.
     */
    private final short minimaxAlphaBeta( Ply lastPly, Board board,Board lastBoard, Color color, int searchLevel, short alpha, short beta) {
	if( searchLevel >= _searchDepth) {
	    _analyzedBoards++;
	    return _analyzer.analyze( board,lastBoard,lastPly, color,get_playerStatus());
	} else {
	    short curAlpha = alpha;
	    short curBeta = beta;
	    int bestPlyIndex = -1;

	    _plyGenerator = new PlyGenerator();
	    Ply [] plies = _plyGenerator.getPliesForColor( (BitBoard)board,  color);
	    if( isAIColor(color)) {
		for( int i = 0; i < plies.length; i++) {
			Board boardBeforePly = get_boardStack().getLastBoard();
			get_boardStack().doPly( plies[i]);
		    short val = minimaxAlphaBeta( plies[i], get_boardStack().getLastBoard(),boardBeforePly, Color.getColorFromInt ((color.getTurn()+1)%4), searchLevel + 1, curAlpha, curBeta);
		    if( val > curAlpha) {
			curAlpha = val;
			bestPlyIndex = i;  // Store the index of this ply, so we can access it later.
		    }
		    if( curAlpha >= curBeta) {
		    	get_boardStack().undoLastPly();  // Take the last ply back, before the loop is aborted.
			break;
		    }
		    get_boardStack().undoLastPly();
		}

		if( bestPlyIndex != -1) {
		    // Since this is the best ply for this search level, we store it in the hashtable
		    get_hashtable().pushEntry( new PlyHashtableEntryImpl( board, plies[ bestPlyIndex],color));
		}

		return curAlpha;
	    } else {
		for( int i = 0; i < plies.length; i++) {
			Board boardBeforePly = get_boardStack().getLastBoard();
			get_boardStack().doPly( plies[i]);
		    short val = minimaxAlphaBeta( plies[i], get_boardStack().getLastBoard(),boardBeforePly, Color.getColorFromInt ((color.getTurn()+1)%4), searchLevel + 1, curAlpha, curBeta);
		    if( val < curBeta) {
			curBeta = val;
			bestPlyIndex = i;  // Store the index of this ply, so we can access it later.
		    }
		    if( curBeta <= curAlpha) {
		    	get_boardStack().undoLastPly();  // Take the last ply back, before the loop is aborted.
			break;
		    }
		    get_boardStack().undoLastPly();
		}

		if( bestPlyIndex != -1) {
		    // Since this is the best ply for this search level, we store it in the hashtable
		    get_hashtable().pushEntry( new PlyHashtableEntryImpl( board, plies[ bestPlyIndex],color));
		}

		return curBeta;
	    }}
	}

    
    

    
    
    
    
    
    
    
}
