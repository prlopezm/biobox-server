package mx.com.tecnetia.marcoproyectoseguridad.dto.client;


import javax.xml.ws.BindingProvider;

public class TestWS {
    public static void main(String[] args) {
        // set-up
        ProntipagosTopUpServiceEndPoint_Service service = new ProntipagosTopUpServiceEndPoint_Service();
        ProntipagosTopUpServiceEndPoint port = service.getProntipagosTopUpServiceEndPointPort();
        BindingProvider prov = (BindingProvider) port;
        //prov.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "pruebasPronti@pagos.com");
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "ProntiP30%");
        // sample calls
        ResponseCatalogProductTO response = port.obtainCatalogProducts();
        System.out.println(response.getErrorResponseTO().getErrorCode());
        for(ProductWsTO producto : response.getProducts()){
            System.out.println(producto.getSku());
            System.out.println(producto.getProductName());
            System.out.println(producto.getPrice());
        }
    }
}
