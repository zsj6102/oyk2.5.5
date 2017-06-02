package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class NineBanner {

    @Expose
    private Status status;
    @Expose
    private List<NineBannerDatum> data = new ArrayList<NineBannerDatum>();

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
    public List<NineBannerDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<NineBannerDatum> data) {
        this.data = data;
    }

}
