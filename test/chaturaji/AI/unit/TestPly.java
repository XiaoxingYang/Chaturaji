package chaturaji.AI.unit;

import junit.framework.TestCase;
import com.Chess.ply.*;
import com.Chess.position.*;

public class TestPly extends TestCase {
	public void testPly(){
		Position s1=new PositionImpl(1);
		Position p1=new PositionImpl(3);
		Ply ply1=new PlyImpl(s1,p1);
//		assertEquals(ply1.toString(),"b8-d8");
		
		Position s2=new PositionImpl(1);
		Position p2=new PositionImpl(2);
		Ply ply2=new PlyImpl(s1,p1);
		assertTrue(ply1.equals(ply2));
		
	}
}
