CREATE TABLE  IF NOT EXISTS cars
    (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(16) NOT NUll,
    model VARCHAR(16) NOT NUll,
    price INTEGER CHECK (cars.price > 0) NOT NUll,
    );

CREATE TABLE  IF NOT EXISTS owners
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(16) NOT NULL,
    age INTEGER CHECK (owners.age > 0) NOT NUll,
    has_driving_licence BOOLEAN DEFAULT false,
    car_id BIGSERIAL REFERENSES cars(id)
);
