package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;

public class SubmitOrderData {

    @Expose
    private String oids;
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The oids
     */
    
    
    public String getOids() {
        return oids;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * 
     * @param oids
     *     The oids
     */
    public void setOids(String oids) {
        this.oids = oids;
    }

}
