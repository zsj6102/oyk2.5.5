package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HomeBrandTuiJia {

    @Expose
    private List<HomeBrandTuiJiaDatum> data = new ArrayList<HomeBrandTuiJiaDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<HomeBrandTuiJiaDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HomeBrandTuiJiaDatum> data) {
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
