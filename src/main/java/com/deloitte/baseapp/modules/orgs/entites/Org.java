package com.deloitte.baseapp.modules.orgs.entites;

import com.deloitte.baseapp.commons.TGenericEntity;
import com.deloitte.baseapp.modules.account.entities.OrgUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="T_ORG",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Getter
@Setter
@ToString
public class Org implements TGenericEntity<Org, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private UUID id;

    private String code;
    private String name;

    @OneToOne(mappedBy = "org")
    private OrgUser user;

    @CreatedDate
    @Column(name="date_created")
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @CreatedBy
    private OrgUser orgCreatedBy;

    @OneToMany(mappedBy = "org")
    private Set<OrgUserGroup> orgGroup;

    public Org(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Org() {

    }

    @Override
    public void update(Org source) {

    }

    @Override
    public Org createNewInstance() {
        return null;
    }
}
