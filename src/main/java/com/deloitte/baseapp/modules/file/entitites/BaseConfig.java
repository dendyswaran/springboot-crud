package com.deloitte.baseapp.modules.file.entitites;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.account.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "base_config")
public class BaseConfig implements Serializable {

    @Id
    private String id;

    private String description;

    @NotBlank
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BaseConfig(String id, String description, String content) {
        this.id = id;
        this.description = description;
        this.content = content;
    }

}
