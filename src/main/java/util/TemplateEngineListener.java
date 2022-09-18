package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.thymeleaf.TemplateEngine;

/**
 * Application Lifecycle Listener implementation class TemplateEngineListener
 *
 */
@WebListener
public class TemplateEngineListener implements ServletContextListener {
	TemplateEngine engine = null;

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
         engine = null;
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
    	engine = TemplateEngineUtil.getTemplateEngine(arg0.getServletContext());
    }

}
