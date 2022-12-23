package com.example.demo.repo;

import com.example.demo.models.JobApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobApplicationRepository extends CrudRepository<JobApplication, Long> {

    List<JobApplication> findByMark(int mark);

    List<JobApplication> findByCandidate_Id(long id);

    List<JobApplication> findByCandidate_IdAndMark(long id, int mark);

    default List<JobApplication> findByCandidate_IdActive(long id) {
        return this.findByCandidate_IdAndMark(id, 0);
    }

    default List<JobApplication> findActive() {
        return this.findByMark(0);
    }
}