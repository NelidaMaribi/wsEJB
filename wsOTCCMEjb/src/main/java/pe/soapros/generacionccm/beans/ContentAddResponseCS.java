package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class ContentAddResponseCS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ContentResponseCS content;

	public ContentResponseCS getContent() {
		return content;
	}

	public void setContent(ContentResponseCS content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ContentAddResponseCS [content=" + content + "]";
	}
	
	
	
}
