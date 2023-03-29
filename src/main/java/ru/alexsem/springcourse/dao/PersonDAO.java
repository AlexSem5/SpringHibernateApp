
package ru.alexsem.springcourse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.models.Person;

import java.util.List;

/**
 * Используем Hibernate
 *
 * Репозиторий - для стандартных операций с данными (CRUD, например) -
 * он более высокоуровневый. Работает с сущностями.
 * DAO - для более сложных манипуляций с данными и БД, где нужно
 * вручную писать SQL/HQL, нестандартные запросы.
 */
@Component
public class PersonDAO {
    /**
     * Меняем sessionFactory на EntityManagerFactory (см ProblemNPlus1DAO)
     */
    private final SessionFactory sessionFactory;
    
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    //    Аннотация сама открывает и закрывает (commit -> session.close) транзакцию
    @Transactional(readOnly = true)
    public List<Person> index() {

//    Сессия для работы с Hibernate
        
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();
//       or session.createQuery("from Person");
        return people;
    }
    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return person;
    }
    
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    
    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);
//       Находимся внутри транзакции. Объект находится в состоянии persistent(managed) -
//        в области persistent context
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }
    
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.remove(person);
//        либо метод delete();
    }
}
