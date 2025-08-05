package backend.repository;

import backend.entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE job SET status = 'Deactivated' WHERE id = ?1", nativeQuery = true)
    void updateStatus(String id);

    // Original method without pagination
    @Query(value = "SELECT * FROM job WHERE job_title LIKE %?1%", nativeQuery = true)
    List<Job> findJobByJobTitleContainingIgnoreCase(String keyword);

    // New method with pagination using Spring Data JPA naming convention
    Page<Job> findByJobTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // Alternative using custom query with pagination
    @Query(value = "SELECT * FROM job WHERE job_title LIKE %?1%",
            countQuery = "SELECT count(*) FROM job WHERE job_title LIKE %?1%",
            nativeQuery = true)
    Page<Job> findJobByJobTitleContainingIgnoreCasePaginated(String keyword, Pageable pageable);

}
