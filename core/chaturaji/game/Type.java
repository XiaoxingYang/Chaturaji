package chaturaji.game;


	public enum Type {
		EMPTY (0),
		PAWN (1),
		BOAT (2),
		KNIGHT (3),
		ELEPHANT (4),
		KING (5);
		
		private int type;
		
		private Type(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public static Type getTypeFromInt(int n){
			switch(n){
			case 1:
				return Type.PAWN;
			case 2:
				return Type.BOAT;
			case 3:
				return Type.KNIGHT;
			case 4:
				return Type.ELEPHANT;
			case 5:
				return Type.KING;
			default:
				return Type.EMPTY;	
			}
		}
		
		public static int getIntFromType(Type type){
			switch(type){
			case PAWN:
				return 1;
			case BOAT:
				return 2;
			case KNIGHT:
				return 3;
			case ELEPHANT:
				return 4;
			case KING:
				return 5;
			default:
				return 0;
			}
		}
	}

