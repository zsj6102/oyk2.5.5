package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class PinTuanDetail {

    @Expose
    private Status status;
    @Expose
    private PinTuanDetailData data;

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
    public PinTuanDetailData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(PinTuanDetailData data) {
        this.data = data;
    }

}
