package m7.only.groupworkbot.entity.report;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "file_id")
    @NotEmpty
    private String fileId;

    /**
     * Отчет, к которому относится фото
     */
    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    private Report report;

    public ReportPhoto(String fileId, Report report) {
        this.fileId = fileId;
        this.report = report;
    }
}
