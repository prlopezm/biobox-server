package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.FotoProductoRecicladoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.FotoProductoRecicladoEntityRepository;

@Service
@RequiredArgsConstructor
public class ImagenesServiceImpl implements ImagenesService{

	private final FotoProductoRecicladoEntityRepository fotoProductoRecicladoEntityRepository;
	
	@Override
	@Transactional(readOnly = true)
	public FotoProductoRecicladoListDTO consultarFotosProductosReciclados(Collection<Long> ids) {
		FotoProductoRecicladoListDTO fotoListDTO = new FotoProductoRecicladoListDTO();
		fotoListDTO.setFotos(new ArrayList<FotoProductoRecicladoDTO>());	
		ArrayList<FotoProductoRecicladoEntity> list = fotoProductoRecicladoEntityRepository.findByIdFotoProductoRecicladoIsIn(ids);
				
		list.forEach(fotoEntity -> {
			FotoProductoRecicladoDTO fotoDTO = new FotoProductoRecicladoDTO();	
			fotoDTO.setIdFotoProductoReciclado(fotoEntity.getIdFotoProductoReciclado());
			fotoDTO.setIdProductoReciclado(fotoEntity.getIdProductoReciclado());
			fotoDTO.setFoto(fotoEntity.getFoto());
			fotoDTO.setExitoso(fotoEntity.getExitoso());		
			fotoListDTO.getFotos().add(fotoDTO);
		});
		
		return fotoListDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public FotoProductoRecicladoDTO consultarFotoProductoReciclado(Long id) {
		FotoProductoRecicladoDTO fotoDTO = null;
		Optional<FotoProductoRecicladoEntity> fotoEntity = fotoProductoRecicladoEntityRepository.findById(id);
		
		if(fotoEntity.isPresent()) {
			fotoDTO = new FotoProductoRecicladoDTO();
			fotoDTO.setIdFotoProductoReciclado(fotoEntity.get().getIdFotoProductoReciclado());
			fotoDTO.setIdProductoReciclado(fotoEntity.get().getIdProductoReciclado());
			fotoDTO.setFoto(fotoEntity.get().getFoto());
			fotoDTO.setExitoso(fotoEntity.get().getExitoso());
		}
		return fotoDTO;
	}
}
