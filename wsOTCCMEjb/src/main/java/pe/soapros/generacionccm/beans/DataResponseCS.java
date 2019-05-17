package pe.soapros.generacionccm.beans;

import java.io.Serializable;
import java.util.Arrays;

public class DataResponseCS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private ContentAddResponseCS[] result;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ContentAddResponseCS[] getResult() {
		return result;
	}

	public void setResult(ContentAddResponseCS[] result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "DataResponseCS [id=" + id + ", result=" + Arrays.toString(result) + "]";
	}

	
	
	
	
}
