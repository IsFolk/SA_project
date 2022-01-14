   public JSONObject getByID(String id, String id2) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
    	HandInHw p = null;
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
            String sql = "SELECT * FROM (SELECT * FROM sa_project.hw WHERE CourseId=?) AS hw\r\n"
            		+ "INNER JOIN (SELECT * FROM sa_project.handinhw WHERE StudentId=?) AS handinhw ON hw.StudentId=hw.StudentId\r\n";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1,id);
            pres.setString(2,id2);
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
                int c_id = rs.getInt("OpeningTime");
                int hw_id = rs.getInt("EndindTime");
                int st_id = rs.getInt("HwDetail");
                String detail = rs.getString("HwHandInDetail");
                int score =rs.getInt("HwHandInScore");
                int courseid=rs.getInt("CourseId");
                
                JSONObject jso = new JSONObject();
                jso.put("OpeningTime", c_id);
                jso.put("EndingTime", hw_id);
                jso.put("HwDetail", st_id);
                jso.put("HwHandInDetail",detail);
                jso.put("HwHandInScore", score);
                jso.put("CourseId", courseid);
                
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(jso);
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
