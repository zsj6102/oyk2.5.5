package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class DataNetshare {
    @Expose
    private Status status;
    @Expose
    private NetShare data;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public NetShare getNetShare() {
        return data;
    }

    public void setNetShare(NetShare netShare) {
        this.data = netShare;
    }


}
