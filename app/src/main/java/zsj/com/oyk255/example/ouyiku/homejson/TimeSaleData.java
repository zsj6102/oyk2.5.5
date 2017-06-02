package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class TimeSaleData {

    @Expose
    private List<Re> res = new ArrayList<Re>();
    @Expose
    private String etime;
    @Expose
    private String img;

    /**
     * 
     * @return
     *     The res
     */
    public List<Re> getRes() {
        return res;
    }

    /**
     * 
     * @param res
     *     The res
     */
    public void setRes(List<Re> res) {
        this.res = res;
    }

    /**
     * 
     * @return
     *     The etime
     */
    public String getEtime() {
        return etime;
    }

    /**
     * 
     * @param etime
     *     The etime
     */
    public void setEtime(String etime) {
        this.etime = etime;
    }

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

}
