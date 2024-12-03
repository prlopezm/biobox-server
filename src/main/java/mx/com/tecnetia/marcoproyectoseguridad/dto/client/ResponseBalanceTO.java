
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para responseBalanceTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="responseBalanceTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="balances" type="{http://prontipagos.ws.com}balanceTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errorResponseTO" type="{http://prontipagos.ws.com}errorResponseTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseBalanceTO", propOrder = {
    "balances",
    "errorResponseTO"
})
public class ResponseBalanceTO {

    @XmlElement(nillable = true)
    protected List<BalanceTO> balances;
    protected ErrorResponseTO errorResponseTO;

    /**
     * Gets the value of the balances property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the balances property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBalances().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BalanceTO }
     * 
     * 
     */
    public List<BalanceTO> getBalances() {
        if (balances == null) {
            balances = new ArrayList<BalanceTO>();
        }
        return this.balances;
    }

    /**
     * Obtiene el valor de la propiedad errorResponseTO.
     * 
     * @return
     *     possible object is
     *     {@link ErrorResponseTO }
     *     
     */
    public ErrorResponseTO getErrorResponseTO() {
        return errorResponseTO;
    }

    /**
     * Define el valor de la propiedad errorResponseTO.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorResponseTO }
     *     
     */
    public void setErrorResponseTO(ErrorResponseTO value) {
        this.errorResponseTO = value;
    }

}
