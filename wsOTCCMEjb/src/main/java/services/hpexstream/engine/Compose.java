
package services.hpexstream.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Compose complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Compose">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EWSComposeRequest" type="{urn:hpexstream-services/Engine}ewsComposeRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Compose", propOrder = {
    "ewsComposeRequest"
})
public class Compose {

    @XmlElement(name = "EWSComposeRequest")
    protected EwsComposeRequest ewsComposeRequest;

    /**
     * Gets the value of the ewsComposeRequest property.
     * 
     * @return
     *     possible object is
     *     {@link EwsComposeRequest }
     *     
     */
    public EwsComposeRequest getEWSComposeRequest() {
        return ewsComposeRequest;
    }

    /**
     * Sets the value of the ewsComposeRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link EwsComposeRequest }
     *     
     */
    public void setEWSComposeRequest(EwsComposeRequest value) {
        this.ewsComposeRequest = value;
    }

}
