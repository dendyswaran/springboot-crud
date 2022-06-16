package com.deloitte.baseapp.modules.tasklist.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.tasklist.object.TMtWfStatusCd;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_MT_WF_STATUS")
public class TMtWfStatus implements GenericEntity<TMtWfStatus> {

    // constructor to be used by Data loader.
    public TMtWfStatus(Long id,
                       TMtWfStatusCd cd,
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

    @Column(length = 50)
    private TMtWfStatusCd cd;

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

    // TODO: Need to ask about this --> when this is dropped --> we cannot drop TAppSite --> but there will be error when reloading.
    @JsonIgnore
    @OneToMany(mappedBy = "tMtWfStatus", cascade = CascadeType.ALL)
    private List<TAppSite> tAppSiteList = new ArrayList<>();

    @Override
    public void update(TMtWfStatus source) {

    }

    @Override
    public TMtWfStatus createNewInstance() {
        return null;
    }
}
