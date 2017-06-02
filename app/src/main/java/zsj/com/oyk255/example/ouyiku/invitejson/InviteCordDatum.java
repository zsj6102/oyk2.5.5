package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteCordDatum {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @Expose
    private String money;

    /**
     * 
     * @return
     *     The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     *     The createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return
     *     The gmoney
     */
    public String getGmoney() {
        return money;
    }

    /**
     * 
     * @param gmoney
     *     The gmoney
     */
    public void setGmoney(String money) {
        this.money = money;
    }

}
