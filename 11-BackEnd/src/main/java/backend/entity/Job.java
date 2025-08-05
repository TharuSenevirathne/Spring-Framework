package backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "job_title")
    private String jobTitle;

    private String location;
    private String status;
    private String type;
}
