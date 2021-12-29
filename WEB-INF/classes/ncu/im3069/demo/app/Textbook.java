package ncu.im3069.demo.app;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

public class Textbook {
	
	public int TextbookId;
	public int CourseId;
	public String TextbookName;
	public String TextbookDetail;
	/**附件**/
	public ArrayList<String> Attachment = new ArrayList<String>();
	public Date UpdateTime;
	
	private TextbookHelper th =  TextbookHelper.getHelper();
	
	/**新增公告or提取單個公告or更新公告**/
//	public Textbook(int TextbookId, String TextbookdName, int CourseId, Date UpdateTime) {
//		/**this.CourseId=CourseId;**/
//		this.TextbookName = TextbookName;
//		this.CourseId = CourseId;
//		this.UpdateTime = UpdateTime;
//	}
	
	
	/**提取所有公告**/
	public Textbook(int TextbookId, String TextbookName, String TextbookDetail, int CourseId, Date UpdateTime) {
		/**this.CourseId=CourseId;**/
		this.TextbookId = TextbookId;
		this.TextbookName = TextbookName;
		this.TextbookDetail =TextbookDetail;
		this.CourseId = CourseId;
		this.UpdateTime = UpdateTime;
	}
	

	
	public Textbook(int TextbookId, String TextbookName, ArrayList<String> Attachment, Date UpdateTime) {
		this.TextbookId = TextbookId;
		this.TextbookName = TextbookName;
		this.Attachment = Attachment;
		this.UpdateTime = UpdateTime;		
	}
	public int getTextbookId() {
		return this.TextbookId;
		
	}
	
	public int getCourseId() {
		return this.CourseId;
	}
	
	public String getTextbookName() {
		return this.TextbookName;
	}
	
	public String getTextbookDetail() {
		return this.TextbookDetail;
	}
	
	public ArrayList<String> getAttachment() {
		return this.Attachment;
	}
	
	public Date getUpdateTime() {
		return this.UpdateTime;
	}
	
	
	/**提取所有公告**/
    public JSONObject getAllData() {
        JSONObject jso = new JSONObject();
        jso.put("TextbookId", getTextbookId());
        jso.put("TextbookName", getTextbookName());        
        jso.put("UpdateTime", getUpdateTime());

        return jso;
    }
    
    /**提取單個公告**/
    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        
        jso.put("TextbookId", getTextbookId());
        jso.put("TextbookName", getTextbookName());
        jso.put("Attachment", getAttachment());
//        jso.put("Attachment", getAttachment());
        jso.put("UpdateTime", getUpdateTime());
           
        return jso;
    }
    
    /**更新公告**/
    public JSONObject update() {
    	JSONObject data = new JSONObject();
    	data = th.update(this);
    	
    	return data;
    }
}
