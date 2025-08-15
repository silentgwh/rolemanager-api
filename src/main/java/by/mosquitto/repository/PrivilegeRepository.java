package by.mosquitto.repository;

import by.mosquitto.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    boolean existsByName(String name);
}
