package zsj.com.oyk255.example.ouyiku.groupjson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SecondGroup {
	
	@Expose
    private Status status;
    @Expose
    private List<SecondData> data = new ArrayList<SecondData>();

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
    public List<SecondData> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<SecondData> data) {
        this.data = data;
    }
	
}