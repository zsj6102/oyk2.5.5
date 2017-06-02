package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class PinTuanBanner {

    @Expose
    private PinTuanBannerData data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public PinTuanBannerData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(PinTuanBannerData data) {
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
