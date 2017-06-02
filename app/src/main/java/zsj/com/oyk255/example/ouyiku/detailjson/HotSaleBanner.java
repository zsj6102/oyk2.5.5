package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class HotSaleBanner {

    @Expose
    private List<HotSaleBannerDatum> data = new ArrayList<HotSaleBannerDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<HotSaleBannerDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HotSaleBannerDatum> data) {
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
