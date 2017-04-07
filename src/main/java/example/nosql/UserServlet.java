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
import org.json.JSONObject;

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
		
		try {
		    JSONObject jsonObject = JSONObject.fromObject(jsonBuff.toString());
		
		    System.out.print(jsonObject.get("name"));//writing output as you did
		
		} catch (Exception e) {
		    throw new IOException("Error parsing JSON ");
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("user");
		String key = request.getParameter("password");

		Document fav = CloudantClientMgr.getDB().find(Document.class, id, new Params().attachments());

		OutputStream output = response.getOutputStream();
		Attachment attachment = fav.getAttachments().get(URLEncoder.encode(key, "UTF-8"));
		String attachmentData = attachment.getData();
		response.setContentType(attachment.getContentType());
		output.write(Base64.decodeBase64(attachmentData));

	}

}
