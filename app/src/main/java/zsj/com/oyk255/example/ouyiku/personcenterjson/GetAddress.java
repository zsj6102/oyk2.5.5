package zsj.com.oyk255.example.ouyiku.personcenterjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class GetAddress {

    @Expose
    private Status status;
    @Expose
    private List<GetAddressDatum> data = new ArrayList<GetAddressDatum>();

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
    public List<GetAddressDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<GetAddressDatum> data) {
        this.data = data;
    }

}
