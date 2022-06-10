package com.deloitte.baseapp.modules.MTStatus.entities;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="T_MT_STATUS")
@Getter
@Setter
@ToString
public class MtStatus extends AuditModel implements TGenericEntity<MtStatus, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String code;
    // TODO: check whether to use String or Enum
    private String dscp;

    @OneToMany(mappedBy = "mtStatus")
    private Set<OrgUser> orgUsers;

    @Override
    public void update(MtStatus source) {
        this.code = source.getCode();
        this.dscp = source.getDscp();
    }

    @Override
    public MtStatus createNewInstance() {
        MtStatus newInstance = new MtStatus();
        newInstance.update(this);
        return newInstance;
    }
}