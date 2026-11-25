INSERT INTO users (user_name, name, password, rol)
VALUES ('sanjx', 'Santiago', '123', 'ADMIN')
ON DUPLICATE KEY UPDATE user_name = user_name;

INSERT INTO productos (id, nombre, descripcion, precio, disponible)
VALUES (1, 'JFO-0210', 'Filtro Aceite', 900.0, TRUE)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO users (user_name, name, password, rol)
VALUES ('joseva', 'Jose', '123', 'REGULAR')
    ON DUPLICATE KEY UPDATE user_name = user_name;