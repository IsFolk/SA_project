package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import org.json.*;


public class Question {

	private int id; //題號
	
	private String text; //題目
	
	private String attachment; //可能有圖片
	
	private String optionA;
	
	private String optionB;
	
	private String optionC;
	
	private String optionD;
	
	private String answerA;
	
	private String answerB;
	
	private String answerC;
	
	private String answerD;
	
	private int givePoint;
	
	private QuestionHelper qsh = QuestionHelper.getHelper();
	
	
	/** 建立一道題目 **/
	public Question(int id) {
		this.id = id;
	}
	
	/** 編輯or更新完整ㄉ題目 **/
	public Question(String text, String attachment, String optionA, String optionB, String optionC, String optionD,
			String answerA, String answerB, String answerC, String answerD, int givePoint) {
		
		this.text = text;
		this.attachment = attachment; 
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.givePoint = givePoint;
		update();
	}
	

	public Question(int id, String text, String attachment, String optionA, String optionB, String optionC, String optionD,
			String answerA, String answerB, String answerC, String answerD, int givePoint) {
		
		this.id = id;
		this.text = text;
		this.attachment = attachment; 
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.givePoint = givePoint;
		update();
	}
	
	
	
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = qsh.update(this);
        }        
        
        return data;
    }	
	

	
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	

	public String getText() {
		return this.text;
	}
	
	public String getAttachment() {
		return this.attachment;
	}
	
	public String getOptionA() {
		return this.optionA;
	}
	
	public String getOptionB() {
		return this.optionB;
	}

	public String getOptionC() {
		return this.optionC;
	}
	
	public String getOptionD() {
		return this.optionD;
	}
	
	public String getAnswerA() {
		return this.answerA;
	}
	
	public String getAnswerB() {
		return this.answerB;
	}
	
	public String getAnswerC() {
		return this.answerC;
	}
	
	public String getAnswerD() {
		return this.answerD;
	}
	
	public int getGivePoint() {
		return this.givePoint;
	}
	
	
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("text", getText());
        data.put("attachment", getAttachment());
        data.put("optionA", getOptionA());
        data.put("optionB", getOptionB());
        data.put("optionC", getOptionC());
        data.put("optionD", getOptionD());
        data.put("answerA", getAnswerA());
        data.put("answerB", getAnswerB());
        data.put("answerC", getAnswerC());
        data.put("answerD", getAnswerD());
        data.put("givePoint", getGivePoint());

        return data;
    }	
	
	
	
	
	
	
}
