package chaturaji.login;

import org.hibernate.Session;
import org.hibernate.Transaction;

import chaturaji.util.HibernateUtil;

public class UserDb implements UserDbItf {
	
	@Override
	public boolean addUser(User user) {
		User db_user = load(user.getLogin());
		if (db_user != null) {
			return false;
		}
		persist(user);
		return true;
	}

	@Override
	public void delUser(User user) {
		User db_user = load(user.getLogin());
		if (db_user != null) {
			remove(db_user);
		}
	}

	@Override
	public boolean checkLoginInfo(User user) {
		User db_user = load(user.getLogin());
		if (db_user != null && db_user.getPassword().equals(user.getPassword())) {
			return true;
		}
		return false;
	}
	
	private User load(String Id) {
		User user = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			user = (User)session.get(User.class, Id);
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	private void persist(Object o) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.save(o);
			t.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void remove(Object o) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.delete(o);
			t.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void update(Object o) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.update(o);
			t.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
