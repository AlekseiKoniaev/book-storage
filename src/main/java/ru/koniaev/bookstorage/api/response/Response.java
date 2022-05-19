package ru.koniaev.bookstorage.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Response {
    
    private boolean result;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Response response = (Response) o;
    
        return result == response.result;
    }
    
    @Override
    public int hashCode() {
        return (result ? 1 : 0);
    }
}