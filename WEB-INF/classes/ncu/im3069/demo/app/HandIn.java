package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import org.json.*;


public class HandIn {

	private int id; //題號
	
	private String handInAns1;
	
	private String handInAns2;
	
	private String handInAns3;
	
	private String handInAns4;
	
	private int gotPoint;
	
	
	private HandInHelper hih = HandInHelper.getHelper();
	
	

	/** 新增答題 **/
	public HandIn(String handInAns1, String handInAns2, String handInAns3, String handInAns4) {
	
		this.handInAns1 = handInAns1;
		this.handInAns2 = handInAns2;
		this.handInAns3 = handInAns3;
		this.handInAns4 = handInAns4;
		//update();
	}
	

	/** 更新作答 **/
	public HandIn(int id, String handInAns1, String handInAns2, String handInAns3, String handInAns4) {
		
		this.id = id;
		this.handInAns1 = handInAns1;
		this.handInAns2 = handInAns2;
		this.handInAns3 = handInAns3;
		this.handInAns4 = handInAns4;
		update();

	}
	
	/** 提取作答 **/
	public HandIn(int id, String handInAns1, String handInAns2, String handInAns3, String handInAns4, int gotPoint) {
		
		this.id = id;
		this.handInAns1 = handInAns1;
		this.handInAns2 = handInAns2;
		this.handInAns3 = handInAns3;
		this.handInAns4 = handInAns4;
		this.gotPoint = gotPoint;

	}
	
	
	
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = hih.update(this);
        }        
        
        return data;
    }	
	

	
	
	public void setGotPoint(int gotPoint) {
		this.gotPoint = gotPoint;
	}

	public int getId() {
		return this.id;
	}
	
	
	public String getHandInAnswer1() {
		return this.handInAns1;
	}
	
	public String getHandInAnswer2() {
		return this.handInAns2;
	}
	
	public String getHandInAnswer3() {
		return this.handInAns3;
	}
	
	public String getHandInAnswer4() {
		return this.handInAns4;
	}
	
	public int getGotPoint() {
		return this.gotPoint;
	}
	
	
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("handInAns1", getHandInAnswer1());
        data.put("handInAns2", getHandInAnswer2());
        data.put("handInAns3", getHandInAnswer3());
        data.put("handInAns4", getHandInAnswer4());
        data.put("gotPoint", getGotPoint());

        return data;
    }	
	

	
	
}
