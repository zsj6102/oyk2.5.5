package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class UserHB {

    @Expose
    private Status status;
    @Expose
    private List<UserHBDatum> data = new ArrayList<UserHBDatum>();

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
    public List<UserHBDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<UserHBDatum> data) {
        this.data = data;
    }

}
