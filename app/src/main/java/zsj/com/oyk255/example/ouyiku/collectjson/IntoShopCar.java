package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;

public class IntoShopCar {

    @Expose
    private Status status;
    @Expose
    private IntoShopCarData data;

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
    public IntoShopCarData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(IntoShopCarData data) {
        this.data = data;
    }

}
