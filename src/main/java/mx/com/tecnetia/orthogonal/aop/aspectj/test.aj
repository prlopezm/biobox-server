package mx.com.tecnetia.orthogonal.aop.aspectj;

import mx.com.tecnetia.orthogonal.api.rest.CatalogoArquitecturaRestController;

public aspect test {
    pointcut callCatalogoRest(CatalogoArquitecturaRestController catalogoArquitecturaRestController):
            call(ResponseEntity<List<ServicioRestDTO>> CatalogoRestController.getServiciosRest()) && target(catalogoRestController);

    before(CatalogoArquitecturaRestController catalogoArquitecturaRestController):callCatalogoRest(catalogoRestController){

        System.out.println("...Dentro de AspectJ...");
    }
}
