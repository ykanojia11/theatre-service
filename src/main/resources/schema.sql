CREATE TABLE theatres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    language VARCHAR(255),
    genre VARCHAR(255)
);

CREATE TABLE shows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    theatre_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    show_time DATETIME NOT NULL,
    available_seats INT,
    FOREIGN KEY (theatre_id) REFERENCES theatres(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);
