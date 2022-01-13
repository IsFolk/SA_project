package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Board;
import ncu.im3069.demo.app.BoardHelper;
import ncu.im3069.tools.JsonReader;

import java.util.ArrayList;
import java.util.Date;

// api/BoardController
public class BoardController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private BoardHelper bh = BoardHelper.getHelper();
    
    /** 新增公告**/
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        System.out.println(jso);
        
        Date date= new Date();
        /** 取出經解析到JSONObject之Request參數 */
        String boardname = jso.getString("BoardName");
        String details = jso.getString("BoardDetails");
        int courseid = jso.getInt("CourseId");
        ArrayList<String> attachment = new ArrayList<String>();
        
        if(jso.getString("Attachment") != null) {
	        String att = jso.getString("Attachment");
	        char[] attc = att.toCharArray();
	        System.out.println("att:"+att);
	        String tmp = "";
	//        System.out.println(att);
	        for(int i = 0; i < att.length(); i++) {
	//        	System.out.println(attc[i]);
	        	if(attc[i] == ',') {
	        		attachment.add(tmp);
	        		tmp = "";
	        		continue;
	        	}else if(i == (att.length()-1)) {
	        		tmp += attc[i];
	        		attachment.add(tmp);
	        		break;
	        	}
	        	tmp += attc[i];
	        }
        }
        String updatetime=date.toString();
        
        /** 建立一個新的會員物件 */
        Board b = new Board(boardname, courseid, details, attachment, date);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(boardname.isEmpty() || details.isEmpty()||updatetime.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過ManagerHelper物件的checkDuplicate()檢查該管理員電子郵件信箱是否有重複 */
        else {
            /** 透過ManagerHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = bh.create(b);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增公告...");
            resp.put("response", data);
            
            System.out.println("resp:"+resp);
            
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String BoardId = jsr.getParameter("BoardId");
        String CourseId = jsr.getParameter("CourseId");
        System.out.println("BoardId:"+BoardId);
        System.out.println("CourseId"+CourseId);

        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        if (!BoardId.isEmpty()) {
          JSONObject query = bh.getByID(BoardId);
          resp.put("status", "200");
          resp.put("message", "所有購物車之商品資料取得成功");
          resp.put("response", query);
        }else if(!CourseId.isEmpty()) {
            JSONObject query = bh.getALL(CourseId);

            resp.put("status", "200");
            resp.put("message", "所有公告資料取得成功");
            resp.put("response", query);
        }
//        else {
//          JSONObject query = bh.getALL();
//
//          resp.put("status", "200");
//          resp.put("message", "所有商品資料取得成功");
//          resp.put("response", query);
//        }
        jsr.response(resp, response);
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
        int id = jso.getInt("BoardId");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = bh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "公告移除成功！");
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
        
        System.out.println(jso);
        /** 取出經解析到JSONObject之Request參數 */
        String boardid = jso.getString("BoardId");
        String courseid = jso.getString("CourseId");
        String boardname = jso.getString("BoardName");
        String details = jso.getString("BoardDetails");
        Date date = new Date();

        ArrayList<String> attachment = new ArrayList<String>();
        
        if(jso.getString("Attachment") != "") {
	        String att = jso.getString("Attachment");
	        char[] attc = att.toCharArray();
	        System.out.println("att:"+att);
	        String tmp = "";
	//        System.out.println(att);
	        for(int i = 0; i < att.length(); i++) {
	//        	System.out.println(attc[i]);
	        	if(attc[i] == ',') {
	        		attachment.add(tmp);
	        		tmp = "";
	        		continue;
	        	}else if(i == (att.length()-1)) {
	        		tmp += attc[i];
	        		attachment.add(tmp);
	        		break;
	        	}
	        	tmp += attc[i];
	        }
        }
        
        System.out.println("attachment:"+attachment);
        
        
		/** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
        Board b = new Board(Integer.parseInt(boardid), Integer.parseInt(courseid),boardname, details, attachment, date);
        
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = b.update();
        System.out.println("b.update()");
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
}
