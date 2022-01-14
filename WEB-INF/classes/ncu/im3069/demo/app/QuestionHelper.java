package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import com.mysql.cj.ServerPreparedQueryTestcaseGenerator;

import ncu.im3069.demo.util.DBMgr;

public class QuestionHelper {
    
    private QuestionHelper() {
        
    }
    
    private static QuestionHelper qsh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static QuestionHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(qsh == null) qsh = new QuestionHelper();
        
        return qsh;
    }
 
    
    
    
    
    public JSONObject create(Question q) {
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
            String sql = "INSERT INTO `sa_project`.`question`(`QuestionText`,`OptionA`,`OptionB`,`OptionC`,`OptionD`,`CorrectAnswer1`,`CorrectAnswer2`,`CorrectAnswer3`,`CorrectAnswer4`,`GivePoint`)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            
            
            
            /** 取得所需之參數 */
            //int testId = q.getTestId();
            String text = q.getText();
            String optionA = q.getOptionA();
            String optionB = q.getOptionB();
            String optionC = q.getOptionC();
            String optionD = q.getOptionD();
            String answerA = q.getAnswerA();
            String answerB = q.getAnswerB();
            String answerC = q.getAnswerC();
            String answerD = q.getAnswerD();
            int givePoint = q.getGivePoint();
            
            
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            //pres.setInt(1, testId);
            pres.setString(1, text);
            pres.setString(2, optionA);
            pres.setString(3, optionB);
            pres.setString(4, optionC);
            pres.setString(5, optionD);
            pres.setString(6, answerA);
            pres.setString(7, answerB);
            pres.setString(8, answerC);
            pres.setString(9, answerD);
            pres.setInt(10, givePoint);

            
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
    
    
    
    
   
    public JSONObject deleteByID(int id) {
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
            String sql = "DELETE FROM `sa_project`.`question` WHERE `QuestionId` = ?  LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
           // pres.setInt(2, test_id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
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
            DBMgr.close(rs, pres, conn);
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

        return response;
    }   
    

    
    public JSONObject update(Question q) {
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
            String sql = "Update `sa_project`.`question` SET  `QuestionText` = ? , ˋOptionAˋ = ? ,ˋOptionBˋ = ? ,ˋOptionCˋ = ?, ˋOptionDˋ = ?, ˋCorrectAnswer1ˋ = ? , ˋCorrectAnswer2ˋ = ?, ˋCorrectAnswer3ˋ = ?, ˋCorrectAnswer4ˋ = ?, ˋGivePointˋ = ? WHERE `QuestionId` = ? and `TestId`= ? ";
            /** 取得所需之參數 */
            String text = q.getText();
            String optionA = q.getOptionA();
            String optionB = q.getOptionB();
            String optionC = q.getOptionC();
            String optionD = q.getOptionD();
            String answerA = q.getAnswerA();
            String answerB = q.getAnswerB();
            String answerC = q.getAnswerC();
            String answerD = q.getAnswerD();
            int givePoint = q.getGivePoint();
            int question_id = q.getId();
            //int test_id = q.getTestId();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, text);
            pres.setString(2, optionA);
            pres.setString(3, optionB);
            pres.setString(4, optionC);
            pres.setString(5, optionD);
            pres.setString(6, answerA);
            pres.setString(7, answerB);
            pres.setString(8, answerC);
            pres.setString(9, answerD);
            pres.setInt(10, givePoint);
            pres.setInt(11, question_id);
            //pres.setInt(12, test_id);
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
    
    
   

    public JSONObject getByID(String id) { 
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Question q = null;
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
            String sql = "SELECT * FROM `sa_project`.`question` WHERE `QuestionId` = ?  LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);   
            //pres.setString(2, test_id);
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
                int question_id = rs.getInt("QuestionId");
                //int TestId = rs.getInt("TestId");
                String text = rs.getString("QuestionText");
                String optionA = rs.getString("OptionA");
                String optionB = rs.getString("OptionB");
                String optionC = rs.getString("OptionC");
                String optionD = rs.getString("OptionD");
                String answerA = rs.getString("CorrectAnswer1");
                String answerB = rs.getString("CorrectAnswer2");
                String answerC = rs.getString("CorrectAnswer3");
                String answerD = rs.getString("CorrectAnswer4");
                int givePoint = rs.getInt("GivePoint");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                q = new Question(question_id, text, optionA, optionB, optionC, optionD, answerA, answerB, answerC, answerD, givePoint);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(q.getData());
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
    
    

    

    public JSONObject getAll() {
        Question q = null;
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
            String sql = "SELECT * FROM `sa_project`.`question`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            //pres.setInt(1, test_id);
            
            
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
                int question_id = rs.getInt("QuestionId");
                String text = rs.getString("QuestionText");
                String optionA = rs.getString("OptionA");
                String optionB = rs.getString("OptionB");
                String optionC = rs.getString("OptionC");
                String optionD = rs.getString("OptionD");
                String answerA = rs.getString("CorrectAnswer1");
                String answerB = rs.getString("CorrectAnswer2");
                String answerC = rs.getString("CorrectAnswer3");
                String answerD = rs.getString("CorrectAnswer4");
                int givePoint = rs.getInt("GivePoint");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                q = new Question(question_id, text, optionA, optionB, optionC, optionD, answerA, answerB, answerC, answerD, givePoint);
               
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(q.getData());
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
