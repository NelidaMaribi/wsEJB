package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class ResponseFlujoCS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	
	private DataResponseCS data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DataResponseCS getData() {
		return data;
	}

	public void setData(DataResponseCS data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseFlujoCS [status=" + status + ", data=" + data + "]";
	}
	
	

}
