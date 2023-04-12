package m7.only.groupworkbot.entity.report;

import jakarta.persistence.*;
import lombok.*;
import m7.only.groupworkbot.entity.user.User;

import java.time.LocalDateTime;
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
     * Текст отчета
     */
    @Column(name = "report")
    private String report;

    /**
     * Дата создания отчета
     */
    @Column(name = "report_date")
    private LocalDateTime reportDate;

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

    public Report(User user, LocalDateTime reportDate) {
        this.reportDate = reportDate;
        this.user = user;
    }
}
