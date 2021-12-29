package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.app.LoginHelper;
import ncu.im3069.demo.app.Manager;
import ncu.im3069.demo.app.ManagerHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/api/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private LoginHelper lh =  LoginHelper.getHelper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
	
		
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        System.out.println(request);
        System.out.println(jsr.getObject());
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String id = jso.getString("ManagerId");
        String passwordfromlogin = jso.getString("Password");
        
        JSONObject logres = lh.getByID(id);
        
//        System.out.println(logres);
//        System.out.println("id:"+id+"password:"+password);
//        System.out.println(logres.getJSONArray("data"));
//        //System.out.println(logres.getJSONArray("data").getString("Password"));
        
        JSONArray  jsonArray = logres.getJSONArray("data");

        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String passwordfromdatabase = jsonObject.getString("Password");
        
        
//        System.out.println(pass);
//        System.out.println("reqpas:"+password);
//        System.out.println("pass:" + pass +" password:"+password);
        
        if(passwordfromdatabase.equals(passwordfromlogin)) {
        	JSONObject resp = new JSONObject();
        	
        	System.out.println("密碼相同");
            resp.put("status", "200");
            resp.put("message", "登入成功");
            System.out.println(resp);
        	jsr.response(resp, response);
        }
    }
}
