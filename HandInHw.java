package sa_project.app;

import org.json.JSONObject;

public class HandInHw {
    
	private int CourseId;
	private int HwId;
	private int StudentId;
	private String HwHandInDetail;
	private int HwHandInScore;
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
    public HandInHw(int courseid, int hwid, int studentid, String HwHandInDetail) {
        this.CourseId=courseid;
        this.HwId = hwid;
        this.StudentId = studentid;
        this.HwHandInDetail = HwHandInDetail;
    }
    public HandInHw(int courseid, int hwid, int studentid, String HwHandInDetail, int score) {
        this.CourseId=courseid;
        this.HwId = hwid;
        this.StudentId = studentid;
        this.HwHandInDetail = HwHandInDetail;
        this.HwHandInScore=score;
    }
    
    public HandInHw(int courseid, int hwid, String detail) {
        this.CourseId=courseid;
        this.HwId = hwid;
        this.HwHandInDetail= detail;
        update();
    }

    public int getCourseId() {
    	return this.CourseId;
    }
    
    public int getHwId() {
    	return this.HwId;
    }
    
    public int getStudentId() {
    	return this.StudentId;
    }
    
    public String getHwHandInDetail() {
    	return this.HwHandInDetail;
    }
    
    public int getHwHandInScore() {
    	return this.HwHandInScore;
    }
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
            data = hih.update(this);
        
        return data;
    }
    
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("CourseId", getCourseId());
        jso.put("HwId", getHwId());
        jso.put("StudentId", getStudentId());
        jso.put("HwHandInDretail",getHwHandInDetail());
        jso.put("HwHandInScore", getHwHandInScore());

        
        return jso;
    }


    

}
