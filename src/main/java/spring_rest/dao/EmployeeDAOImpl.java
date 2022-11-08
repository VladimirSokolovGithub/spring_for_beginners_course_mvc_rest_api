package spring_rest.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring_rest.entity.Employee;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

    @Autowired
    private SessionFactory sessionFactory;

    //метод для получения всех работников из БД
    @Override
    public List<Employee> getAllEmployees() {
        Session session = sessionFactory.getCurrentSession(); //получим сессию из нашей sessionFactory

        //Далее приведены два варианта получения списка работников из БД

//        List<Employee> allEmployees = session.createQuery("from Employee"
//                        , Employee.class).getResultList(); //получаем список работников из БД

        //Вышеуказанный statement по получению работников можно разделить на две части.
        //Сначала мы создаем запрос Query в котором работаем с Employee. После чего можем
        //выполнить этот Query.
        Query<Employee> query = session.createQuery("from Employee", Employee.class);
        List<Employee> allEmployees = query.getResultList();

        return allEmployees;
    }


    @Override
    public void saveEmployee(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(employee);
    }


    @Override
    public Employee getEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        return employee;
    }


    @Override
    public void deleteEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("delete from Employee  " +
                "where id =:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }
}
