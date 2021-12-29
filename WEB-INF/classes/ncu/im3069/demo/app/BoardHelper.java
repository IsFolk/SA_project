package ncu.im3069.demo.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.json.*;

import ncu.im3069.demo.app.BoardHelper;
import ncu.im3069.demo.app.Board;
import ncu.im3069.demo.util.DBMgr;

import java.util.ArrayList;
import java.util.Date;

/**與資料庫連結**/
public class BoardHelper {

	public BoardHelper(){
		
	}
	
	public static BoardHelper bh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
    public static BoardHelper getHelper() {
        if(bh == null) bh = new BoardHelper();
        
        return bh;
    }
    
    /**新增公告**/
    public JSONObject create(Board b) {
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        
        try {
            conn = DBMgr.getConnection();
            
            
            String sql = "INSERT INTO `sa_project`.`board`(`CourseId`, `BoardName`, `BoardDetails`, `UpdateTime`)"
                    + " VALUES(?, ?, ?, ?)";
            
            int course_id = b.getCourseId();
            String name = b.getBoardName();
            String details =b.getBoardDetails();
            Date updatetime = b.getUpdateTime();
            
            pres = conn.prepareStatement(sql);
            pres.setInt(1, course_id);
            pres.setString(2, name);
            pres.setString(3, details);
            pres.setString(4, updatetime.toString());
            


            row = pres.executeUpdate();
            
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(pres, conn);
        }

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    
    /**刪除公告**/
    public JSONObject deleteByID(int id) {
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        ResultSet rs = null;
        
        try {
            conn = DBMgr.getConnection();
            
            String sql = "DELETE FROM board inner JOIN boardattachment where board.BoardId = ? and boardattachment.BoardId = ?";
           
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            pres.setInt(2, id);
            row = pres.executeUpdate();

            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);
        
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }
    
    /**提取所有公告**/
    public JSONObject getALL() {
	Board b = null;
    JSONArray jsa = new JSONArray();
    String exexcute_sql = "";
    long start_time = System.nanoTime();
    int row = 0;
    ResultSet rs = null;
    
    try {
        conn = DBMgr.getConnection();
        String sql = "SELECT `BoardId`, `BoardName`, `UpdateTime` FROM `sa_project`.`Board`";
        
        pres = conn.prepareStatement(sql);
        rs = pres.executeQuery();

        exexcute_sql = pres.toString();
        System.out.println(exexcute_sql);
        
        while(rs.next()) {

            row += 1;
            int id = rs.getInt("BoardId");
            String name = rs.getString("BoardName");
            String updatetime = rs.getString("UpdateTime");
            Date update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updatetime);

            
            b = new Board(id, name, update);
            jsa.put(b.getAllData());
        }

    } catch (SQLException e) {
        System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        DBMgr.close(rs, pres, conn);
    }
    
    long end_time = System.nanoTime();
    long duration = (end_time - start_time);
    
    JSONObject response = new JSONObject();
    response.put("sql", exexcute_sql);
    response.put("row", row);
    response.put("time", duration);
    response.put("data", jsa);

    return response;
    }
    
    /**提取單個公告**/
    public JSONObject getByID(String id) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Board b = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = 
            		"SELECT board.BoardId, BoardName, BoardDetail, Attachment, UpdateTime FROM board inner JOIN boardattachment where board.BoardId = ? and boardattachment.BoardId = ?";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            
            pres.setString(1, id);
            pres.setString(2, id);
            System.out.println(pres.toString());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            
            int board_id = 0;
            String name = null;
            String details = null;
            String updatetime;
            ArrayList<String> totalattachment = new ArrayList<String>();
            Date update = null;
            
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                board_id = rs.getInt("BoardId");
                name = rs.getString("BoardName");
                details = rs.getString("BoardDetail");
                String attachment = rs.getString("Attachment");
                updatetime = rs.getString("UpdateTime");
                
                totalattachment.add(attachment);
                
                                
                update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updatetime);
                
                /** 將每一筆會員資料產生一名新Member物件 */
            }
            b = new Board(board_id, name, details, totalattachment ,update);
            /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
            jsa.put(b.getData());
         
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    /**更新公告**/
    public JSONObject update(Board b) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa_project`.`board` SET `BoardName` = ? ,`BoardDetails` = ? , `UpdateTime` = ? WHERE `BoardId` = ?";
            /** 取得所需之參數 */
            int boardid=b.getBoardId();
            String name = b.getBoardName();
            String details = b.getBoardDetails();
            String updatetime = new Date().toString();
            
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, details);
            pres.setString(3, updatetime);
            pres.setInt(4,boardid);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
}
