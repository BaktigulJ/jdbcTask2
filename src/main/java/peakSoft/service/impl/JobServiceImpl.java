package peakSoft.service.impl;

import peakSoft.dao.impl.JobDaoImpl;
import peakSoft.model.Job;
import peakSoft.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    private final JobDaoImpl jobDaoImpl= new JobDaoImpl();
    @Override
    public void createJobTable() {
        jobDaoImpl.createJobTable();
    }

    @Override
    public void addJob(Job job) {
jobDaoImpl.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDaoImpl.getJobById(jobId);
    }


    @Override
    public List<Job> sortByExperience(boolean ascOrDesc) {
        return jobDaoImpl.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDaoImpl.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
jobDaoImpl.deleteDescriptionColumn();
    }
}
