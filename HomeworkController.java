package sa_project.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import sa_project.app.Homework;
import sa_project.app.HomeworkHelper;
import sa_project.tools.JsonReader;
import java.util.Date;

/**
 * Servlet implementation class ProfessorController
 */
@WebServlet("/HomeworkController")


// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberController<br>
 * MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class HomeworkController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private HomeworkHelper hh =  HomeworkHelper.getHelper();
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	
    	Date date = new Date();
    	String hwupdatetime = date.toString();
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        String hwdetailattachment = jsr.getParameter("HwDetailAttachment");
        
        /**判斷有無附加文件 以建立不同建構子**/
        if (hwdetailattachment.isEmpty()) {
        	int courseid = jso.getInt("CourseId");
        	String hwtype = jso.getString("HwType");
        	String hwopeningtime = jso.getString("HwOpeningTime");
        	String hwendingtime = jso.getString("HwEndingTime");
        	String hwdetail = jso.getString("HwDetail");
        	String hwname = jso.getString("HwName");
 
        	Homework h = new Homework(courseid, hwtype, hwopeningtime, hwendingtime, hwupdatetime, hwdetail, hwname);
        
        	/** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        	if(hwtype.isEmpty() || hwopeningtime.isEmpty() || hwendingtime.isEmpty() || hwname.isEmpty()) {
        		/** 以字串組出JSON格式之資料 */
        		String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
        		/** 透過JsonReader物件回傳到前端（以字串方式） */
        		jsr.response(resp, response);
        	}
        	/** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        	else {
        		/** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
        		JSONObject data = hh.create1(h);
            
        		/** 新建一個JSONObject用於將回傳之資料進行封裝 */
        		JSONObject resp = new JSONObject();
        		resp.put("status", "200");
        		resp.put("message", "成功! 註冊會員資料...");
            	resp.put("response", data);
            
            	/** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            	jsr.response(resp, response);
        	}
        }
        else {
        	int courseid = jso.getInt("CourseId");
        	String hwtype = jso.getString("HwType");
        	String hwopeningtime = jso.getString("HwOpeningTime");
        	String hwendingtime = jso.getString("HwEndingTime");
        	String hwdetail = jso.getString("HwDetail");
        	String hwname = jso.getString("HwName");
 
        	Homework h = new Homework(courseid, hwtype, hwopeningtime, hwendingtime, hwupdatetime, hwdetail, hwname, hwdetailattachment);
        
        	/** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        	if(hwtype.isEmpty() || hwopeningtime.isEmpty() || hwendingtime.isEmpty() || hwname.isEmpty()) {
        		/** 以字串組出JSON格式之資料 */
        		String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
        		/** 透過JsonReader物件回傳到前端（以字串方式） */
        		jsr.response(resp, response);
        	}
        	/** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        	else {
        		/** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
        		JSONObject data = hh.create2(h);
            
        		/** 新建一個JSONObject用於將回傳之資料進行封裝 */
        		JSONObject resp = new JSONObject();
        		resp.put("status", "200");
        		resp.put("message", "成功! 註冊會員資料...");
            	resp.put("response", data);
            
            	/** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            	jsr.response(resp, response);
        	}
        }
    }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("HwId");
        
        System.out.println(request);
        
        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
        if (id.isEmpty()) {
            /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = hh.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過MemberHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = hh.getByID(id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
    }

    /**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
       
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("HwId");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = hh.DeleteById(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        Date date = new Date();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("HwId");
        String hwtype = jso.getString("HwType");
        String hwopeningtime = jso.getString("HwOpeningTime");
        String hwendingtime = jso.getString("HwEndingTime");
        String hwupdatetime = date.toString();
        String hwdetail = jso.getString("HwDetail");
        String hwname = jso.getString("HwName");
        int hwscore = jso.getInt("HwScore");

        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
        Homework h = new Homework(id, hwtype, hwopeningtime, hwendingtime, hwupdatetime, hwdetail, hwname, hwscore);
        
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = h.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
	public String HwType;
	public String HwOpeningTime;
	public String HwEndingTime;
	public String HwUpdateTime;
	public String HwDetail;
	public String HwName;
	public int HwScore;
	public String HwDetailAttachment;
}
