package chaturaji.game;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.Chess.board.Board;
import com.Chess.engine.Engine;
import com.Chess.piece.Piece;
import com.Chess.piece.PieceImpl;
import com.Chess.ply.Ply;
import com.Chess.ply.PlyImpl;
import com.Chess.position.Position;
import com.Chess.position.PositionImpl;
import static com.Chess.piece.Piece.*;

public class ChaturajiEngine implements Engine {
	
	private Board board;
	
	private Piece lastTaken;
	
	private List<String> boats;
	
	private Color boatTriumph;
	
	public ChaturajiEngine(Board board) {
		this.board = board;
		lastTaken = null;
		boatTriumph = null;
		boats = new ArrayList<String>();
	}

	@Override
	public void updateBoard(Position from, Position to) {
		Ply ply = new PlyImpl(from, to);
		lastTaken = board.doPly(ply);
	}

	public boolean checkMove(Color currentPlayer, JSONObject message) {
		Position ps = null;
		Position pd = null;
		try {
			String from = (String) message.get("from_sq");
			String to = (String) message.get("to_sq");
			
			char cs = from.charAt(0);
			char cd = to.charAt(0);
			int numbers = from.charAt(1);
			int numberd = to.charAt(1);

			ps = new PositionImpl(from);
			pd = new PositionImpl(to);

			PieceImpl selected = (PieceImpl)board.getPiece(ps);
			PieceImpl newPos = (PieceImpl)board.getPiece(pd);
			
			if ( selected == null || selected.getColor().getTurn() != currentPlayer.getTurn()) {
				System.out.println("Null or not your turn!");
				return false;
			}
			
			if (from.charAt(0) > 72 || from.charAt(0) < 65 || from.charAt(1) > 56 || from.charAt(1) < 49) {
				System.out.println("Out of bound");
				return false;
			}
			
			if (newPos != null && selected.getColor() == newPos.getColor()) {
				System.out.println("Don't eat your friends");
				return false;
			}
			
			
			switch (selected.getType()) {
			case PAWN:
				switch (selected.getColor()) {
				case RED:
					// A pawn can not go back.
					if (cs < cd) {
						return false;
					}
					
					// A pawn can only go diagonally to take another piece
					if (numbers != numberd && null == board.getPiece(pd)) {
						return false;
					}
					break;
				case GREEN:
					// A pawn can not go back.
					if (numbers > numberd) {
						return false;
					}
					
					// A pawn can only go diagonally to take another piece
					if (cs != cd && null == board.getPiece(pd)) {
						return false;
					}
					break;
				case YELLOW:
					// A pawn can not go back.
					if (cs > cd) {
						System.out.println("Don't go back you pawn (yellow)");
						return false;
					}
					
					// A pawn can only go diagonally to take another piece
					if (numbers != numberd && null == board.getPiece(pd)) {
						System.out.println("No diagonal for you yellow pawn");
						return false;
					}
					break;
				case BLACK:
					// A pawn can not go back.
					if (numbers < numberd) {
						System.out.println("Don't go back you pawn (black)");
						return false;
					}
					
					// A pawn can only go diagonally to take another piece
					if (cs != cd && null == board.getPiece(pd)) {
						System.out.println("No diagonal for you black pawn");
						return false;
					}
					break;
				}
				// A pawn can only move one square at a time
				if (Math.abs(cs - cd) > 1 || Math.abs(numbers - numberd) > 1) {
					System.out.println("Not too far pawn!");
					return false;
				}
				break;
			case KING:
				// A king can only move one square at a time
				if (Math.abs(from.charAt(0) - to.charAt(0)) > 1 || Math.abs(from.charAt(1) - to.charAt(1)) > 1) {
					System.out.println("Not too far king!");
					return false;
				}
				break;
			case BOAT:
				if (boats.contains(ps.toSquareName())) {
					boats.remove(ps.toSquareName());
				}
				boats.add(pd.toSquareName());
				System.out.println(boats);
				
				// A boat moves diagonally and exactly 2 squares
				if (Math.abs(cs - cd) != 2 || Math.abs(numbers - numberd) != 2) {
					System.out.println("Boat please!");
					return false;
				}
				
				// Triumph of the boat
				if (boats.size() == 4) {
					boolean tb = true;
					for (String p : boats) {
						if (!p.equals(pd.toSquareName())) {
							char posc = p.charAt(0);
							char posn = p.charAt(1);
							if (Math.abs(posc - cd) > 1 || Math.abs(posn - numberd) > 1) {
								tb = false;
								break;
							}
						}
					}
					if (tb) {
						boatTriumph = selected.getColor();
						for (int i = 0; i < 3; ++i) {
							board.setPiece(null, new PositionImpl(boats.get(i)));
						}
					}
				}
				break;
			case ELEPHANT:
				// The elephant must move in straight lines.
				if (cs != cd && numbers != numberd) {
					System.out.println("Elephant ... don't be gay");
					return false;
				}
				// The elephant can not jump over other pieces
				if (cs == cd) {
					int s = (numbers < numberd) ? numbers : numberd;
					int d = (numbers < numberd) ? numberd : numbers;
					for (int i = s + 1; i < d; ++i) {
						int index = (8-i)*8+cs-'A';
						PositionImpl p = new PositionImpl(index);
						if (board.getPiece(p) != null) {
							System.out.println("No jump");
							return false;
						}
					}
				}
				
				if (numbers == numberd) {
					int s = (cs < cd ? cs : cd);
					char d = (cs < cd ? cd : cs);
					for (char c = (char)(s + 1); c < d; ++c) {
						int index = (8-numbers)*8+c-'A';
						PositionImpl p = new PositionImpl(index);
						if (board.getPiece(p) != null) {
							System.out.println("No jump");
							return false;
						}
					}					
				}
				break;
			case KNIGHT:
				if (Math.abs(cs - cd) != 2 && Math.abs(numbers - numberd) != 2) {
					System.out.println("L you fool");
					return false;
				}
				if ((2 == Math.abs(cs - cd) && 1 != Math.abs(numbers - numberd)) || 
						(1 != Math.abs(cs - cd) && 2 == Math.abs(numbers - numberd))) {
					System.out.println("L you fool (2)");
					return false;
				}
			default:
				break;
			}
			
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		updateBoard(ps, pd);
		return true;
	}
	
	public Piece getLastTaken() {
		return lastTaken;
	}

	@Override
	public void startGame(Color AIColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAIColor(Color aiColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generatePly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean myTurn() {
		// TODO Auto-generated method stub
		return false;
	}

	public void playerOut(Color turn) {
		
	}
	
	public Color boatTriumph() {
		return boatTriumph;
	}
	
	public List<String> boats() {
		return boats;
	}
	
	@Override
	public void boatTriumph(List<String> boats) {
	}
	
}
