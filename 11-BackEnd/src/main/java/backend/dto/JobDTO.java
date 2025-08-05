package backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobDTO {
    private Integer id;

    @NotBlank(message = "company name is mandetory")
//    @Pattern(regexp = "^[a-zA-Z]+$",message = "company name should contain only alphabets")
    private String company;

//    @Size(min = 13,max = 100, message = "job des must be at least 10 character long")
    private String jobdescription;

    @NotBlank(message = "job title is mandatory")
    private String jobtitle;

    private String location;
    private String status;

    @NotNull(message = "type is required")
    private String type;

}
