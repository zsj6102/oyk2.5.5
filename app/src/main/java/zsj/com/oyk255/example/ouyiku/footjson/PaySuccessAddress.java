package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;

public class PaySuccessAddress {

    @Expose
    private Status status;
    @Expose
    private PaySuccessAddressData data;

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
    public PaySuccessAddressData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(PaySuccessAddressData data) {
        this.data = data;
    }

}
