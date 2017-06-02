package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class DetaiBanner {

    @Expose
    private List<String> data = new ArrayList<String>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<String> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<String> data) {
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
