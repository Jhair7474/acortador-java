
##Mysql
CREATE DATABASE IF NOT EXISTS enlace;
USE enlace;


DROP TABLE IF EXISTS links;

CREATE TABLE links (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_url TEXT NOT NULL,
    shortened_url VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


## MongoDB MongoDB creará automáticamente una base de datos llamada test 
## y, dentro de ella, una colección llamada enlace