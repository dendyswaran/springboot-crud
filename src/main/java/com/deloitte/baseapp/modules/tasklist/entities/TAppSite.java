package com.deloitte.baseapp.modules.tasklist.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.assignment.entities.TAppSiteWh;
import com.deloitte.baseapp.modules.assignment.entities.TStrSiteAccs;
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
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_APP_SITE")
public class TAppSite implements Serializable, GenericEntity<TAppSite> {

    /*
        Entity Attributes
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    // TODO: GenericEntity has defined a signature of Long id --> use GenericEntity and use Long id and define a separate uuid
    //  OR we can use JPARepository instead of GenericEntity.

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(insertable = false, updatable = false, nullable = false)
//    private UUID id;


    @Column(length = 50)
    private String cd;

    @Column(length = 250)
    private String nm;

    private int eqpCount;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtDecom;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtDecomAct;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtScrap;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtScrapAct;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtOem;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dtOemAct;

    // TODO: To be removed
    @Column(length = 50)
    private String mtWfStatusId;

    // TODO: propose to change flag into Boolean
//    @Column(length = 1)
//    private String oemWfFlg;
    private Boolean oemWfFlag;

    //    @Column(length = 1)
//    private String scrapWfFlg;
    private Boolean scrapWfFlg;

    // TODO: consider introduce timezone
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

    // TODO: change back to LAZY evaluation if status is not required to be fetched when tasklist is first loaded.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mt_wf_status_fk")
    private TMtWfStatus tMtWfStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "tAppSite", cascade = CascadeType.ALL)
    private List<TAppSiteEqp> tAppSiteEqpList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tAppSite", cascade = CascadeType.ALL)
    private List<TStrSiteAccs> tStrSiteAccsList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tAppSite", cascade = CascadeType.ALL)
    private List<TAppSiteWh> tAppSiteWhList= new ArrayList<>();

    /*
       Add or remove TAppSiteEqp from TAppSite
     */
    public void addTAppSiteEqp(TAppSiteEqp tAppSiteEqp) {
        tAppSiteEqpList.add(tAppSiteEqp);
        tAppSiteEqp.setTAppSite(this);
    }

    public void removeTAppSiteEqp(TAppSiteEqp tAppSiteEqp) {
        tAppSiteEqpList.remove(tAppSiteEqp);
        tAppSiteEqp.setTAppSite(null);
    }

    /*
        Methods from GenericEntity
     */

    @Override
    public void update(TAppSite source) {
    }

    @Override
    public TAppSite createNewInstance() {
        return null;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
