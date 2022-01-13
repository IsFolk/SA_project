package ncu.im3069.demo.app;

import org.json.*;

import ncu.im3069.demo.app.CourseHelper;


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

public class Course {
    
    /** id，課程編號 */
    private int id;
    
    /** sdl，需要學生修課名單內ㄉ學號們 */
   // private StudentList sdl;
    
    /** pfl，需要教授的ID */
   // private ProfessorList pfl;
    
    /** name，課程名稱 */
    private String name;
    
    /** detail，課程說明 */
    private String detail;
    
    /** semester，課程學期**/
    private String semester;  
    
    /** department，開課單位 **/
    private String department;

    /** credit，課程學分數**/
    private int credit;
    
    /** ch，CourseHelper之物件與Course相關之資料庫方法（Sigleton） */
    private CourseHelper ch =  CourseHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    public Course(String name, String detail, String semester, String department, int credit) {
        this.name = name;
        this.detail = detail;
        this.semester = semester;
        this.department = department;
        this.credit = credit;
        //update();
    }


    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     * @param login_times 更新時間的分鐘數
     * @param status the 會員之組別
     */
    public Course(int id, String name, String detail, String semester, String department, int credit) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.semester = semester;
        this.department = department;
        this.credit = credit;
        update(); //不知道必不必要加
    }
    
    public int getId() {
        return this.id;
    }


    
    /**
     * 取得課程名
     *
     * @return the name 回傳課名
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * 取得課程說明
     *
     * @return the detail 回傳課程說明
     */
    public String getDetail() {
        return this.detail;
    }
    

    /**
     * 取得開課學期
     *
     * @return the semester 回傳開課學期
     */
    public String getSemester() {
        return this.semester;
    }
    
    /**
     * 取得開課系所
     *
     * @return the department 回傳開課單位
     */
    public String getDepartment() {
        return this.department;
    }
    
    
    /**
     * 取得學分數
     *
     * @return the credit 回傳學分數
     */
    public int getCredit() {
        return this.credit;
    }    

    
    
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = ch.update(this);
        }
        
        //如果沒==>create
        
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("name", getName());
        jso.put("detail", getDetail());
        jso.put("semester", getSemester());
        jso.put("department", getDepartment());
        jso.put("credit", getCredit());
        
        return jso;
    }
 
    

}