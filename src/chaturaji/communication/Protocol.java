package chaturaji.communication;

import org.json.JSONObject;

public interface Protocol {
	
	public void send(JSONObject message);
	
}
