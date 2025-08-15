package by.mosquitto.repository;

import by.mosquitto.entity.Role;
import by.mosquitto.entity.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    boolean existsByRoleId(Long roleId);
    boolean existsByPrivilegeId(Long privilegeId);
    List<RolePrivilege> findAllByRoleId(Long roleId);
    void deleteAllByRoleId(Long roleId);
    void deleteAllByPrivilegeId(Long privilegeId);
}
