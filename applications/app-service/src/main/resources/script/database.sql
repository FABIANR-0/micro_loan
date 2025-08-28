-- Create tables
--estados de la solicitud
CREATE TABLE loan_status
(
    status_id   BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT
);

--tipos de prestamo
CREATE TABLE loan_type
(
    loan_type_id    BIGSERIAL PRIMARY KEY,
    name            VARCHAR(100)   NOT NULL,
    min_amount      DECIMAL(15, 2) NOT NULL,
    max_amount      DECIMAL(15, 2) NOT NULL,
    interest_rate   DECIMAL(5, 2)  NOT NULL, --MENSUAL
    auto_validation BOOLEAN DEFAULT FALSE
);

--solicitudes de prestamos
CREATE TABLE loan_application
(
    application_id   BIGSERIAL PRIMARY KEY,
    amount           DECIMAL(10, 2) NOT NULL,
    term             INTEGER        NOT NULL, --en meses
    email            VARCHAR(255)   NOT NULL,
    status_id        INTEGER        NOT NULL,
    loan_type_id     INTEGER        NOT NULL,
    application_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (status_id) REFERENCES loan_status (status_id),
    FOREIGN KEY (loan_type_id) REFERENCES loan_type (loan_type_id)
);

-- Insert initial data
-- Estados de la solicitud
INSERT INTO loan_status (name, description)
VALUES ('PENDIENTE', 'La solicitud ha sido enviada y está esperando revisión inicial'),
       ('APROBADO', 'La solicitud de préstamo ha sido aprobada'),
       ('RECHAZADO', 'La solicitud ha sido denegada basándose en los criterios de evaluación');

-- Tipos de préstamo
INSERT INTO loan_type (name, min_amount, max_amount, interest_rate, auto_validation)
VALUES ('Préstamo Personal', 4000000.00, 20000000.00, 1.00, TRUE),
       ('Hipoteca Vivienda', 15000000.00, 200000000.00, 0.30, FALSE),
       ('Préstamo Vehicular', 10000000.00, 120000000.00, 0.60, TRUE),
       ('Tarjeta de Crédito', 1000000.00, 20000000.00, 1.50, TRUE);