package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class Respuesta implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Origen origen;
    String numOperacion;
    byte[] docBase64;
    //String docBase64;


    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

	public byte[] getDocBase64() {
		return docBase64;
	}

	public void setDocBase64(byte[] docBase64) {
		this.docBase64 = docBase64;
	}

   
}
