package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class PinTuanComfirm {

    @Expose
    private Status status;
    @Expose
    private PinTuanComfirmData data;

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
    public PinTuanComfirmData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(PinTuanComfirmData data) {
        this.data = data;
    }

}
