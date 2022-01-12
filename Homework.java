package sa_project.app;

import org.json.JSONObject;

public class Homework {
	
	public int CourseId;
	public String HwType;
	public String HwOpeningTime;
	public String HwEndingTime;
	public String HwUpdateTime;
	public String HwDetail;
	public String HwName;
	public String HwDetailAttachment;
	public int HwId;
	
	private HomeworkHelper hh =  HomeworkHelper.getHelper();
	
	
	/**拿取文件**/
	public Homework(int courseid, String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname, String detailattachment, int hwid) {
		this.CourseId=courseid;
		this.HwType=hwtype;
		this.HwOpeningTime=hwopeningtime;
		this.HwEndingTime=hwendingtime;
		this.HwUpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.HwDetailAttachment=detailattachment;
	}
	/**新增作業(有說明文件)**/
	public Homework(int courseid, String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname, String detailattachment) {
		this.CourseId=courseid;
		this.HwType=hwtype;
		this.HwOpeningTime=hwopeningtime;
		this.HwEndingTime=hwendingtime;
		this.HwUpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.HwDetailAttachment=detailattachment;
	}
	
	/**新增作業(無說明文件)**/
	public Homework(int courseid,String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname) {
		this.CourseId=courseid;
		this.HwType=hwtype;
		this.HwOpeningTime=hwopeningtime;
		this.HwEndingTime=hwendingtime;
		this.HwUpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
	}
	
	
	/**刪除作業**/
	public Homework(int hwid) {
		hh.DeleteById(hwid);
	}
	
	/**更新作業**/
	public Homework(int CourseId, int hwid, String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname) {
		this.HwId=hwid;
		this.HwOpeningTime=hwopeningtime;
		this.HwEndingTime=hwendingtime;
		this.HwUpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.CourseId=CourseId;
	}
	
	
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("CourseId", getCourseId());
        jso.put("HwType", getHwType());
        jso.put("HwOpeningTime", getHwOpeningTime());
        jso.put("HwEndingTime", getHwEndingTime());
        jso.put("HwUpdateTime", getHwUpdateTime());
        jso.put("HwDetail", getHwDetail());
        jso.put("HwName", getHwName());
        jso.put("HwDetailAttachment", getHwDetailAttachment());
        jso.put("HwId", getHwId());
        
        return jso;
    }
    
    /**更新**/
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        data = hh.update(this);
        
        return data;
    }
	public int getCourseId() {
		return this.CourseId;
	}
	public String getHwType() {
		return this.HwType;
	}
	
	public String getHwOpeningTime() {
		return this.HwOpeningTime;
	}
	
	public String getHwEndingTime() {
		return this.HwEndingTime;
	}
	
	public String getHwUpdateTime() {
		return this.HwUpdateTime;
	}
	
	public String getHwDetail() {
		return this.HwDetail;
	}
	
	public String getHwName() {
		return this.HwName;
	}
	
	public String getHwDetailAttachment() {
		return this.HwDetailAttachment;
	}
	
	public int getHwId() {
		return this.HwId;
	}
	
}
