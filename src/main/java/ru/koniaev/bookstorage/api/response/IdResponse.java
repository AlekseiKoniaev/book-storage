package ru.koniaev.bookstorage.api.response;

import lombok.Getter;

@Getter
public class IdResponse extends Response {

    private final int id;

    public IdResponse(int id) {
        super(true);
        this.id = id;
    }
}
