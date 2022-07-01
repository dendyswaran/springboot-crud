package com.deloitte.baseapp.modules.assignment.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "T_MT_WAREHOUSE")
@Entity
@Data
public class TMtWarehouse implements Serializable, GenericEntity<TMtWarehouse> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String cd;

    @Column(length = 250)
    private String dscp;

    @Column(length = 250)
    private String address;

    @Column(length = 10)
    private String postcode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dtCreated;

    @Column(length = 50)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime dtUpdated;

    @Column(length = 50)
    private String updatedBy;

    @Column(columnDefinition = "integer default 0")
    private Integer version;

    @JsonIgnore
    @OneToMany(mappedBy = "tMtWarehouse", cascade = CascadeType.ALL)
    private List<TAppSiteWh> tAppSiteWhList = new ArrayList<>();

    @Override
    public void update(TMtWarehouse source) {

    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public TMtWarehouse createNewInstance() {
        return null;
    }

}