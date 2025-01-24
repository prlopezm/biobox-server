alter table arq_usuario
    add email_validado bool NOT NULL DEFAULT false;
alter table arq_usuario
    add telefono_validado bool NOT NULL DEFAULT false;
alter table arq_usuario
    add registro_concluido bool NOT NULL DEFAULT false;
update arq_usuario au
set registro_concluido = true;

update arq_usuario
set telefono = ''
where telefono in (select telefono
                   from arq_usuario
                   group by telefono
                   having count(1) > 1);

update programa_codigos_descuento
set imagen = REPLACE(imagen, 'https://tecnetiadev.com/pics', '');
-------Fin DDL Kike


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
    respuesta_oxxo       text                    NOT NULL,
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
       ('OXXO', 'OXXO_PARAM_XAPIKEY', 'OXXO_PARAM_XAPIKEY',
        '46e9ccff-6dfb-4f9e-919c-f2f96d2d2a4c',
        'header x-api-key', true),
       ('OXXO', 'OXXO_CANJES_MENSUALES', 'OXXO_CANJES_MENSUALES', '100',
        'Cantidad máxima de puntos canjeables por mes', true),
       ('OXXO', 'OXXO_PARAM_PARTNER_ID', 'OXXO_PARAM_PARTNER_ID', '6',
        'header partner-promoId', true),
       ('OXXO', 'OXXO_LIMITE_EXCEDIDO_ERROR', 'OXXO_LIMITE_EXCEDIDO_ERROR',
        'Has alcanzado el límite mensual de los beneficios Spin. Te invitamos a volver a partir del 1er día del mes siguiente para seguir disfrutando de estos beneficios',
        'Mensaje de error al llegar al máximo de canjes mensuales', true);
--Valores NOT_FOUND para productos que no se encuentren en la BD:
insert into fabricante(nombre)
values ('NOT_FOUND');
insert into marca(nombre)
values ('NOT_FOUND');
insert into sub_marca(nombre, id_marca)
values ('NOT_FOUND', 111); --Buscar el ID del insert anterior.
insert into material(nombre)
values ('NOT_FOUND');
--------
CREATE TABLE public.oxxo_member_id
(
    id             bigserial   NOT NULL,
    arq_usuario_id int8        NOT NULL,
    member_id      varchar(20) NOT NULL,
    CONSTRAINT oxxo_member_id_pk PRIMARY KEY (id),
    CONSTRAINT oxxo_member_id_unique UNIQUE (arq_usuario_id)
);

----
insert into arq_propiedad(grupo_codigo, codigo, nombre, valor, descripcion, activo)
values ('OXXO', 'OXXO_URL_CANJE', 'OXXO_URL_CANJE',
        'https://challenger-testing.mioxxo-dev.io/api/v1/campaign/partner/accrual/points', 'URL para canjear puntos',
        true);

ALTER TABLE public.usuario_puntos_color
    ADD CONSTRAINT usuario_puntos_color_unique UNIQUE (id_arq_usuario);

insert into arq_propiedad(grupo_codigo, codigo, nombre, valor, descripcion, activo)
values ('TWILIO', 'SMS.TWILIO.ACCOUNT-SID', 'SMS.TWILIO.ACCOUNT-SID', 'ACa9909191727d20f59834b91b513f5580',
        'Cuenta de Twilio', true),
       ('TWILIO', 'SMS.TWILIO.AUTH-TOKEN', 'SMS.TWILIO.AUTH-TOKEN', '6a34f172d3262b1f35ebf844dc066371',
        'Token de Twilio', true),
       ('TWILIO', 'SMS.TWILIO.FROM-NUMBER', 'SMS.TWILIO.FROM-NUMBER', '+15202638223',
        'Teléfono emisor de Twilio', true);

ALTER TABLE public.arq_usuario
    ADD nuevo_ingreso boolean DEFAULT false NOT NULL;
COMMENT ON COLUMN public.arq_usuario.nuevo_ingreso IS 'Usuarios que recién se crean y aún no han validado su email mediante la liga que se les anvía. No pueden firmarse en la aplicacion';

-------DDL Cuponerapp
CREATE TABLE public.cuponerapp
(
    id           serial4 NOT NULL,
    id_promocion int4    NOT NULL,
    promocion    text    NOT NULL,
    id_marca     int4    NOT NULL,
    puntos       int4    NOT NULL,
    CONSTRAINT cuponerapp_pk PRIMARY KEY (id),
    CONSTRAINT cuponerapp_unique UNIQUE (id_promocion)
);
COMMENT ON TABLE public.cuponerapp IS 'campos del proyecto de integración con CuponerApp';


CREATE TABLE public.cuponerapp_usada
(
    id             bigserial               NOT NULL,
    cuponerapp_id  int4                    NOT NULL,
    arq_usuario_id int8                    NOT NULL,
    puntos         int4                    NOT NULL,
    momento        timestamp DEFAULT now() NOT NULL,
    CONSTRAINT cuponerapp_usada_pk PRIMARY KEY (id)
);


-- public.cuponerapp_usada foreign keys

ALTER TABLE public.cuponerapp_usada
    ADD CONSTRAINT cuponerapp_usada_cuponerapp_fk FOREIGN KEY (cuponerapp_id) REFERENCES public.cuponerapp (id);

-----DDL Recargas telefonicas
CREATE TABLE public.compania_cel
(
    id         serial4 NOT NULL,
    nombre     text    NOT NULL,
    id_product int4    NOT NULL,
    CONSTRAINT compania_cel_pk PRIMARY KEY (id)
);

CREATE TABLE public.denominacion_recarga_cel
(
    id              serial4 NOT NULL,
    compania_cel_id int4    NOT NULL,
    monto           int4    NOT NULL,
    puntos          int4    NOT NULL,
    CONSTRAINT denominacion_recarga_cel_pk PRIMARY KEY (id)
);

-- public.denominacion_recarga_cel foreign keys

ALTER TABLE public.denominacion_recarga_cel
    ADD CONSTRAINT denominacion_recarga_cel_compania_cel_fk FOREIGN KEY (compania_cel_id) REFERENCES public.compania_cel (id);

CREATE TABLE public.denominacion_recarga_cel_usada
(
    id                          bigserial               NOT NULL,
    denominacion_recarga_cel_id int4                    NOT NULL,
    arq_usuario_id              int8                    NOT NULL,
    momento                     timestamp DEFAULT now() NOT NULL,
    puntos                      int4                    NOT NULL,
    cel                         text                    NOT NULL,
    CONSTRAINT denominacion_recarga_cel_usada_pk PRIMARY KEY (id)
);

-- public.denominacion_recarga_cel_usada foreign keys

ALTER TABLE public.denominacion_recarga_cel_usada
    ADD CONSTRAINT denominacion_recarga_cel_usada_denominacion_recarga_cel_fk FOREIGN KEY (denominacion_recarga_cel_id) REFERENCES public.denominacion_recarga_cel (id);
