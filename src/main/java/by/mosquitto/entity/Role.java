package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "userCorr")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Short type;

    private String comment;

    @Column(name = "date_corr")
    private LocalDateTime dateCorr;

    @ManyToOne
    @JoinColumn(name = "user_corr")
    private User userCorr;

    @ManyToMany(mappedBy = "roles")
    private Set<Profile> profiles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_priv")
    )
    private Set<Privilege> privileges = new HashSet<>();
}
