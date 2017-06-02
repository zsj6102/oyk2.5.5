package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;

public class HotPictureDetail {

    @Expose
    private HotPictureDetailData data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public HotPictureDetailData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(HotPictureDetailData data) {
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
