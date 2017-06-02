package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Notable {

    @Expose
    private Status status;
    @Expose
    private List<NotableDatum> data = new ArrayList<NotableDatum>();

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
    public List<NotableDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<NotableDatum> data) {
        this.data = data;
    }

}
