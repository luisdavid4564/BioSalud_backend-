INSERT IGNORE INTO `roles` (`id_rol`, `nombre`, `descripcion`) VALUES
(1, 'ADMIN', 'Administrador del sistema con todos los permisos'),
(2, 'RECEPCIONISTA', 'Usuario de recepción para gestionar pacientes y citas'),
(3, 'MEDICO', 'Usuario médico para registrar consultas y diagnósticos');

INSERT IGNORE INTO `usuarios` (`id_usuario`, `nombre_usuario`, `clave`, `estado`, `id_rol`) VALUES
(1, 'admin', '$2b$12$tVebN1WpmhtD48TQDi7W1ea1dNYOF2iig/OAa4sClnHvu4w.Ygqo6', 'ACTIVO', 1);
INSERT IGNORE INTO `usuarios` (`id_usuario`, `nombre_usuario`, `clave`, `estado`, `id_rol`) VALUES
(2, 'recepcionista', '$2b$12$p5StX2XT8QTz4sT0rM0vfeHIy2RDAjhAwTDsmUUXvwnE3VBkULL5q', 'ACTIVO', 2);

INSERT IGNORE INTO `paciente` (`id_paciente`, `dni`, `nombres`, `apellidos`, `sexo`, `fecha_nacimiento`, `telefono`, `email`, `alergias`, `estado`) VALUES 
(1, '00047852', 'Lucas', 'Lopez', 'Masculino', '2025-09-30', '956812511', 'lucas@hotmail.com', b'1', 'Activo'), 
(2, '00047850', 'Luis', 'Sosa', 'Masculino', '2025-10-07', '956812511', 'luis@hotmail.com', b'0', 'Activo'), 
(3, '00147859', 'Juana', 'Guitierrez', 'Femenino', '2025-10-07', '956812000', 'juana@hotmail.com', b'0', 'Inactivo'), 
(4, '00051234', 'María', 'Ramos', 'Femenino', '1998-06-12', '955444333', 'maria.ramos@gmail.com', b'1', 'Activo'), 
(5, '00067891', 'Carlos', 'Vega', 'Masculino', '1995-12-25', '987123456', 'carlos.vega@gmail.com', b'0', 'Inactivo');
