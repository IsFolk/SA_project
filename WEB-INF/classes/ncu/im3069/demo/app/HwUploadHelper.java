package ncu.im3069.demo.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import sa_project.util.DBMgr;

public class HwUploadHelper {
	public HwUploadHelper(){
		
	}
	
	public static HwUploadHelper buh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
    public static HwUploadHelper getHelper() {
        if(buh == null) buh = new HwUploadHelper();
        
        return buh;
    }
    
    
    public JSONObject upload(Homework h) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        System.out.println(h);
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            
            int hwid=h.getHwId();
            String att_name = h.getAttachmentname();
            
            String sql = "INSERT INTO homeworkattachment (`HwId`, `Attachment`)" + "VALUES(?, ?)";
            pres = conn.prepareStatement(sql);
            pres.setInt(1, hwid);
            pres.setString(2, att_name);
            
            row = pres.executeUpdate();
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql = pres.toString());
            System.out.println(hwid);
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