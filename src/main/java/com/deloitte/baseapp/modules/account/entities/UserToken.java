package com.deloitte.baseapp.modules.account.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_token")
public class UserToken implements GenericEntity<UserToken>, Serializable {

    @Id
    private Long user_id;

    @NotBlank
    private String token;

    public UserToken(Long user_id, String token) {
        this.user_id = user_id;
        this.token = token;
    }

    public UserToken() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void update(UserToken source) {
        this.token = source.getToken();
    }

    @Override
    public Long getId() {
        return this.user_id;
    }

    @Override
    public UserToken createNewInstance() {
        UserToken userToken = new UserToken();
        userToken.update(this);
        return userToken;
    }
}
