package ncu.im3069.demo.app;

import java.util.ArrayList;

import org.json.JSONObject;

public class Homework {
	
	public int CourseId;
	public String Type;
	public String OpeningTime;
	public String EndingTime;
	public String UpdateTime;
	public String HwDetail;
	public String HwName;
	public String HwDetailAttachment;
	public int HwId;
	public ArrayList<String> Attachment = new ArrayList<String>();
	public String Attachmentname;
	
	private HomeworkHelper hh =  HomeworkHelper.getHelper();
	private HwUploadHelper hwuh = HwUploadHelper.getHelper();
	
	/**拿取文件**/
	public Homework(int courseid, String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname, String detailattachment, int hwid) {
		this.CourseId=courseid;
		this.Type=hwtype;
		this.OpeningTime=hwopeningtime;
		this.EndingTime=hwendingtime;
		this.UpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.HwDetailAttachment=detailattachment;
		this.HwId=hwid;
	}
	/**新增作業(有說明文件)**/
	public Homework(int courseid, String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname, String detailattachment) {
		this.CourseId=courseid;
		this.Type=hwtype;
		this.OpeningTime=hwopeningtime;
		this.EndingTime=hwendingtime;
		this.UpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.HwDetailAttachment=detailattachment;
	}
	
	/**新增作業(無說明文件)**/
	public Homework(int courseid,String hwtype, String hwopeningtime, String hwendingtime, String hwupdatetime, String hwdetail, String hwname) {
		this.CourseId=courseid;
		this.Type=hwtype;
		this.OpeningTime=hwopeningtime;
		this.EndingTime=hwendingtime;
		this.UpdateTime=hwupdatetime;
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
		this.OpeningTime=hwopeningtime;
		this.EndingTime=hwendingtime;
		this.UpdateTime=hwupdatetime;
		this.HwDetail=hwdetail;
		this.HwName=hwname;
		this.CourseId=CourseId;
	}
	
	
	public Homework(int HomeworkId, String Attachment) {
		this.HwId = HomeworkId;
		this.Attachmentname = Attachment;
		
	}
	
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("CourseId", getCourseId());
        jso.put("Type", getHwType());
        jso.put("OpeningTime", getHwOpeningTime());
        jso.put("EndingTime", getHwEndingTime());
        jso.put("UpdateTime", getHwUpdateTime());
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
		return this.Type;
	}
	
	public String getHwOpeningTime() {
		return this.OpeningTime;
	}
	
	public String getHwEndingTime() {
		return this.EndingTime;
	}
	
	public String getHwUpdateTime() {
		return this.UpdateTime;
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
	
    public JSONObject upload() {
    	JSONObject data = new JSONObject();
    	data = hwuh.upload(this);
    	
    	return data;
    }
	public String getAttachmentname() {
		// TODO Auto-generated method stub
		return this.Attachmentname;
	}
	
}