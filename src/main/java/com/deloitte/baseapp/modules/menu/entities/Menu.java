package com.deloitte.baseapp.modules.menu.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Menu  implements Serializable, GenericEntity<Menu> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Menu> children = new HashSet<>();

    /**
     * What will happen when a menu is clicked
     */
    @Enumerated(EnumType.STRING)
    private EMenuClickEvent clickEvent = EMenuClickEvent.OPEN;

    private String href;

    /**
     * Menu ordering is based on the highest priority
     */
    private Integer priority;

    private Boolean isActive;

    @Override
    public void update(Menu source) {
        this.name = source.getName();
        this.code = source.getCode();
        if (null == source.getParent()
                || (null != this.getId() && this.getId().equals(source.getParent().getId()))) {
            this.parent = null;
        } else {
            this.parent = source.getParent();
        }
        this.children = source.getChildren();
        this.href = source.getHref();
        this.priority = source.getPriority();
        this.clickEvent = source.getClickEvent();
        this.isActive = source.getIsActive();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Menu createNewInstance() {
        Menu newInstance = new Menu();
        newInstance.update(this);

        return newInstance;
    }
}
