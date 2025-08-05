package backend.service;

import backend.dto.JobDTO;
import backend.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    void saveJob(JobDTO jobDTO);
    void updateJob(JobDTO jobDTO);
    List<JobDTO> loadAllJobs();
    void changeStatus(String id);
    List<JobDTO> getAllJobsByKeyword(String keyword);

    // New method for pagination
    Page<PageResponse> loadAllJobsPaginated(Pageable pageable);
    // New method for paginated search
    Page<PageResponse> getAllJobsByKeywordPaginated(String keyword, Pageable pageable);
}
