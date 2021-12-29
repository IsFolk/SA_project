package ncu.im3069.demo.app;

import org.json.*;
import java.util.Calendar;

/**
 * <p>
 * The Class Manager
 * Manager類別（class）具有管理者所需要之屬性與方法，並且儲存與管理員相關之商業判斷邏輯<br>
 * </p>
 * 
 */

public class Manager{
	
	// 管理者編號
	private int ManagerId;
	
	private String ManagerEmail = null;
	
	// 管理者姓名
	private String ManagerName = null;
	
	// 管理者密碼
	private String Password;
	
    // login_times，更新時間的分鐘數
    private int login_times;
	
    
    // mh，ManagerHelper之物件與Manager相關之資料庫方法（Sigleton） 
    private ManagerHelper mh =  ManagerHelper.getHelper();
    
    
    /**
         * 實例化（Instantiates）一個新的（new）Manager物件<br>
         * 採用多載（overload）方法進行，此建構子用於建立管理員資料時，產生一名新的管理員
     *
     * @param email 管理員電子信箱
     * @param password 管理員密碼
     * @param name 管理員姓名
     */
    public Manager(String email, String password, String name) {
        this.ManagerEmail = email;
        this.Password = password;
        this.ManagerName = name;
        update();
    }
    
    /**
         * 實例化（Instantiates）一個新的（new）Manager物件<br>
         * 採用多載（overload）方法進行，此建構子用於更新管理員資料時，產生一名管理員同時需要去資料庫檢索原有更新時間分鐘數
     * 
     * @param id 管理員編號
     * @param email 管理員電子信箱
     * @param password 管理員密碼
     * @param name 管理員姓名
     */
    public Manager(int id, String email, String password, String name) {
        this.ManagerId = id;
        this.ManagerEmail = email;
        this.Password = password;
        this.ManagerName = name;
        /** 取回原有資料庫內該名會員之更新時間分鐘數 */
        getLoginTimesStatus();
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
    public Manager(int id, String email, String password, String name, int login_times) {
        this.ManagerId = id;
        this.ManagerEmail = email;
        this.Password = password;
        this.ManagerName = name;
        this.login_times = login_times;
    }
    
    /**
          * 取管理員之編號
     *
     * @return the id 回傳管理員編號
     */
    public int getID() {
        return this.ManagerId;
    }
    
    /**
         * 取得管理員之電子郵件信箱
     *
     * @return the email 回傳管理員電子郵件信箱
     */
    public String getEmail() {
        return this.ManagerEmail;
    }
    
    /**
         * 取得管理員之姓名
     *
     * @return the name 回傳管理員姓名
     */
    public String getName() {
        return this.ManagerName;
    }

    /**
         * 取得管理員之密碼
     *
     * @return the password 回傳管理員密碼
     */
    public String getPassword() {
        return this.Password;
    }
    
    /**
         * 取得更新資料時間之分鐘數
     *
     * @return the login times 回傳更新資料時間之分鐘數
     */
    public int getLoginTimes() {
        return this.login_times;
    }
    
    /**
         * 更新管理員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        Calendar calendar = Calendar.getInstance();
        this.login_times = calendar.get(Calendar.MINUTE);
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.ManagerId != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            mh.updateLoginTimes(this);
            /** 透過ManagerHelper物件，更新目前之管理員資料置資料庫中 */
            data = mh.update(this);
        }
        
        return data;
    }
    
    /**
         * 取得該名管理員所有資料
     *
     * @return the data 取得該名管理員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("ManagerId", getID());
        jso.put("ManagerName", getName());
        jso.put("ManagerEmail", getEmail());
        jso.put("Password", getPassword());
        jso.put("login_times", getLoginTimes());
        
        return jso;
    }
    
    
    /**
         * 取得資料庫內之更新資料時間分鐘數
     *
     */
    private void getLoginTimesStatus() {
        /** 透過ManagerHelper物件，取得儲存於資料庫的更新時間分鐘數與會員組別 */
        JSONObject data = mh.getLoginTimesStatus(this);
        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
        this.login_times = data.getInt("login_times");
    }
    
}