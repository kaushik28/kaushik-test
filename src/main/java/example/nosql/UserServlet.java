package example.nosql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.String;
import java.lang.StringBuilder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.model.Params;
import com.cloudant.client.org.lightcouch.Attachment;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/User")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		StringBuilder jsonBuff = new StringBuilder();
		String line = null;
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		        jsonBuff.append(line);
		} catch (Exception e) { /*error*/ }
		
		System.out.println("Request JSON string :" + jsonBuff.toString());
		//write the response here by getting JSON from jasonBuff.toString()
		
		String username = "";
		
		try {
			JsonParser parser = new JsonParser();
		    JsonObject jsonObject = parser.parse(jsonBuff.toString()).getAsJsonObject();
		
		    System.out.print(jsonObject.get("name"));//writing output as you did
		    
		    String username = jsonObject.get("username");
		    String password = jsonObject.get("password");
		    
		    String userResponse = "{\"status\": \"success\",\"message\": \"Hi " + username + ", how are you?\"}";
		
		} catch (Exception e) {
		    throw new IOException("Error parsing JSON ");
		}
		response.setContentType("application/json");
		response.getWriter().print(userResponse);
	}

}
