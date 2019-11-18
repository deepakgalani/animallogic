package animallogic.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;


/*
 * RestHttpServletDispatcher is the class that makes the REST calls work.
 * Servlet mechanism is the underlying mechanism for the REST in the Web Project.
 * */
public class RestHttpServletDispatcher extends HttpServletDispatcher {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		try {
			super.service(httpServletRequest, httpServletResponse);
		} catch (Exception ex) {
			httpServletResponse.getWriter().println("Some error occurred, please contact administrator!!");
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
