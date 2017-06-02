package zsj.com.oyk255.example.ouyiku.personcenterjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class WuLiuDetail {

    @Expose
    private String message;
    @Expose
    private String nu;
    @Expose
    private String ischeck;
    @Expose
    private String condition;
    @Expose
    private String com;
    @Expose
    private String status;
    @Expose
    private String state;
    @Expose
    private List<WuLiuDetailDatum> data = new ArrayList<WuLiuDetailDatum>();

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The nu
     */
    public String getNu() {
        return nu;
    }

    /**
     * 
     * @param nu
     *     The nu
     */
    public void setNu(String nu) {
        this.nu = nu;
    }

    /**
     * 
     * @return
     *     The ischeck
     */
    public String getIscheck() {
        return ischeck;
    }

    /**
     * 
     * @param ischeck
     *     The ischeck
     */
    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
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
     *     The com
     */
    public String getCom() {
        return com;
    }

    /**
     * 
     * @param com
     *     The com
     */
    public void setCom(String com) {
        this.com = com;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<WuLiuDetailDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<WuLiuDetailDatum> data) {
        this.data = data;
    }

}
