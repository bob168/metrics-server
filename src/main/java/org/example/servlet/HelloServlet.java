package org.example.servlet;

/******************************************
Created by: Borong Zhou
Created at: Jun 26, 2013:1:32:42 PM 
File: HelloServlet.java
Comments:
 ******************************************/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5385307507060673387L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}