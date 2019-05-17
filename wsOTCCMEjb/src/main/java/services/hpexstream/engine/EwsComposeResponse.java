
package services.hpexstream.engine;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ewsComposeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ewsComposeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="engineMessage" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="files" type="{urn:hpexstream-services/Engine}engineOutput" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="statusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ewsComposeResponse", propOrder = {
    "engineMessage",
    "files",
    "statusMessage"
})
public class EwsComposeResponse {

    protected byte[] engineMessage;
    @XmlElement(nillable = true)
    protected List<EngineOutput> files;
    protected String statusMessage;

    /**
     * Gets the value of the engineMessage property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEngineMessage() {
        return engineMessage;
    }

    /**
     * Sets the value of the engineMessage property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEngineMessage(byte[] value) {
        this.engineMessage = ((byte[]) value);
    }

    /**
     * Gets the value of the files property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the files property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EngineOutput }
     * 
     * 
     */
    public List<EngineOutput> getFiles() {
        if (files == null) {
            files = new ArrayList<EngineOutput>();
        }
        return this.files;
    }

    /**
     * Gets the value of the statusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusMessage(String value) {
        this.statusMessage = value;
    }

}
