-------------Inicio
update fabricante
set nombre = 'RECICLADO_EXITOSO'
where nombre = 'NOT_FOUND';

update material
set nombre = 'RECICLADO_EXITOSO'
where nombre = 'NOT_FOUND';

update marca
set nombre = 'RECICLADO_EXITOSO'
where nombre = 'NOT_FOUND';

update sub_marca
set nombre = 'RECICLADO_EXITOSO'
where nombre = 'NOT_FOUND';

update producto_reciclable
set sku = 'RECICLADO_EXITOSO', bar_code='RECICLADO_EXITOSO'
where sku = 'NOT_FOUND';
-----------------------------Fin
