package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "privilege")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Short type; // 0 = приватная, 1 = публичная

    @Column(name = "name_2")
    private String name2;

    private String comment;

    @Column(name = "date_corr")
    private LocalDateTime dateCorr;

    @ManyToOne
    @JoinColumn(name = "user_corr")
    private User userCorr;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles = new HashSet<>();
}
