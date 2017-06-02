package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;

public class SubmitOrder {

    @Expose
    private Status status;
    @Expose
    private SubmitOrderData data;

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
    public SubmitOrderData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(SubmitOrderData data) {
        this.data = data;
    }

}
