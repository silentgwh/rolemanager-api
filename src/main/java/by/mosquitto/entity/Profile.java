package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String comment;

    @Column(name = "date_corr")
    private LocalDateTime dateCorr;

    @ManyToOne
    @JoinColumn(name = "user_corr")
    private User userCorr;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "profile_role",
            joinColumns = @JoinColumn(name = "id_prof"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles = new HashSet<>();
}
