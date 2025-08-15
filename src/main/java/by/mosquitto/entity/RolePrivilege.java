package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_privilege")
@Data
@NoArgsConstructor
public class RolePrivilege {

    @EmbeddedId
    private RolePrivilegeKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "id_role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("privilegeId")
    @JoinColumn(name = "id_priv")
    private Privilege privilege;

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
        this.id = new RolePrivilegeKey(role.getId(), privilege.getId());
    }
}

