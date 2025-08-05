package backend.service.impl;

import backend.dto.JobDTO;
import backend.dto.PageResponse;
import backend.entity.Job;
import backend.exception.ResourceFoundException;
import backend.exception.ResourceNotFoundException;
import backend.repository.JobRepository;
import backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveJob(JobDTO jobDTO) {
//        jobRepository.save(modelMapper.map(jobDTO, Job.class));
//        if (jobRepository.existsById(jobDTO.getId())){
//            throw new ResourceFoundException("Job already exit");
//        }

        if (jobDTO.getId() != null && jobRepository.existsById(jobDTO.getId())) {
            throw new ResourceFoundException("Job already exists");
        }
        jobRepository.save(modelMapper.map(jobDTO, Job.class));
    }

    @Override
    public void updateJob(JobDTO jobDTO) {
//        if (jobRepository.existsById(jobDTO.getId())) {
//            jobRepository.save(modelMapper.map(jobDTO, Job.class));
//        }else {
//            throw new ResourceFoundException("Job not found");
//        }

        if (jobDTO.getId() == null) {
            throw new IllegalArgumentException("Job id must not be null for update");
        }
        if (jobRepository.existsById(jobDTO.getId())) {
            jobRepository.save(modelMapper.map(jobDTO, Job.class));
        } else {
            throw new ResourceNotFoundException("Job not found");
        }
    }

    @Override
    public List<JobDTO> loadAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No Job Found");
        }
        return jobs.stream().map(job -> modelMapper.map(job, JobDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void changeStatus(String id) {
//        if (jobRepository.existsById(Integer.valueOf(id))){
//            throw new IllegalArgumentException("JobDTO can't empty");
//        }

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Job id must not be null or empty");
        }
        int jobId;
        try {
            jobId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid job id format");
        }
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Job not found");
        }
        Job job = jobRepository.findById(jobId).get();
        // Change status example: toggle between ACTIVE and INACTIVE
        if ("ACTIVE".equalsIgnoreCase(job.getStatus())) {
            job.setStatus("INACTIVE");
        } else {
            job.setStatus("ACTIVE");
        }
        jobRepository.save(job);
    }

    @Override
    public List<JobDTO> getAllJobsByKeyword(String keyword) {
//        if (keyword == null){
//            throw new ResourceNotFoundException("Job id can't null");
//        }
//        List<Job> jobs = jobRepository.findJobByJobTitleContainingIgnoreCase(keyword);
//        if (jobs.isEmpty()){
//            throw new IllegalArgumentException("No Job Found");
//        }
//        return modelMapper.map(jobs, new TypeToken<List<PageResponse>>() {}.getType());

        if (keyword == null) {
            throw new ResourceNotFoundException("Keyword can't be null");
        }
        List<Job> jobs = jobRepository.findJobByJobTitleContainingIgnoreCase(keyword);
        if (jobs.isEmpty()) {
            throw new IllegalArgumentException("No Job Found");
        }
        return modelMapper.map(jobs, new TypeToken<List<JobDTO>>() {}.getType());
    }

    @Override
    public Page<PageResponse> loadAllJobsPaginated(Pageable pageable) {
        Page<Job> jobPage = jobRepository.findAll(pageable);
        return jobPage.map(job -> modelMapper.map(job, PageResponse.class));
    }

    @Override
    public Page<PageResponse> getAllJobsByKeywordPaginated(String keyword, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findJobByJobTitleContainingIgnoreCasePaginated(keyword, pageable);
        return jobPage.map(job -> modelMapper.map(job, PageResponse.class));
    }
}
