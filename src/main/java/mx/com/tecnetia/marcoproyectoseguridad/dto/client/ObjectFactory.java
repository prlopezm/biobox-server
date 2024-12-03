
package mx.com.tecnetia.marcoproyectoseguridad.dto.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SellService_QNAME = new QName("http://prontipagos.ws.com", "sellService");
    private final static QName _CheckStatusService_QNAME = new QName("http://prontipagos.ws.com", "checkStatusService");
    private final static QName _CheckStatusServiceResponse_QNAME = new QName("http://prontipagos.ws.com", "checkStatusServiceResponse");
    private final static QName _BalanceService_QNAME = new QName("http://prontipagos.ws.com", "balanceService");
    private final static QName _BalanceServiceResponse_QNAME = new QName("http://prontipagos.ws.com", "balanceServiceResponse");
    private final static QName _SellServiceResponse_QNAME = new QName("http://prontipagos.ws.com", "sellServiceResponse");
    private final static QName _ObtainCatalogProducts_QNAME = new QName("http://prontipagos.ws.com", "obtainCatalogProducts");
    private final static QName _ObtainCatalogProductsResponse_QNAME = new QName("http://prontipagos.ws.com", "obtainCatalogProductsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckStatusService }
     * 
     */
    public CheckStatusService createCheckStatusService() {
        return new CheckStatusService();
    }

    /**
     * Create an instance of {@link CheckStatusServiceResponse }
     * 
     */
    public CheckStatusServiceResponse createCheckStatusServiceResponse() {
        return new CheckStatusServiceResponse();
    }

    /**
     * Create an instance of {@link SellService }
     * 
     */
    public SellService createSellService() {
        return new SellService();
    }

    /**
     * Create an instance of {@link BalanceServiceResponse }
     * 
     */
    public BalanceServiceResponse createBalanceServiceResponse() {
        return new BalanceServiceResponse();
    }

    /**
     * Create an instance of {@link BalanceService }
     * 
     */
    public BalanceService createBalanceService() {
        return new BalanceService();
    }

    /**
     * Create an instance of {@link ObtainCatalogProducts }
     * 
     */
    public ObtainCatalogProducts createObtainCatalogProducts() {
        return new ObtainCatalogProducts();
    }

    /**
     * Create an instance of {@link SellServiceResponse }
     * 
     */
    public SellServiceResponse createSellServiceResponse() {
        return new SellServiceResponse();
    }

    /**
     * Create an instance of {@link ObtainCatalogProductsResponse }
     * 
     */
    public ObtainCatalogProductsResponse createObtainCatalogProductsResponse() {
        return new ObtainCatalogProductsResponse();
    }

    /**
     * Create an instance of {@link ErrorResponseTO }
     * 
     */
    public ErrorResponseTO createErrorResponseTO() {
        return new ErrorResponseTO();
    }

    /**
     * Create an instance of {@link ResponseBalanceTO }
     * 
     */
    public ResponseBalanceTO createResponseBalanceTO() {
        return new ResponseBalanceTO();
    }

    /**
     * Create an instance of {@link ReferencesProductsTO }
     * 
     */
    public ReferencesProductsTO createReferencesProductsTO() {
        return new ReferencesProductsTO();
    }

    /**
     * Create an instance of {@link TransactionResponseTO }
     * 
     */
    public TransactionResponseTO createTransactionResponseTO() {
        return new TransactionResponseTO();
    }

    /**
     * Create an instance of {@link TransactionResponseDto }
     * 
     */
    public TransactionResponseDto createTransactionResponseDto() {
        return new TransactionResponseDto();
    }

    /**
     * Create an instance of {@link BalanceTO }
     * 
     */
    public BalanceTO createBalanceTO() {
        return new BalanceTO();
    }

    /**
     * Create an instance of {@link ResponseCatalogProductTO }
     * 
     */
    public ResponseCatalogProductTO createResponseCatalogProductTO() {
        return new ResponseCatalogProductTO();
    }

    /**
     * Create an instance of {@link ProductWsTO }
     * 
     */
    public ProductWsTO createProductWsTO() {
        return new ProductWsTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SellService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "sellService")
    public JAXBElement<SellService> createSellService(SellService value) {
        return new JAXBElement<SellService>(_SellService_QNAME, SellService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckStatusService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "checkStatusService")
    public JAXBElement<CheckStatusService> createCheckStatusService(CheckStatusService value) {
        return new JAXBElement<CheckStatusService>(_CheckStatusService_QNAME, CheckStatusService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckStatusServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "checkStatusServiceResponse")
    public JAXBElement<CheckStatusServiceResponse> createCheckStatusServiceResponse(CheckStatusServiceResponse value) {
        return new JAXBElement<CheckStatusServiceResponse>(_CheckStatusServiceResponse_QNAME, CheckStatusServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BalanceService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "balanceService")
    public JAXBElement<BalanceService> createBalanceService(BalanceService value) {
        return new JAXBElement<BalanceService>(_BalanceService_QNAME, BalanceService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BalanceServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "balanceServiceResponse")
    public JAXBElement<BalanceServiceResponse> createBalanceServiceResponse(BalanceServiceResponse value) {
        return new JAXBElement<BalanceServiceResponse>(_BalanceServiceResponse_QNAME, BalanceServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SellServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "sellServiceResponse")
    public JAXBElement<SellServiceResponse> createSellServiceResponse(SellServiceResponse value) {
        return new JAXBElement<SellServiceResponse>(_SellServiceResponse_QNAME, SellServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtainCatalogProducts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "obtainCatalogProducts")
    public JAXBElement<ObtainCatalogProducts> createObtainCatalogProducts(ObtainCatalogProducts value) {
        return new JAXBElement<ObtainCatalogProducts>(_ObtainCatalogProducts_QNAME, ObtainCatalogProducts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtainCatalogProductsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://prontipagos.ws.com", name = "obtainCatalogProductsResponse")
    public JAXBElement<ObtainCatalogProductsResponse> createObtainCatalogProductsResponse(ObtainCatalogProductsResponse value) {
        return new JAXBElement<ObtainCatalogProductsResponse>(_ObtainCatalogProductsResponse_QNAME, ObtainCatalogProductsResponse.class, null, value);
    }

}
