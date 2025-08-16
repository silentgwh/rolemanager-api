package by.mosquitto.repository;

import by.mosquitto.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Role> findByNameIgnoreCase(String name);
    Optional<Role> findByName(String name); // 👈 Добавь это
    boolean existsByName(String name);
}
