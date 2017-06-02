package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;

public class HotPictureZan {

    @Expose
    private ZanData data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public ZanData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(ZanData data) {
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
