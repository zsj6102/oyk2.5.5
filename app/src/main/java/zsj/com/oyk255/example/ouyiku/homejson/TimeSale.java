package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class TimeSale {

    @Expose
    private Status status;
    @Expose
    private TimeSaleData data;

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
    public TimeSaleData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(TimeSaleData data) {
        this.data = data;
    }

}
