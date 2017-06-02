package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;

public class DetaiInfo {

    @Expose
    private DetaiInfoData data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public DetaiInfoData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(DetaiInfoData data) {
        this.data = data;
    }

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

}
