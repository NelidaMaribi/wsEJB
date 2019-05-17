package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class ContentResponseCS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte[] data;
	
	private String contentType;
	
	private String streamingFilePath;
	
	private boolean empty;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getStreamingFilePath() {
		return streamingFilePath;
	}

	public void setStreamingFilePath(String streamingFilePath) {
		this.streamingFilePath = streamingFilePath;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return "ContentResponseCS [data=" + data + ", contentType=" + contentType + ", streamingFilePath="
				+ streamingFilePath + ", empty=" + empty + "]";
	}
	
	
	
}
