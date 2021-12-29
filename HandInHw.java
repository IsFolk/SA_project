package sa_project.app;

import org.json.JSONObject;

public class HandInHw {
    
	private int CourseId;
	private int HwId;
	private int StudentId;
    /** ph，ProfessorHelper之物件與Professor相關之資料庫方法（Sigleton） */
    private HandInHwHelper hih =  HandInHwHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Professor物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立教授資料時，產生一名新的教授
     *
     * @param email 教授電子信箱
     * @param password 教授密碼
     * @param name 教授姓名
     */
    public HandInHw(int courseid, int hwid, int studentid) {
        this.CourseId=courseid;
        this.HwId = hwid;
        this.StudentId = studentid;
        update();
    }

    public int getCourseId() {
    	return this.CourseId;
    }
    
    public int getHwId() {
    	return this.CourseId;
    }
    
    public int getStudentId() {
    	return this.CourseId;
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
