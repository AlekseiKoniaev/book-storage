package ru.koniaev.bookstorage.api.response;

import lombok.Getter;

@Getter
public class SuccessResponse extends Response {

    private final int id;

    public SuccessResponse(int id) {
        super(true);
        this.id = id;
    }
}
