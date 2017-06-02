package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class NewsTop {

    @Expose
    private List<NewsTopDatum> data = new ArrayList<NewsTopDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<NewsTopDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<NewsTopDatum> data) {
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
