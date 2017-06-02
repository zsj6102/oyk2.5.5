package zsj.com.oyk255.example.ouyiku.hotjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HotPicture {

    @Expose
    private List<PictureDatum> data = new ArrayList<PictureDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<PictureDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<PictureDatum> data) {
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
