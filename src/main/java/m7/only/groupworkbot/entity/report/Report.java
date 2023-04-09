package m7.only.groupworkbot.entity.report;

import jakarta.persistence.*;
import lombok.*;
import m7.only.groupworkbot.entity.user.User;

import java.util.Set;

/**
 * Класс сущности ежедневного отчета
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Рацион животного
     */
    @Column(name = "ration")
    private String ration;

    /**
     * Общее самочувствие и привыкание к новому месту
     */
    @Column(name = "health")
    private String health;

    /**
     * Изменение в поведении: отказ от старых привычек, приобретение новых
     */
    @Column(name = "behaviour")
    private String behaviour;

    /**
     * Пользователь, которому принадлежит отчет
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
    /**
     * Фотографии животного
     */
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReportPhoto> photos;
}
