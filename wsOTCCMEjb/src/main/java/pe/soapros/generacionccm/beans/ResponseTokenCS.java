package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class ResponseTokenCS implements Serializable {

	private String token;
	private String userId;
	private String ticket;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "ResponseTokenCS [token=" + token + ", userId=" + userId + ", ticket=" + ticket + "]";
	}

	
	
	
}
