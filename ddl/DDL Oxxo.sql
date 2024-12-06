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
        'RC7SNy1qac6Rj6hlFRV0g37xGDfu2ASEB71hMUHe',
        'header x-api-key', true),
       ('OXXO', 'OXXO_CANJES_MENSUALES', 'OXXO_CANJES_MENSUALES', '100',
        'Cantidad máxima de puntos canjeables por mes', true),
       ('OXXO', 'OXXO_PARAM_PARTNER_ID', 'OXXO_PARAM_PARTNER_ID', '34',
        'header partner-id', true),
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
    arq_usuario_id int8     NOT NULL,
    member_id      varchar(20) NOT NULL,
    CONSTRAINT oxxo_member_id_pk PRIMARY KEY (id),
    CONSTRAINT oxxo_member_id_unique UNIQUE (arq_usuario_id)
);

----
insert into arq_propiedad(grupo_codigo, codigo, nombre, valor, descripcion, activo)
values ('OXXO', 'OXXO_URL_CANJE', 'OXXO_URL_CANJE',
        'https://challenger-testing.mioxxo-dev.io/api/v1/campaign/partner/accrual/points', 'URL para canjear puntos', true);

ALTER TABLE public.usuario_puntos_color ADD CONSTRAINT usuario_puntos_color_unique UNIQUE (id_arq_usuario);

insert into arq_propiedad(grupo_codigo, codigo, nombre, valor, descripcion, activo)
values ('TWILIO', 'SMS.TWILIO.ACCOUNT-SID', 'SMS.TWILIO.ACCOUNT-SID', 'ACa9909191727d20f59834b91b513f5580',
        'Cuenta de Twilio', true),
       ('TWILIO', 'SMS.TWILIO.AUTH-TOKEN', 'SMS.TWILIO.AUTH-TOKEN', '6a34f172d3262b1f35ebf844dc066371',
        'Token de Twilio', true),
       ('TWILIO', 'SMS.TWILIO.FROM-NUMBER', 'SMS.TWILIO.FROM-NUMBER', '+15202638223',
        'Teléfono emisor de Twilio', true);
