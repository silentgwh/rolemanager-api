package by.mosquitto.repository;

import by.mosquitto.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Profile> findByNameIgnoreCase(String name);
    Optional<Profile> findByName(String name); // 👈 Добавь это, если используешь без ignoreCase
    boolean existsByName(String name);
}

