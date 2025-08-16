package by.mosquitto.repository;

import by.mosquitto.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    boolean existsByName(String name);
    Optional<Privilege> findByName(String name);
    List<Privilege> findByType(short type); // ← вот этот метод
}
