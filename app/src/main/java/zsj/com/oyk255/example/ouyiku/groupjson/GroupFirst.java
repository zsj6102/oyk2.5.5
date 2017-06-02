package zsj.com.oyk255.example.ouyiku.groupjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class GroupFirst {

    @Expose
    private Status status;
    @Expose
    private List<Group1Datum> data = new ArrayList<Group1Datum>();

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
    public List<Group1Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Group1Datum> data) {
        this.data = data;
    }

}
