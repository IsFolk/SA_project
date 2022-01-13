package ncu.im3069.demo.app;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

public class Board {
	
	public int BoardId;
	public int CourseId;
	public String BoardName;
	public String BoardDetails;
	/**附件**/
	public ArrayList<String> Attachment = new ArrayList<String>();
	public Date UpdateTime;
	public String Attachmentname;
	
	private BoardHelper bh =  BoardHelper.getHelper();
	private BoardUploadHelper buh = BoardUploadHelper.getHelper();
	
	/**新增公告or提取單個公告or更新公告**/
	public Board(int BoardId, String BoardName, String BoardDetails, Date UpdateTime) {
		this.BoardId = BoardId;
		/**this.CourseId=CourseId;**/
		this.BoardName = BoardName;
		this.BoardDetails = BoardDetails;
		this.UpdateTime = UpdateTime;
	}
	
	public Board(int BoardId, int CourseId, String BoardName, String BoardDetails, Date UpdateTime) {
		this.BoardId = BoardId;
		this.CourseId = CourseId;
		this.BoardDetails = BoardDetails;
		this.BoardName = BoardName;
		this.UpdateTime = UpdateTime;		
	}
	
	
	/**提取所有公告**/
	public Board(int BoardId, int CourseId, String BoardName, Date UpdateTime) {
		this.BoardId = BoardId;
		this.CourseId = CourseId;
		this.BoardName = BoardName;
		this.UpdateTime = UpdateTime;
	}
	
	public Board(int BoardId, int CourseId, String BoardName, String BoardDetails, ArrayList<String> Attachment, Date UpdateTime) {
		this.BoardId = BoardId;
		this.CourseId = CourseId;
		this.BoardName = BoardName;
		this.BoardDetails = BoardDetails;
		this.Attachment = Attachment;
		this.UpdateTime = UpdateTime;		
	}	

	
	public Board(int BoardId, String BoardName, String BoardDetails, ArrayList<String> Attachment, Date UpdateTime) {
		this.BoardId = BoardId;
		this.BoardName = BoardName;
		this.BoardDetails = BoardDetails;
		this.Attachment = Attachment;
		this.UpdateTime = UpdateTime;		
	}
	
	public Board(String BoardName, int CourseId, String BoardDetails, ArrayList<String> Attachment, Date UpdateTime) {
		this.BoardName = BoardName;
		this.CourseId = CourseId;
		this.BoardDetails = BoardDetails;
		this.Attachment = Attachment;
		this.UpdateTime = UpdateTime;
	}
	
	public Board(int BoardId, String Attachment) {
		this.BoardId = BoardId;
		this.Attachmentname = Attachment;
	}
	

	public String getAttachmentname() {
		return this.Attachmentname;
	}
	
	public int getBoardId() {
		return this.BoardId;
		
	}
	
	public int getCourseId() {
		return this.CourseId;
	}
	
	public String getBoardName() {
		return this.BoardName;
	}
	
	public String getBoardDetails() {
		return this.BoardDetails;
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
        jso.put("BoardId", getBoardId());
        jso.put("CourseId", getCourseId());
        jso.put("BoardName", getBoardName());        
        jso.put("UpdateTime", getUpdateTime());

        return jso;
    }
    
    
    /**提取單個公告**/
    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        
        jso.put("BoardId", getBoardId());
        jso.put("CourseId", getCourseId());
        jso.put("BoardName", getBoardName());
        jso.put("BoardDetails",getBoardDetails());
        jso.put("Attachment", getAttachment());
//        jso.put("Attachment", getAttachment());
        jso.put("UpdateTime", getUpdateTime());
           
        return jso;
    }
    
    /**更新公告**/
    public JSONObject update() {
    	JSONObject data = new JSONObject();
    	data = bh.update(this);
    	
    	return data;
    }
    
    public JSONObject upload() {
    	JSONObject data = new JSONObject();
    	data = buh.upload(this);
    	
    	return data;
    }
}
