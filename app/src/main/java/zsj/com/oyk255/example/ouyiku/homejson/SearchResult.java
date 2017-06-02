package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class SearchResult {

    @Expose
    private List<SearchResultDatum> data = new ArrayList<SearchResultDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<SearchResultDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<SearchResultDatum> data) {
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
