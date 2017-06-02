package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class HomeTuijian {
	
	@Expose
    private Status status;
    @Expose
    private List<HomeTuijianData> data = new ArrayList<HomeTuijianData>();

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
    public List<HomeTuijianData> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HomeTuijianData> data) {
        this.data = data;
    }
}
