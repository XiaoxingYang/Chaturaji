package chaturaji.AI.unit;

import junit.framework.TestCase;
import com.Chess.position.*;

public class TestPostion extends TestCase {
	public void testPosition(){
		Position p=new PositionImpl(7);
		//assertEquals(p.getSquareIndex(),7);
//		assertEquals(p.getLineIndex(),7);
//		assertEquals(p.getRowIndex(),0);
		assertEquals(p.toSquareName(),"H8");
		

	}
	
}
