package ncu.im3069.demo.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.util.DBMgr;

public class BoardUploadHelper {
	public BoardUploadHelper(){
		
	}
	
	public static BoardUploadHelper buh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
    public static BoardUploadHelper getHelper() {
        if(buh == null) buh = new BoardUploadHelper();
        
        return buh;
    }
    
    
    public JSONObject upload(Board b) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        System.out.println(b);
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            
            int boardid=b.getBoardId();
            String att_name = b.getAttachmentname();
            System.out.println("att_name(Buh):"+att_name);
            System.out.println("Buh");
            
            if(att_name != null) {
	            String sql = "INSERT INTO boardattachment (`BoardId`, `Attachment`)" + "VALUES(?, ?)";
	            pres = conn.prepareStatement(sql);
	            pres.setInt(1, boardid);
	            pres.setString(2, att_name);
	            
	            row = pres.executeUpdate();
	            exexcute_sql = pres.toString();
	            System.out.println(exexcute_sql = pres.toString());
            }
            System.out.println(boardid);
            System.out.println(att_name);
            

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
