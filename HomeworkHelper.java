package sa_project.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import sa_project.util.DBMgr;

public class HomeworkHelper {

private HomeworkHelper() {
        
    }
    private static HomeworkHelper hh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static HomeworkHelper getHelper() {
        if(hh == null) hh = new HomeworkHelper();
        
        return hh;
    }
    
    /**新增作業**/
    public JSONObject create2(Homework h) {
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
            String sql = "INSERT INTO `sa_project`.`hw`(`CourseId`, `Type`, `OpeningTime`, `EndingTime`, `HwDetail`, `HwName`, `HwDetailAttachment`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            int courseid = h.getCourseId();
            String hwtype = h.getHwType();
            String hwopeningtime = h.getHwOpeningTime();
            String hwendingtime=h.getHwEndingTime();
            String hwdetail=h.getHwDetail();
            String hwname=h.getHwName();
            String hwdetailattachment=h.getHwDetailAttachment();
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, courseid);
            pres.setString(2, hwtype);
            pres.setString(3, hwopeningtime);
            pres.setString(4, hwendingtime);
            pres.setString(5, hwdetail);
            pres.setString(6, hwname);
            pres.setString(6, hwdetailattachment);
            

            /** 執行新增之SQL指令並記錄影響之行數 */
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
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    /**新增作業**/
    public JSONObject create1(Homework h) {
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
            String sql = "INSERT INTO `sa_project`.`hw`(`CourseId`, `Type`, `OpeningTime`, `EndingTime`, `HwDetail`, `HwName`)"
                    + " VALUES(?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            int courseid = h.getCourseId();
            String hwtype = h.getHwType();
            String hwopeningtime = h.getHwOpeningTime();
            String hwendingtime=h.getHwEndingTime();
            String hwdetail=h.getHwDetail();
            String hwname=h.getHwName();
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, courseid);
            pres.setString(2, hwtype);
            pres.setString(3, hwopeningtime);
            pres.setString(4, hwendingtime);
            pres.setString(5, hwdetail);
            pres.setString(6, hwname);
            
         
            /** 執行新增之SQL指令並記錄影響之行數 */
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
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    /**刪除作業**/
    public JSONObject DeleteById(int id) {
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        ResultSet rs = null;
        
        try {
            conn = DBMgr.getConnection();
            String sql = "DELETE FROM `sa_project`.`hw` WHERE `HwId` = ? LIMIT 1";
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
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
    
    /**拿取全部作業**/
    public JSONObject getAll(String id) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Homework h = null;
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
            String sql = "SELECT * FROM `sa_project`.`hw` WHERE `CourseId`=?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int courseid = rs.getInt("CourseId");
                String hwtype = rs.getString("Type");
                String hwopeningtime = rs.getString("OpeningTime");
                String hwendingtime = rs.getString("EndingTime");
                String hwupdatetime = rs.getString("UpdateTime");
                String hwdetail = rs.getString("HwDetail");
                String hwname = rs.getString("HwName");
                String hwdetailattachment = rs.getString("HwDetailAttachment");
                int hwid = rs.getInt("HwId");
                		
                /** 將每一筆會員資料產生一名新Member物件 */
                h = new Homework(courseid, hwtype, hwopeningtime, hwendingtime, hwupdatetime, hwdetail, hwname, hwdetailattachment, hwid);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(h.getData());
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
    
    public JSONObject getByID(String id) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Homework h = null;
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
            String sql = "SELECT * FROM `sa_project`.`hw` WHERE `HwId` = ? LIMIT 1";
            
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
                int courseid = rs.getInt("CourseId");
                String hwtype = rs.getString("Type");
                String hwopeningtime = rs.getString("OpeningTime");
                String hwendingtime = rs.getString("EndingTime");
                String hwupdatetime = rs.getString("UpdateTime");
                String hwdetail = rs.getString("HwDetail");
                String hwname = rs.getString("HwName");
                String hwdetailattachment = rs.getString("HwDetailAttachment");
                int hwid = rs.getInt("HwId");
                		
                /** 將每一筆會員資料產生一名新Member物件 */
                h = new Homework(courseid, hwtype, hwopeningtime, hwendingtime, hwupdatetime, hwdetail, hwname, hwdetailattachment, hwid);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(h.getData());
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
    
    public JSONObject update(Homework h) {
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
            String sql = "Update `sa_project`.`hw` SET `Type` = ? ,`OpeningTime` = ? ,`EndingTime` = ? , `UpdateTime` = ? ,`HwDetail`= ? ,`HwName` = ?  WHERE `HwId` = ?";
            /** 取得所需之參數 */
            int hwid = h.getHwId();
            String hwtype = h.getHwType();
            String hwopeningtime = h.getHwOpeningTime();
            String hwendingtime = h.getHwEndingTime();
            String hwupdatetime = h.getHwUpdateTime();
            String hwdetail = h.getHwDetail();
            String hwname = h.getHwName();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, hwtype);
            pres.setString(2, hwopeningtime);
            pres.setString(3, hwendingtime);
            pres.setString(4, hwupdatetime);
            pres.setString(5, hwdetail);
            pres.setString(6, hwname);
            pres.setInt(7, hwid);
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
