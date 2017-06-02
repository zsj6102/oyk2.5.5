package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class NotableDatum {

    @Expose
    private String uname;
    @Expose
    private String photo;

    /**
     * 
     * @return
     *     The uname
     */
    public String getUname() {
        return uname;
    }

    /**
     * 
     * @param uname
     *     The uname
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * 
     * @return
     *     The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 
     * @param photo
     *     The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
