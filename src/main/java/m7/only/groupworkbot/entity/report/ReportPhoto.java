package m7.only.groupworkbot.entity.report;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс сущности фотографии в ежедневном отчете
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_photos")
public class ReportPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя файла в хранилище
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * Отчет, к которому относится фото
     */
    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Report report;

    public ReportPhoto(String fileName, Report report) {
        this.fileName = fileName;
        this.report = report;
    }
}
