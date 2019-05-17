
package services.hpexstream.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for header complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="header">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DDAOutputFile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="defaultExtension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="outputLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PDL" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userDataLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "header", propOrder = {
    "ddaOutputFile",
    "defaultExtension",
    "fileType",
    "messageLength",
    "outputLength",
    "pdl",
    "pageCount",
    "returnCode",
    "userData",
    "userDataLength",
    "version"
})
public class Header {

    @XmlElement(name = "DDAOutputFile")
    protected boolean ddaOutputFile;
    protected String defaultExtension;
    protected String fileType;
    protected int messageLength;
    protected int outputLength;
    @XmlElement(name = "PDL")
    protected int pdl;
    protected int pageCount;
    protected int returnCode;
    protected String userData;
    protected int userDataLength;
    protected int version;

    /**
     * Gets the value of the ddaOutputFile property.
     * 
     */
    public boolean isDDAOutputFile() {
        return ddaOutputFile;
    }

    /**
     * Sets the value of the ddaOutputFile property.
     * 
     */
    public void setDDAOutputFile(boolean value) {
        this.ddaOutputFile = value;
    }

    /**
     * Gets the value of the defaultExtension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultExtension() {
        return defaultExtension;
    }

    /**
     * Sets the value of the defaultExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultExtension(String value) {
        this.defaultExtension = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileType(String value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the messageLength property.
     * 
     */
    public int getMessageLength() {
        return messageLength;
    }

    /**
     * Sets the value of the messageLength property.
     * 
     */
    public void setMessageLength(int value) {
        this.messageLength = value;
    }

    /**
     * Gets the value of the outputLength property.
     * 
     */
    public int getOutputLength() {
        return outputLength;
    }

    /**
     * Sets the value of the outputLength property.
     * 
     */
    public void setOutputLength(int value) {
        this.outputLength = value;
    }

    /**
     * Gets the value of the pdl property.
     * 
     */
    public int getPDL() {
        return pdl;
    }

    /**
     * Sets the value of the pdl property.
     * 
     */
    public void setPDL(int value) {
        this.pdl = value;
    }

    /**
     * Gets the value of the pageCount property.
     * 
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets the value of the pageCount property.
     * 
     */
    public void setPageCount(int value) {
        this.pageCount = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     */
    public void setReturnCode(int value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the userData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserData() {
        return userData;
    }

    /**
     * Sets the value of the userData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserData(String value) {
        this.userData = value;
    }

    /**
     * Gets the value of the userDataLength property.
     * 
     */
    public int getUserDataLength() {
        return userDataLength;
    }

    /**
     * Sets the value of the userDataLength property.
     * 
     */
    public void setUserDataLength(int value) {
        this.userDataLength = value;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

}
