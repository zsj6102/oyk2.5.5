package zsj.com.oyk255.example.ouyiku.groupjson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SecondData {
	@Expose
    private String m_name;
    @Expose
    private List<SecondData2> sscat = new ArrayList<SecondData2>();
    
    
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public List<SecondData2> getSscat() {
		return sscat;
	}
	public void setSscat(List<SecondData2> sscat) {
		this.sscat = sscat;
	}

   
}
