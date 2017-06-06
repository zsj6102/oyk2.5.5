package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;
/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class NetredDetail {
    @Expose
    private Status status;
    @Expose
    private DataNet data;

    public DataNet getData() {
        return data;
    }

    public void setData(DataNet data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

}
