package by.mosquitto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // латиница, уникально

    @Column(name = "type", nullable = false)
    private Short type; // 0 = приватный, 1 = публичный

    @Column(name = "name_2", nullable = false)
    private String name2; // Название

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "date_corr")
    private LocalDateTime dateCorr;

    @ManyToOne
    @JoinColumn(name = "user_corr")
    private User userCorr;

    @Column(nullable = false)
    private boolean isPrivate;

    public Privilege(Long id) {
        this.id = id;
    }
}
