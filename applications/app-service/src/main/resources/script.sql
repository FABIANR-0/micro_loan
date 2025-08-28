-- Create tables
--estados de la solicitud
CREATE TABLE loan_status
(
    status_id   SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT
);

--tipos de prestamo
CREATE TABLE loan_type
(
    loan_type_id    SERIAL PRIMARY KEY,
    name            VARCHAR(100)   NOT NULL,
    min_amount      DECIMAL(15, 2) NOT NULL,
    max_amount      DECIMAL(15, 2) NOT NULL,
    interest_rate   DECIMAL(5, 4)  NOT NULL,
    auto_validation BOOLEAN DEFAULT FALSE
);

--solicitudes de prestamos
CREATE TABLE loan_application
(
    application_id   SERIAL PRIMARY KEY,
    amount           DECIMAL(10, 2) NOT NULL,
    term             INTEGER        NOT NULL, --en meses
    email            VARCHAR(255)   NOT NULL,
    status_id        INTEGER        NOT NULL,
    loan_type_id     INTEGER        NOT NULL,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (status_id) REFERENCES loan_status (status_id),
    FOREIGN KEY (loan_type_id) REFERENCES loan_type (loan_type_id)
);

-- Insert initial data
-- Estados de la solicitud
INSERT INTO loan_status (name, description)
VALUES ('Pendiente', 'La solicitud ha sido enviada y está esperando revisión inicial'),
       ('En Revisión', 'La solicitud está siendo evaluada por el comité de préstamos'),
       ('Aprobado', 'La solicitud de préstamo ha sido aprobada'),
       ('Rechazado', 'La solicitud ha sido denegada basándose en los criterios de evaluación');

-- Tipos de préstamo
INSERT INTO loan_type (name, min_amount, max_amount, interest_rate, auto_validation)
VALUES ('Préstamo Personal', 4000000.00, 20000000.00, 0.1200, TRUE),
       ('Hipoteca Vivienda', 15000000.00, 200000000.00, 0.0350, FALSE),
       ('Préstamo Vehicular', 10000000.00, 120000000.00, 0.0650, TRUE),
       ('Tarjeta de Crédito', 1000000.00, 20000000.00, 0.1800, TRUE);