package peakSoft.service;

import peakSoft.model.Job;

import java.util.List;

public interface JobService {
    void createJobTable();
    void addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(boolean ascOrDesc);

    Job getJobByEmployeeId(Long employeeId);
    void deleteDescriptionColumn();

}
