
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtainCatalogProductsResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtainCatalogProductsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://prontipagos.ws.com}responseCatalogProductTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtainCatalogProductsResponse", propOrder = {
    "_return"
})
public class ObtainCatalogProductsResponse {

    @XmlElement(name = "return")
    protected ResponseCatalogProductTO _return;

    /**
     * Obtiene el valor de la propiedad return.
     * 
     * @return
     *     possible object is
     *     {@link ResponseCatalogProductTO }
     *     
     */
    public ResponseCatalogProductTO getReturn() {
        return _return;
    }

    /**
     * Define el valor de la propiedad return.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseCatalogProductTO }
     *     
     */
    public void setReturn(ResponseCatalogProductTO value) {
        this._return = value;
    }

}
