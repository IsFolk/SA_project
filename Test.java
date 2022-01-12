package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Test {
    
    /** id，課程編號 */
    private int id;
    
    //CourseId
    
    /** name，課程名稱 */
    private String name;
    
    /** detail，考試說明 */
    private String detail;
    
    //private ArrayList<Question> list = new ArrayList<Question>();
    
    /** opening_time，測驗開始時間**/
    private Timestamp opening_time;  
    
    /** end_time，測驗結束時間 **/
    private Timestamp end_time;
    


    /** th，TestHelper之物件與Course相關之資料庫方法（Sigleton） */
    private QuestionHelper qsh =  QuestionHelper.getHelper();
    
    private TestHelper th =  TestHelper.getHelper();

    
   
    /**新增一個考試**/
    public Test(String name, String detail, Timestamp opening_time, Timestamp end_time) {
        this.name = name;
        this.detail = detail;
        this.opening_time = opening_time;
        this.end_time = end_time;
        //update();
    }
    
    
    /**查看、修改一個考試資訊 **/ 
    public Test(int id, String name, String detail, Timestamp opening_time, Timestamp end_time) {
      this.id = id;
      this.name = name;
      this.detail = detail;
      this.opening_time = opening_time;
      this.end_time = end_time;
      update();
  }
  
    
    /**顯示一個考試資訊 **/ 
    public Test(String name,Timestamp end_time) {
      //this.id = id;
      this.name = name;
      this.end_time = end_time;
  }
    

    
    public void setId(int id) {
    	this.id = id;
    }
    
    

    public int getID() {
        return this.id;
    }
    
    

    public String getName() {
        return this.name;
    }

    

    public String getDetail() {
        return this.detail;
    }
    

    public Timestamp getOpeningTime() {
        return this.opening_time;
    }
    

    public Timestamp getEndTime() {
        return this.end_time;
    }
    
    

    
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = th.update(this);
        }
        
        //如果沒==>create
        
        
        return data;
    }
    
   
    /**
     * 取得該課程所有資料
     *
     * @return the data 取得該課程之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getTestData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("detail", getDetail());
        jso.put("opening_time", getOpeningTime());
        jso.put("end_time", getEndTime());
        
        return jso;
    }

    
}
