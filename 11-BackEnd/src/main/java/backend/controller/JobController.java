package backend.controller;


import backend.dto.JobDTO;
import backend.dto.PageResponse;
import backend.service.JobService;
import backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/v1/job")
@RestController
@RequiredArgsConstructor
@Slf4j  //Log liynn gnne
public class JobController {

    //Constructor injection
    @Autowired
    private final JobService jobService;

    @PostMapping("create")
    public ResponseEntity<APIResponse<String>> createJob(@Valid @RequestBody JobDTO jobDTO) {
        log.info(" Info job - created ");
        log.debug(" Debug job - created");
        log.warn(" Error job - created");
        log.error(" Error job - created");
        log.trace(" Trace job - created");

        jobService.saveJob(jobDTO);
        // save the job
        return new ResponseEntity(new APIResponse<>(
                201,
                "Job Created Successfully",
                null
        ), HttpStatus.CREATED);


    }

    @PutMapping("update")
    public ResponseEntity<APIResponse<String>>updateJob(@Valid @RequestBody JobDTO jobDTO) {
        jobService.updateJob(jobDTO);
        return ResponseEntity.ok(new APIResponse<>(
                200,
                "Job Updated Successfully",
                null
        ));
    }

    @GetMapping("loadall")
    public ResponseEntity<APIResponse> loadAllJobs() {
        List<JobDTO> jobDTOList = jobService.loadAllJobs();
        return ResponseEntity.ok(new APIResponse(200,"Success",jobDTOList));
    }

    @PatchMapping("changestatus/{id}")
    public ResponseEntity<APIResponse<String>>  changeStatus(@PathVariable String id) {
        jobService.changeStatus(id);
        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Job Status Changed Successfully",
                        null));
    }

    // New paginated method
    @GetMapping("loadall/paginated")
    public Page<PageResponse> loadAllJobsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return jobService.loadAllJobsPaginated(pageable);
    }

    // Original search method without pagination
    @GetMapping("search/{keyword}")
    public ResponseEntity<APIResponse<List<JobDTO>>> searchJob(@PathVariable("keyword") String keyword) {
        List<JobDTO> jobDTOS = jobService.getAllJobsByKeyword(keyword);
        return ResponseEntity.ok(new APIResponse<>(
                200,
                "Job List Fetched Successfully",
                jobDTOS
        ));
    }

    // New paginated search method
    @GetMapping("search/{keyword}/paginated")
    public Page<PageResponse> searchJobPaginated(
            @PathVariable("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return jobService.getAllJobsByKeywordPaginated(keyword, pageable);
    }

}
