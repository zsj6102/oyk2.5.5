package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserHBDatum {

    @SerializedName("bm_id")
    @Expose
    private String bmId;
    @Expose
    private String money;
    @SerializedName("user_time")
    @Expose
    private String userTime;
    @Expose
    private String name;
    @Expose
    private String condition;
    @Expose
    private String isuse;

    /**
     * 
     * @return
     *     The bmId
     */
    public String getBmId() {
        return bmId;
    }

    /**
     * 
     * @param bmId
     *     The bm_id
     */
    public void setBmId(String bmId) {
        this.bmId = bmId;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 
     * @return
     *     The userTime
     */
    public String getUserTime() {
        return userTime;
    }

    /**
     * 
     * @param userTime
     *     The user_time
     */
    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 
     * @param condition
     *     The condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 
     * @return
     *     The isuse
     */
    public String getIsuse() {
        return isuse;
    }

    /**
     * 
     * @param isuse
     *     The isuse
     */
    public void setIsuse(String isuse) {
        this.isuse = isuse;
    }

}
