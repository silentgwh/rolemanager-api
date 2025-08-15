package by.mosquitto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePrivilegeKey implements Serializable {

    @Column(name = "id_role")
    private Long roleId;

    @Column(name = "id_priv")
    private Long privilegeId;
}
