package com.bluenose;

/******************************************
Created by: Borong Zhou
Created at: Jun 25, 2013:2:43:02 PM 
File: BNAMetrics.java
Comments:
 ******************************************/

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
 
/**
 * on web browser
 *  http://localhost:8088/
 *  
 * @author borongzhou
 *
 */
public class BNAMetrics extends AbstractHandler
{
	static final String SERVICE_NAME = "/metrics";
	static final String OP_GET_NAME = "/read";
	static final String METRIC_NAME = "/scoring";
	
	@Override
	public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
		
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
		String requestURI = baseRequest.getRequestURI();
		
		if (requestURI.startsWith(SERVICE_NAME + "/") == false) {
			response.getWriter().println("<h1>404 Not Found</h1>");
			return;
		}
		requestURI = requestURI.substring(SERVICE_NAME.length());

		if (requestURI.startsWith(OP_GET_NAME) == false) {
			response.getWriter().println(
					"<h1>Currently only supporting \"/get\" operation</h1>");
			return;
		}
		requestURI = requestURI.substring(OP_GET_NAME.length());

		if (requestURI.startsWith(METRIC_NAME) == false) {
			response.getWriter().println(
					"<h1>Currently only supporting \"/scoring\" metric</h1>");
			return;
		}
		
		// get parameters
		Map<String, String[]> params = request.getParameterMap();

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(
				"/Users/borongzhou/metrics-server/EnablementScores.json"));
		StringBuilder json = new StringBuilder();
		line = br.readLine();

		if (line != null) {
			json.append(line);

			while ((line = br.readLine()) != null) {
				json.append("\n").append(line);
			}
		}

		br.close();
		line = json.toString().trim();
		line = line.isEmpty() ? "No data available!" : line;

		response.getWriter().println(line);
	}
	
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8088);
        server.setHandler(new BNAMetrics());
 
        server.start();
        server.join();
    }
    
    /**
     * 
		Operation	     	SQL		HTTP
		Create	        	INSERT	POST
		Read (Retrieve)		SELECT	GET
		Update (Modify)		UPDATE	PUT / PATCH
		Delete (Destroy)	DELETE	DELETE

     *
     */
    public static enum ACTION_NAME {
    	CREATE,
    	READ,
    	UPDATE,
    	DELETE
    }
}
