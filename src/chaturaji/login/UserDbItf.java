package chaturaji.login;

public interface UserDbItf {
	
	public boolean addUser(User user);
	
	public void delUser(User user);
	
	public boolean checkLoginInfo(User user);
	
}
