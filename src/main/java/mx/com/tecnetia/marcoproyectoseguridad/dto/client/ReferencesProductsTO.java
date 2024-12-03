
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para referencesProductsTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="referencesProductsTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://prontipagos.ws.com}dto">
 *       &lt;sequence>
 *         &lt;element name="alterUser" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="alteredDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="confirmation" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="createUser" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="productId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "referencesProductsTO", propOrder = {
    "alterUser",
    "alteredDate",
    "confirmation",
    "createDate",
    "createUser",
    "id",
    "productId",
    "reference",
    "regex",
    "type"
})
public class ReferencesProductsTO
    extends Dto
{

    protected Long alterUser;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar alteredDate;
    protected Long confirmation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    protected Long createUser;
    protected Long id;
    protected Long productId;
    protected String reference;
    protected String regex;
    protected String type;

    /**
     * Obtiene el valor de la propiedad alterUser.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAlterUser() {
        return alterUser;
    }

    /**
     * Define el valor de la propiedad alterUser.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAlterUser(Long value) {
        this.alterUser = value;
    }

    /**
     * Obtiene el valor de la propiedad alteredDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlteredDate() {
        return alteredDate;
    }

    /**
     * Define el valor de la propiedad alteredDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlteredDate(XMLGregorianCalendar value) {
        this.alteredDate = value;
    }

    /**
     * Obtiene el valor de la propiedad confirmation.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getConfirmation() {
        return confirmation;
    }

    /**
     * Define el valor de la propiedad confirmation.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setConfirmation(Long value) {
        this.confirmation = value;
    }

    /**
     * Obtiene el valor de la propiedad createDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Define el valor de la propiedad createDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Obtiene el valor de la propiedad createUser.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * Define el valor de la propiedad createUser.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreateUser(Long value) {
        this.createUser = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad productId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Define el valor de la propiedad productId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProductId(Long value) {
        this.productId = value;
    }

    /**
     * Obtiene el valor de la propiedad reference.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Define el valor de la propiedad reference.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Obtiene el valor de la propiedad regex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Define el valor de la propiedad regex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegex(String value) {
        this.regex = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
