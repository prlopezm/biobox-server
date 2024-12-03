
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para transactionResponseDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="transactionResponseDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codeTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactionResponseDto", propOrder = {
    "codeDescription",
    "codeTransaction",
    "dateTransaction",
    "folioTransaction",
    "statusTransaction",
    "transactionId"
})
public class TransactionResponseDto {

    protected String codeDescription;
    protected String codeTransaction;
    protected String dateTransaction;
    protected String folioTransaction;
    protected String statusTransaction;
    protected String transactionId;

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

}
