
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para transactionResponseTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="transactionResponseTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://prontipagos.ws.com}dto">
 *       &lt;sequence>
 *         &lt;element name="codeTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionStatusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactionResponseTO", propOrder = {
    "codeTransaction",
    "statusTransaction",
    "codeDescription",
    "dateTransaction",
    "transactionId",
    "folioTransaction",
    "transactionStatusDescription",
    "additionalInfo"
})
public class TransactionResponseTO
    extends Dto
{

    protected String codeTransaction;
    protected String statusTransaction;
    protected String codeDescription;
    protected String dateTransaction;
    protected String transactionId;
    protected String folioTransaction;
    protected String transactionStatusDescription;
    protected String additionalInfo;

    /**
     * Obtiene el valor de la propiedad codeTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeTransaction() {
        return codeTransaction;
    }

    /**
     * Define el valor de la propiedad codeTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeTransaction(String value) {
        this.codeTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad statusTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusTransaction() {
        return statusTransaction;
    }

    /**
     * Define el valor de la propiedad statusTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusTransaction(String value) {
        this.statusTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad codeDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeDescription() {
        return codeDescription;
    }

    /**
     * Define el valor de la propiedad codeDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeDescription(String value) {
        this.codeDescription = value;
    }

    /**
     * Obtiene el valor de la propiedad dateTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTransaction() {
        return dateTransaction;
    }

    /**
     * Define el valor de la propiedad dateTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTransaction(String value) {
        this.dateTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad transactionId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Define el valor de la propiedad transactionId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Obtiene el valor de la propiedad folioTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioTransaction() {
        return folioTransaction;
    }

    /**
     * Define el valor de la propiedad folioTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioTransaction(String value) {
        this.folioTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad transactionStatusDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    /**
     * Define el valor de la propiedad transactionStatusDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatusDescription(String value) {
        this.transactionStatusDescription = value;
    }

    /**
     * Obtiene el valor de la propiedad additionalInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Define el valor de la propiedad additionalInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

}
