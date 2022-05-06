package com.deloitte.baseapp.modules.account.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "roles")
public class Role implements GenericEntity<Role>, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Enumerated(EnumType.STRING)
  @NotBlank
  @Column(length = 20)
  private String name;

  @Override
  public void update(Role source) {
    this.name = source.getName();
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public Role createNewInstance() {
    Role role = new Role();
    role.update(this);
    return role;
  }
}