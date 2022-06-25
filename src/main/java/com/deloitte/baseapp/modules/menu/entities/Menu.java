package com.deloitte.baseapp.modules.menu.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Menu implements Serializable, GenericEntity<Menu> {

    @Id
    @CsvBindByName
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @CsvBindByName
    private String name;

    @NotBlank
    @Column(unique = true)
    @CsvBindByName
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Transient
    @CsvBindByName(column = "parent_id")
    private Long parentId;


    // disable auto generation of getter and setter for this attribute to avoid infinite recursion during JsonConversion

    //    @Setter(AccessLevel.NONE)
    // TODO: after model mapping work try to switch back to SET

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Menu> children;

    /**
     * What will happen when a menu is clicked
     */
    @Enumerated(EnumType.STRING)
    private EMenuClickEvent clickEvent = EMenuClickEvent.OPEN;

    @Transient
    @CsvBindByName
    private String pathname;

    private String href;

    /**
     * Menu ordering is based on the highest priority
     */
    @CsvBindByName
    private Integer priority;

    @CsvBindByName(column = "is_active")
    private Boolean isActive;


//    @JsonIgnore
//    public Set<Menu> getChildren() {
//        return children;
//    }
//
    @JsonIgnore
    public List<Menu> getChildren() {
        return children;
    }

    @Override
    public void update(Menu source) {
        this.name = source.getName();
        this.code = source.getCode();

        // if "source" don't have a parent --> this parent is null
        // OR
        // if this id not null, meaning menu exists AND this id (this menu)
        // is equal to source parent id (parent menu)), meaning current object is a parent of itself which is also invalid.
        if (null == source.getParent()
                || (null != this.getId() && this.getId().equals(source.getParent().getId()))) {
            this.parent = null;
        } else {
            this.parent = source.getParent(); // update
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
