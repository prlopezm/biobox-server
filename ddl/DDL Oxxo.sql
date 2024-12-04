CREATE TABLE public.opcion_canje_oxxo
(
    id             serial4 NOT NULL,
    nombre         text    NOT NULL,
    puntos_canjear int4    NOT NULL,
    CONSTRAINT opcion_canje_oxxo_pk PRIMARY KEY (id)
);
CREATE TABLE public.canje_oxxo
(
    id                   bigserial               NOT NULL,
    momento              timestamp DEFAULT now() NOT NULL,
    arq_usuario_id       int8                    NOT NULL,
    exitoso              bool      DEFAULT true  NOT NULL,
    opcion_canje_oxxo_id int4                    NOT NULL,
    respuesta_oxxo       text                    NULL,
    CONSTRAINT canje_oxxo_pk PRIMARY KEY (id),
    CONSTRAINT canje_oxxo_opcion_canje_oxxo_fk FOREIGN KEY (opcion_canje_oxxo_id) REFERENCES public.opcion_canje_oxxo (id)
);
--Luego de crear las tablas, hay que hacer las inserciones de la tabla opcion_canje_oxxo
--Inserciones de propiedades de Oxxo:
insert into arq_propiedad(grupo_codigo, codigo, nombre, valor, descripcion, activo)
values ('OXXO', 'OXXO_LEYENDA', 'OXXO_LEYENDA', 'Esta es la leyenda de Oxxo', 'Esta es la leyenda de Oxxo', true),
       ('OXXO', 'OXXO_URL_VALIDA_USUARIO', 'OXXO_URL_VALIDA_USUARIO',
        'https://dev-spinplus.mioxxo-dev.io/api/v1/stores/partner/phone/validate/',
        'URL para validar el celular del usuario', true),
       ('OXXO', 'OXXO_URL_VALIDA_USUARIO_KEY', 'OXXO_URL_VALIDA_USUARIO_KEY',
        'RC7SNy1qac6Rj6hlFRV0g37xGDfu2ASEB71hMUHe',
        'header x-api-key', true),
       ('OXXO', 'OXXO_URL_VALIDA_USUARIO_PARTNER_ID', 'OXXO_URL_VALIDA_USUARIO_PARTNER_ID', 'biobox',
        'header partner-id', true);
--Valores NOT_FOUND para productos que no se encuentren en la BD:
insert into fabricante(nombre)
values('NOT_FOUND');
insert into marca(nombre)
values('NOT_FOUND');
insert into sub_marca(nombre, id_marca)
values('NOT_FOUND',111); --Buscar el ID del insert anterior.
insert into material( nombre)
values('NOT_FOUND');
