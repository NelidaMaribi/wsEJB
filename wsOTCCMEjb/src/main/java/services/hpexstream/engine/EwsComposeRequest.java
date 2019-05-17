
package services.hpexstream.engine;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ewsComposeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ewsComposeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="driver" type="{urn:hpexstream-services/Engine}driverFile" minOccurs="0"/>
 *         &lt;element name="driverEncoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="engineOptions" type="{urn:hpexstream-services/Engine}engineOption" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fileReturnRegEx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="includeHeader" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="includeMessageFile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="outputFile" type="{urn:hpexstream-services/Engine}output" minOccurs="0"/>
 *         &lt;element name="pubFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubFiles" type="{urn:hpexstream-services/Engine}pubFiles" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ewsComposeRequest", propOrder = {
    "driver",
    "driverEncoding",
    "engineOptions",
    "fileReturnRegEx",
    "includeHeader",
    "includeMessageFile",
    "outputFile",
    "pubFile",
    "pubFiles"
})
public class EwsComposeRequest {

    protected DriverFile driver;
    protected String driverEncoding;
    @XmlElement(nillable = true)
    protected List<EngineOption> engineOptions;
    protected String fileReturnRegEx;
    protected boolean includeHeader;
    protected boolean includeMessageFile;
    protected Output outputFile;
    protected String pubFile;
    protected PubFiles pubFiles;

    /**
     * Gets the value of the driver property.
     * 
     * @return
     *     possible object is
     *     {@link DriverFile }
     *     
     */
    public DriverFile getDriver() {
        return driver;
    }

    /**
     * Sets the value of the driver property.
     * 
     * @param value
     *     allowed object is
     *     {@link DriverFile }
     *     
     */
    public void setDriver(DriverFile value) {
        this.driver = value;
    }

    /**
     * Gets the value of the driverEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverEncoding() {
        return driverEncoding;
    }

    /**
     * Sets the value of the driverEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverEncoding(String value) {
        this.driverEncoding = value;
    }

    /**
     * Gets the value of the engineOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the engineOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEngineOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EngineOption }
     * 
     * 
     */
    public List<EngineOption> getEngineOptions() {
        if (engineOptions == null) {
            engineOptions = new ArrayList<EngineOption>();
        }
        return this.engineOptions;
    }

    /**
     * Gets the value of the fileReturnRegEx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileReturnRegEx() {
        return fileReturnRegEx;
    }

    /**
     * Sets the value of the fileReturnRegEx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileReturnRegEx(String value) {
        this.fileReturnRegEx = value;
    }

    /**
     * Gets the value of the includeHeader property.
     * 
     */
    public boolean isIncludeHeader() {
        return includeHeader;
    }

    /**
     * Sets the value of the includeHeader property.
     * 
     */
    public void setIncludeHeader(boolean value) {
        this.includeHeader = value;
    }

    /**
     * Gets the value of the includeMessageFile property.
     * 
     */
    public boolean isIncludeMessageFile() {
        return includeMessageFile;
    }

    /**
     * Sets the value of the includeMessageFile property.
     * 
     */
    public void setIncludeMessageFile(boolean value) {
        this.includeMessageFile = value;
    }

    /**
     * Gets the value of the outputFile property.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getOutputFile() {
        return outputFile;
    }

    /**
     * Sets the value of the outputFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setOutputFile(Output value) {
        this.outputFile = value;
    }

    /**
     * Gets the value of the pubFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubFile() {
        return pubFile;
    }

    /**
     * Sets the value of the pubFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubFile(String value) {
        this.pubFile = value;
    }

    /**
     * Gets the value of the pubFiles property.
     * 
     * @return
     *     possible object is
     *     {@link PubFiles }
     *     
     */
    public PubFiles getPubFiles() {
        return pubFiles;
    }

    /**
     * Sets the value of the pubFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link PubFiles }
     *     
     */
    public void setPubFiles(PubFiles value) {
        this.pubFiles = value;
    }

}
