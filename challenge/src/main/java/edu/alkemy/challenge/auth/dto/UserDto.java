package edu.alkemy.challenge.auth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @Email(message = "Username must be an email" )
    private String username;
    @Size(min = 8)
    private String password;
    public UserDto( String username, String password) {

        this.username = username;
        this.password = password;

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
