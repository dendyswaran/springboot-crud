package com.deloitte.baseapp.modules.orgs.entites;

import com.deloitte.baseapp.commons.TGenericEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="T_ORG_USRGRP", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Getter
@Setter
@ToString
public class OrgUserGroup implements TGenericEntity<OrgUserGroup,UUID> {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", nullable=false)
    private UUID id;

    private String code;
    private String name;

    @ManyToOne
    @JoinColumn(name="org_id")
    private Org org;

    @Override
    public void update(OrgUserGroup source) {
        this.name = source.getName();
        this.id = source.getId();
        this.code = source.getCode();
    }

    @Override
    public OrgUserGroup createNewInstance() {
        OrgUserGroup newInstance = new OrgUserGroup();
        newInstance.update(this);
        return newInstance;
    }
}
