CREATE TABLE book (
    id SERIAL NOT NULL,
    title VARCHAR NOT NULL,
    year INTEGER NOT NULL,
    page_count INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (title)
);