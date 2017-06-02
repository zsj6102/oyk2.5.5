package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class ServiceInfo {

    @Expose
    private Status status;
    @Expose
    private ServiceInfoData data;

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
    public ServiceInfoData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(ServiceInfoData data) {
        this.data = data;
    }

}
