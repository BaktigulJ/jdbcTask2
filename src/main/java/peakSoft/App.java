package peakSoft;

import peakSoft.dao.impl.EmployeeDaoImpl;
import peakSoft.model.Employee;
import peakSoft.model.Job;
import peakSoft.service.impl.EmployeeServiceImpl;
import peakSoft.service.impl.JobServiceImpl;

import java.util.List;

public class App {

    public static void main( String[] args ) {
JobServiceImpl jobService = new JobServiceImpl();
//jobService.createJobTable();
//jobService.addJob(new Job("java", "programmer", "jfeoie", 4));
//jobService.addJob(new Job("javasctipt", "programmer", "project2", 5));
//jobService.addJob(new Job("java", "programmer", "jfeoie", 3));

       // jobService.getJobByEmployeeId(1L);
        //jobService.getJobById(1L);
       // jobService.deleteDescriptionColumn();

        List<Job> sortedJobs = jobService.sortByExperience((true)); // for ascending order
        System.out.println(sortedJobs);

//
//        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
//        employeeService.createEmployee();
//        employeeService.addEmployee(new Employee("Dina", "Sakeeva", 23, "kna@mail.com", 1));
//        employeeService.addEmployee(new Employee("Kira", "Alieva", 34, "kira@mail.com", 1));
//        employeeService.addEmployee(new Employee("Nina", "Andreeva", 14, "kna@mail.com", 1));

        //employeeService.getAllEmployees();
        //employeeService.getEmployeeById(1L);
       // employeeService.cleanTable();


//employeeService.dropTable();


    }
}
