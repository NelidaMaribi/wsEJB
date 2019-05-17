
package services.hpexstream.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for engineOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="engineOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileHeader" type="{urn:hpexstream-services/Engine}header" minOccurs="0"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileOutput" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "engineOutput", propOrder = {
    "fileHeader",
    "fileName",
    "fileOutput"
})
public class EngineOutput {

    protected Header fileHeader;
    protected String fileName;
    protected byte[] fileOutput;

    /**
     * Gets the value of the fileHeader property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getFileHeader() {
        return fileHeader;
    }

    /**
     * Sets the value of the fileHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setFileHeader(Header value) {
        this.fileHeader = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the fileOutput property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFileOutput() {
        return fileOutput;
    }

    /**
     * Sets the value of the fileOutput property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFileOutput(byte[] value) {
        this.fileOutput = ((byte[]) value);
    }

}
