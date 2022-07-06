package com.deloitte.baseapp.modules.tasklist.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.tasklist.object.EqpType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_APP_SITE_EQP")
public class TAppSiteEqp implements Serializable, GenericEntity<TAppSiteEqp> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(length = 50)
    private String cd;

    @Column(length = 250)
    private String dscp;

    //TODO: to be removed! --> redundant
    @Column(length = 50)
    private String mtMakeId;

    @Column(length = 200)
    private String model;

    @Column(length = 200)
    private String serialNo;

    // TODO: propose to change into Boolean for flag?
//    @Column(length = 1)
//    private String hlthChckFlg;
    private Boolean hlthChckFlg;

    // TODO: propose to change variable name into hlthChckPass --> then true --> pass false otherwise
//    @Column(length = 1)
//    private String hlthChckVal;
    private Boolean hlthChckPass;

    // TODO: to be merged with eqpType
    @Column(length = 10)
    private String scrpReuseTyp;

    @Column(length = 10)
    private EqpType eqpType;

    @Column(length = 50)
    private String appSiteId;

    private Boolean newEqpFlag;

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

    // TODO: can nullable be false for LAZY evaluation?

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_site_id_fk")
    private TAppSite tAppSite;

    // TODO: try to figure out how to join with ENUM.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mt_make_id_fk")
    private TMtMake tMtMake;

    @Override
    public void update(TAppSiteEqp source) {

    }

    @Override
    public TAppSiteEqp createNewInstance() {
        return null;
    }
}

