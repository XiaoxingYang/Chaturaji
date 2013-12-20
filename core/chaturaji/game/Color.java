package chaturaji.game;


public enum Color {
	RED (0),
	GREEN (1),
	YELLOW (2),
	BLACK (3);
	
	private int turn;
	
	private Color(int turn) {
		this.turn = turn;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public static Color getColorFromInt(int n){
		switch(n){
		case 0:
			return Color.RED;
		case 1:
			return Color.GREEN;
		case 2:
			return Color.YELLOW;
		case 3:
		default:
			return Color.BLACK;
		}
	}
	
}
