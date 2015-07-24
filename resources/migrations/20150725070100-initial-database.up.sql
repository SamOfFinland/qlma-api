CREATE TABLE IF NOT EXISTS messages (
    id INT NOT NULL auto_increment PRIMARY KEY,
    message text NOT NULL,
    message_from int,
    message_to int,
    message_type ENUM('kalenteri', 'viesti', 'tiedosto', 'kuva')
)
