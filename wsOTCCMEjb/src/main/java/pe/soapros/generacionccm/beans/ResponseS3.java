package pe.soapros.generacionccm.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseS3 {

	@JsonProperty("ETag")
	private String ETag;
	
	@JsonProperty("Location")
	private String Location;
	
	private String Key;
	
	@JsonProperty("Bucket")
	private String Bucket;
	
	public String getETag() {
		return ETag;
	}
	public void setETag(String eTag) {
		ETag = eTag;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	
	public String getBucket() {
		return Bucket;
	}
	public void setBucket(String bucket) {
		Bucket = bucket;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	
	@Override
	public String toString() {
		return "ResponseS3 [ETag=" + ETag + ", Location=" + Location + ", Key=" + Key + ", Bucket=" + Bucket + "]";
	}
	
	
	
}
