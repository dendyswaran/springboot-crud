package com.deloitte.baseapp.modules.assignment.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "T_APP_SITE_WH")
@Entity
@Data
public class TAppSiteWh implements Serializable, GenericEntity<TAppSiteWh> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_site_id_fk")
    private TAppSite tAppSite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mt_warehouse_id_fk")
    private TMtWarehouse tMtWarehouse;

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

    @Override
    public void update(TAppSiteWh source) {

    }

    @Override
    public TAppSiteWh createNewInstance() { return null; }

    @Override
    public Long getId() {
        return this.id;
    }

}
