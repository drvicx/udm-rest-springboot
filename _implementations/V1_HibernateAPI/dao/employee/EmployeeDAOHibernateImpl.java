package ru.nve.springboot.examples.demoApp1.dao.employee;

import ru.nve.springboot.examples.demoApp1.entity.employee.Employee;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


/**
 *=DAO Implementation (Hibernate)
 */
@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    // define field for entitymanager
    //private final EntityManager entityManager;
    private EntityManager entityManager;

    // set up field injection (is not recommended)
    //@Autowired
    //EntityManager theEntityManager;

    // set up constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    // findAll() Method Implementation
    //--get all records
    @Override
    @Transactional
    public List<Employee> findAll() {

        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //=Use native Hibernate API
        // create a query
        Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

        // execute query and get result list
        List<Employee> employees = theQuery.getResultList();

        // return the result
        return employees;
    }

    //--get single record by id
    @Override
    @Transactional
    public Employee findById(int theId) {
        //--get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        //--get the employee
        Employee theEmployee = currentSession.get(Employee.class, theId);
        //--return the employee
        return theEmployee;
    }

    //--save/update/add new record
    @Override
    @Transactional
    public void save(Employee theEmployee) {
        //--get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        //--save employee
        //  *if theId parameter == 0 then do save/insert record, if theId parameter !=0 then do update record
        currentSession.saveOrUpdate(theEmployee);
    }

    //--delete record by id
    @Override
    @Transactional
    public void deleteById(int theId) {
        //--get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        //--delete object with primary key
        //  make query with parameter
        Query theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
        //  bind method parameter to query parameter
        theQuery.setParameter("employeeId", theId);
        // execute query
        theQuery.executeUpdate();
    }

}
