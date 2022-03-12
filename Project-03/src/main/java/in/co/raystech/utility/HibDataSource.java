
package in.co.raystech.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate DataSource is provides the object of sessionfactory and session
 * @author Vikas Singh
 *
 */
public class HibDataSource {
  private static SessionFactory sessionFactory=null;
  
  public static SessionFactory getSessionFactory(){
	  if(sessionFactory==null){
		  sessionFactory = new Configuration().configure().buildSessionFactory();
	  }
	  return sessionFactory;
  }
  
  public static Session getSession(){
	  Session session= getSessionFactory().openSession();
	  return session;
	  
  }
  public static void closeSession(Session session){
	  if(session!=null){
		  session.close();
	  }
  }
}
