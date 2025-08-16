package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

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
