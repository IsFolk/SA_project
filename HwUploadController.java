package sa_project.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import sa_project.tools.JsonReader;
import sa_project.app.Homework;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/api/HwUploadController")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1,
		maxFileSize = 1024 * 1024 * 10,
		maxRequestSize = 1024 * 1024 * 100
	)
public class HwUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HwUploadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        JsonReader jsr = new JsonReader(request);
        System.out.println(jsr);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request);
//		JSONObject jso = jsr.getObject();
		JSONObject resp = new JSONObject();
		System.out.println(request);
		
		System.out.println("Post");
		System.out.print(request.toString());
		Collection<Part> filePart = request.getParts();
		System.out.print(filePart);
		String fileName = null;
		String BoardKey = null;
		
		
		if(filePart != null) {
			for (Part elem : filePart) {
				BoardKey = elem.getName();
				fileName = elem.getSubmittedFileName();
			}
			
			
	//		byte[] filenms = fileName.getBytes();
	//		
	//
	//		
			for(Part part : request.getParts()) {
				System.out.print(fileName);
				System.out.print(BoardKey);
				part.write("C:\\Users\\andy2\\eclipse-workspace\\sa_project\\statics\\attachment\\homework\\123\\" + fileName);
				System.out.print("ok");
			}
			
			Homework h = new Homework(Integer.parseInt(BoardKey), fileName);
			
			JSONObject query = h.upload();

	        resp.put("response", query);
		}
		
        resp.put("status", "200");
        resp.put("message", "更改成功");
		//System.out.print("ok");
		
        response.setStatus(200);
		
	}

}
