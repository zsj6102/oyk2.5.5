package zsj.com.oyk255.example.ouyiku.hotjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HotPictureComment {

    @Expose
    private List<HotPictureCommentDatum> data = new ArrayList<HotPictureCommentDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<HotPictureCommentDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HotPictureCommentDatum> data) {
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
