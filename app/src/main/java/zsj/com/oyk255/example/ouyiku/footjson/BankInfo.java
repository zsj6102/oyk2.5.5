package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;

public class BankInfo {

    @Expose
    private Status status;
    @Expose
    private BankInfoData data;

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The data
     */
    public BankInfoData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(BankInfoData data) {
        this.data = data;
    }

}
