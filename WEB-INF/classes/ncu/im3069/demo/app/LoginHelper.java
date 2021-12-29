package ncu.im3069.demo.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.util.DBMgr;

public class LoginHelper {
	private LoginHelper() {
		
	}
	
    /** 靜態變數，儲存MemberHelper物件 */
    private static LoginHelper lh;
    
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    public static LoginHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(lh == null) lh = new LoginHelper();
        
        return lh;
    }
    
	
    /**
     * 透過管理員編號（ID）取得會員資料
	 *
	 * @param id 管理員編號
	 * @return the JSON object 回傳SQL執行結果與該管理員編號之管理員資料
	 */
	public JSONObject getByID(String id) {
	    /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
	    Manager m = null;
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
	        String sql = "SELECT * FROM `sa_project`.`manager` WHERE `ManagerId` = ? LIMIT 1";
	        
	        /** 將參數回填至SQL指令當中 */
	        pres = conn.prepareStatement(sql);
	        pres.setString(1, id);
	        /** 執行查詢之SQL指令並記錄其回傳之資料 */
	        rs = pres.executeQuery();
	
	        /** 紀錄真實執行的SQL指令，並印出 **/
	        exexcute_sql = pres.toString();
	        System.out.println(exexcute_sql);
	        
	        /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
	        /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
	        while(rs.next()) {
	            /** 每執行一次迴圈表示有一筆資料 */
	            row += 1;
	            
	            /** 將 ResultSet 之資料取出 */
	            int manager_id = rs.getInt("ManagerId");
	            String name = rs.getString("ManagerName");
	            String email = rs.getString("ManagerEmail");
	            String password = rs.getString("Password");
	            int login_times = rs.getInt("login_times");
	            
	            /** 將每一筆會員資料產生一名新Member物件 */
	            m = new Manager(manager_id, email, password, name, login_times);
	            /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
	            jsa.put(m.getData());
	        }
	        
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

}
