package sa_project.app;

import org.json.*;


// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Professor
 * Professor類別（class）具有教授所需要之屬性與方法，並且儲存與教授相關之判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Professor extends User{
    
	private int CourseListId;
	private String Position;
    /** ph，ProfessorHelper之物件與Professor相關之資料庫方法（Sigleton） */
    private ProfessorHelper ph =  ProfessorHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Professor物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立教授資料時，產生一名新的教授
     *
     * @param email 教授電子信箱
     * @param password 教授密碼
     * @param name 教授姓名
     */
    public Professor(String email, String password, String name) {
        this.Email = email;
        this.Password = password;
        this.Name = name;
        update();
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
     * 
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    public Professor(int id, String email, String password, String name) {
        this.Id = id;
        this.Email = email;
        this.Password = password;
        this.Name = name;
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
    public Professor(int id, String email, String password, String name, String department, String position) {
        this.Id = id;
        this.Email = email;
        this.Password = password;
        this.Name = name;
        this.Department = department;
        this.Position=position;
    }
    
    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getId() {
        return this.Id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.Email;
    }
    
    /**
     * 取得會員之姓名
     *
     * @return the name 回傳會員姓名
     */
    public String getName() {
        return this.Name;
    }

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public String getPassword() {
        return this.Password;
    }
    
    /**
     * 取得會員資料之會員組別
     *
     * @return the status 回傳會員組別
     */
    public String getDepartment() {
        return this.Department;
    }
    
    public int getCourseListId() {
        return this.CourseListId;
    }
    
    public String getPosition() {
    	return this.Position;
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
        if(this.Id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = ph.update(this);
        }
        
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
        jso.put("ProfessorId", getId());
        jso.put("ProfessorName", getName());
        jso.put("Position", getPosition());
        jso.put("Department",getDepartment());
        jso.put("Email", getEmail());
        jso.put("Password", getPassword());
        jso.put("CourseListId", getCourseListId());
        
        return jso;
    }
    

}