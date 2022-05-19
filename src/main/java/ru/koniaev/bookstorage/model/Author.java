package ru.koniaev.bookstorage.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Author {
    
    private int id;
    private String firstName;
    private String secondName;
    private Date birthday;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Author author = (Author) o;
        
        if (!firstName.equals(author.firstName)) return false;
        if (!secondName.equals(author.secondName)) return false;
        return birthday.equals(author.birthday);
    }
    
    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + birthday.hashCode();
        return result;
    }
}
