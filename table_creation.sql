CREATE TABLE companies (
	id INTEGER,  
    name VARCHAR(50), 
    address VARCHAR(50), 
    buisness_number VARCHAR(15), 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_company PRIMARY KEY (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);


CREATE TABLE users (
	id INTEGER, 
    email VARCHAR(50) NOT NULL, 
    password VARCHAR(50) NOT NULL, 
    login_ban DATE,
    rent_ban DATE, 
    message_ban DATE, 
    deleted VARCHAR(6), 
    company INTEGER, 
    firstNname VARCHAR(50), 
    lastName VARCHAR(50), 
    
    CONSTRAINT pk_user PRIMARY KEY (id), 
    CONSTRAINT chk_userRole CHECK (role IN ('renter', 'admin')), 
    CONSTRAINT uniq_email UNIQUE (email), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false')), 
    CONSTRAINT fk_usersCompany FOREIGN KEY (company) REFERENCES companies(Id)
);


CREATE TABLE roles (
	id INTEGER, 
    role VARCHAR(50), 
    
    CONSTRAINT pk_roles PRIMARY KEY (id)
);


CREATE TABLE privileges (
	id INTEGER, 
	privilege VARCHAR(50), 
    
    CONSTRAINT pk_privileges PRIMARY KEY (id)
);


CREATE TABLE roles_privileges (
	role INTEGER, 
    privilege INTEGER, 
    
    CONSTRAINT pk_rolesPrivileges PRIMARY KEY (role, privilege), 
    CONSTRAINT fk_rolesPrivilegesRole FOREIGN KEY (role) REFERENCES roles(id), 
    CONSTRAINT fk_rolesPrivilegesPrivilege FOREIGN KEY (privilege) REFERENCES privileges (id)
);


CREATE TABLE users_roles (
	user INTEGER, 
    role INTEGER, 
    
    CONSTRAINT pk_usersRoles PRIMARY KEY (user, role), 
    CONSTRAINT fk_usersRolesUsers FOREIGN KEY (user) REFERENCES users (id), 
    CONSTRAINT fk_usersRolesRoles FOREIGN KEY (role) REFERENCES roles (id)
);


CREATE TABLE manufacturers (
	id INTEGER, 
    name VARCHAR(50), 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_manufacturer PRIMARY KEY (id), 
    CONSTRAINT chk_manufacturer CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE car_class (
	id INTEGER, 
    name VARCHAR(50), 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_carClass PRIMARY KEY (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE transmission_type (
	id INTEGER, 
    name VARCHAR(50), 
    deleted VARCHAR(6), 
    
    CONSTRAINT fk_transmissionType PRIMARY KEY (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE car_models (
	id INTEGER, 
    name VARCHAR(50),
    manufacturer INTEGER,
    class INTEGER,
    transmission INTEGER, 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_carModels PRIMARY KEY (id), 
    CONSTRAINT fk_carModelsManufacturer FOREIGN KEY (manufacturer) REFERENCES manufacturers (id),
    CONSTRAINT fk_carModelClass FOREIGN KEY (class) REFERENCES car_class (id), 
    CONSTRAINT fk_carModelTransmission FOREIGN KEY (transmission) REFERENCES transmission_type (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE fuel_types (
	id INTEGER, 
    name VARCHAR(50), 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_fuelType PRIMARY KEY (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE pricings (
	id INTEGER, 
    distance_limit DOUBLE, 
    regular_price DOUBLE, 	-- Redovna dnevna cena
    overuse_price DOUBLE, 	-- Cena po prekoracenom km
    collision_damaga_waiver DOUBLE, 
    discount_days INTEGER, 	-- Da bi dobio popust od discount_percent posto, mora da rentira na bar ovoliko dana
    discount_percent DOUBLE, 
    deleted VARCHAR(6), 
	name VARCHAR(50), 
    owner INTEGER, 
    
    CONSTRAINT pk_pricing PRIMARY KEY (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false')), 
    CONSTRAINT fk_pricingOwner FOREIGN KEY (owner) REFERENCES users(id)
);

CREATE TABLE cars (
	id INTEGER, 
    owner INTEGER, 
    model INTEGER, 
    fuel_type INTEGER, 
    pricing INTEGER, 
    milage DOUBLE, 
    deleted VARCHAR(6), 
    name VARCHAR(50), 
    
    CONSTRAINT pk_car PRIMARY KEY (id),
    CONSTRAINT fk_carUser FOREIGN KEY (owner) REFERENCES users(id), 
    CONSTRAINT fk_carModel FOREIGN KEY (model) REFERENCES car_models(id), 
    CONSTRAINT fk_carFuelType FOREIGN KEY (fuel_type) REFERENCES fuel_types(id), 
    CONSTRAINT fk_carPricing FOREIGN KEY (pricing) REFERENCES pricings (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE messages (
	id INTEGER, 
    content VARCHAR(150), 
    from_user INTEGER, 
    to_user INTEGER, 
    date TIMESTAMP, 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_messages PRIMARY KEY (id), 
    CONSTRAINT fk_messagesFromUser FOREIGN KEY (from_user) REFERENCES users (id), 
    CONSTRAINT fk_messagesToUser FOREIGN KEY (to_user) REFERENCES users (id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE rent_requests (
	id INTEGER, 
    user INTEGER, 
    car INTEGER, 
    end_time DATETIME, 
    start_time DATETIME, 
    request_status VARCHAR(15), 
    request_group INTEGER, 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_rentRequests PRIMARY KEY (id), 
    CONSTRAINT fk_rentRequestUser FOREIGN KEY (user) REFERENCES users(id), 
    CONSTRAINT fk_rentRequestCar FOREIGN KEY (car) REFERENCES cars(id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE car_reviews (
	id INTEGER, 
    user INTEGER, 
    car INTEGER, 
    rating INTEGER, 
    review VARCHAR(150), 
    time DATETIME, 
	approved VARCHAR(6), 
    deleted VARCHAR(6), 
    
	CONSTRAINT pk_carReview PRIMARY KEY (id), 
    CONSTRAINT fk_carReviewUser FOREIGN KEY (user) REFERENCES users(id), 
    CONSTRAINT fk_carReviewCar FOREIGN KEY (car) REFERENCES cars(id),
	CONSTRAINT chk_ratingValue CHECK ((rating > 1) AND (ratin <= 5)), 
    CONSTRAINT chk_approved CHECK (approved IN ('true', 'false')), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE renting_report (
	id INTEGER, 
    renting_instance INTEGER, 
    report VARCHAR(150), 
    added_mileage DOUBLE, 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_rentingReport PRIMARY KEY (id), 
    CONSTRAINT fk_rentingReportInstance FOREIGN KEY (renting_instance) REFERENCES rent_requests(id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE gps_position (
	id INTEGER,
    car INTEGER, 
    latitude DOUBLE, 
    longitude DOUBLE, 
    email VARCHAR(50) NOT NULL, 
    password VARCHAR(50) NOT NULL, 
    deleted VARCHAR(6), 
    
    CONSTRAINT pk_gpsPosition PRIMARY KEY (id), 
    CONSTRAINT fk_gpsPositionCar FOREIGN KEY (car) REFERENCES cars(id), 
    CONSTRAINT chk_deleted CHECK (deleted IN ('true', 'false'))
);

CREATE TABLE reciept (
	id INTEGER, 
    customer INTEGER, 
    vehicle_owner INTEGER, 
    total_sum DOUBLE, 
    paid DATE, 			-- If null, presume not paid
    
    CONSTRAINT pk_reciept PRIMARY KEY (id), 
    CONSTRAINT fk_recieptCustomer FOREIGN KEY (customer) REFERENCES users(id),  
    CONSTRAINT fk_recieptOwner FOREIGN KEY (vehicle_owner) REFERENCES users (id)
);

CREATE TABLE reciept_article (
	id INTEGER, 
    service VARCHAR(50), 
    price DOUBLE, 
    reciept INTEGER, 
    times INTEGER, 	-- e.g., how many days the car was rented for
    
    CONSTRAINT pk_recieptArticle PRIMARY KEY (id), 
    CONSTRAINT chk_recieptArticleService CHECK (service IN ('daily rent', 'overuse', 'collision damage waiver')), 
    CONSTRAINT fk_recieptArticleReciept FOREIGN KEY (reciept) REFERENCES reciept(id)
);


-- 
-- CREATE VIEW login_view AS 
-- 	SELECT u.email, u.password, 'USER'
--     FROM users u
--     UNION 
--     SELECT c.email, c.password, 'COMPANY'
--     FROM companies c
--     UNION 
--     SELECT g.email, g.password, 'CAR'
--     FROM gps_position g;
