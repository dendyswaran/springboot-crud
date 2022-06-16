package com.deloitte.baseapp.modules.tasklist.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

// TODO: remove data and only put annotations to used methods --> @SetterAndGetter


@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "T_STR_SITE_EQP_ATT")
public class TStrSiteEqpAtt {
    //    @Id
//    @Column(length = 50)
//    private String id; // is this supposed to be auto generated or user assign?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(length = 250)
    private String filename;

    // TODO: need to check how to properly set this up for image upload.
    //  need to create a Controller to support this.
//    private byte[] content;

    @Column(length = 50)
    private String appSiteEqpId;

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

}
