package zsj.com.oyk255.example.ouyiku.personcenterjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class WuLiuList {

    @Expose
    private List<WuLiuListDatum> data = new ArrayList<WuLiuListDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<WuLiuListDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<WuLiuListDatum> data) {
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
