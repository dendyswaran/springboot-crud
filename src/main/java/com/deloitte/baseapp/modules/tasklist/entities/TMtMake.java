package com.deloitte.baseapp.modules.tasklist.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.tasklist.object.TMtMakeCd;
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


@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_MT_MAKE")
public class TMtMake implements Serializable, GenericEntity<TMtMake> {

    // constructor to be used by Data loader.
    public TMtMake(Long id,
                   TMtMakeCd cd,
                   String dscp,
                   String createdBy,
                   String updatedBy,
                   int version) {

        this.setId(id);
        this.setCd(cd);
        this.setDscp(dscp);
        this.setCreatedBy(createdBy);
        this.setUpdatedBy(updatedBy);
        this.setVersion(version);
    }

    @Id
    public Long id;

    @Column(length = 50, unique = true)
    private TMtMakeCd cd;

    @Column(length = 250)
    private String dscp;

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
    @OneToMany(mappedBy = "tMtMake", cascade = CascadeType.ALL)
    private List<TAppSiteEqp> tAppSiteEqpList = new ArrayList<>();

    /*
        Add or remove TAppSiteEqp from TMtMake
    */
    public void addTAppSiteEqp(TAppSiteEqp tAppSiteEqp) {
        tAppSiteEqpList.add(tAppSiteEqp);
        tAppSiteEqp.setTMtMake(this);
    }

    public void removeTAppSiteEqp(TAppSiteEqp tAppSiteEqp) {
        tAppSiteEqpList.remove(tAppSiteEqp);
        tAppSiteEqp.setTMtMake(null);
    }

    @Override
    public void update(TMtMake source) {
    }

    @Override
    public TMtMake createNewInstance() {
        return null;
    }
}
