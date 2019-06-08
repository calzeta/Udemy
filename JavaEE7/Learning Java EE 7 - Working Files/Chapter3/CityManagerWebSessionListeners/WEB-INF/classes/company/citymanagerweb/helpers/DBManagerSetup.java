package company.citymanagerweb.helpers;
import javax.servlet.*;

import company.citymanagerweb.models.DBManager;
import company.citymanagerweb.models.MySQLServerConnectionBehavior;
import company.citymanagerweb.models.ServerConnectionBehavior;

public class DBManagerSetup implements ServletContextListener
{
	private DBManager dbm = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//cleanup the connection when the context is destroyed
		if (dbm != null)
		{
			if (dbm.isConnected())
			{
				dbm.closeConnection(false);
			}
		}
		dbm = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//access the servlet context from the event argument (renamed sce)
		//get db con info from context init params
		ServletContext sc = sce.getServletContext();
		String uid = sc.getInitParameter("dbuserid");
		String pwd = sc.getInitParameter("dbuserpwd");
		String cat = sc.getInitParameter("dbinitcat");
		
		//set the scb for mySQL
		ServerConnectionBehavior scb = 
				new MySQLServerConnectionBehavior(uid, pwd, cat);
		
		//create the manager
		dbm = new DBManager(scb);
		
		//put the manager into the servlet context attributes for global use in
		//the application
		sc.setAttribute("WorldDBManager", dbm);
		
		System.out.println("WorldDBManager created and added to context");
	}
}
