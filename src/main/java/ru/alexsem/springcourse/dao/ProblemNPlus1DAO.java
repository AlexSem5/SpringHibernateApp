package ru.alexsem.springcourse.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.models.Person;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Решение проблемы N+1
 * Используем EntityManager
 */
@Component
public class ProblemNPlus1DAO {
    /**
     * Меняем sessionFactory на EntityManagerFactory
     * так как мы работаем с JPA-репозиторием
     * entityManager object manages a context consisting of entities.
     * context manages entity lifecycle (Transaction-Scoped Persistence Context).
     */
    private final EntityManager entityManager;
    @Autowired
    public ProblemNPlus1DAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    public void testNPlus1() {
        Session session = entityManager.unwrap(Session.class);
//        1 запрос
//        List<Person> people = session.createQuery(
//                "select p from Person p", Person.class).getResultList();
//        N запросов: person.getItems() - будут выполняться N запросов, т.к. ленивая загрузка
//        people.forEach(person -> System.out.println(person.getName() + " " + person.getItems()));

//        Solution.SQL LEFT JOIN -> результирующая объединённая таблица:
//        Для работы HashSet необходимо реализовать Hashcode и Equals:
        Set<Person> people = new HashSet<>(session.createQuery("select p from Person p left join fetch p.items").getResultList()) ;
        people.forEach(person -> System.out.println(person.getName() + " " + person.getItems()));
    
        //HQL example
        // We refer to object Item, not to the table. i.owner is a property, not a table column
//            List<Item> items = session.createQuery("select i from Item i where i.owner.id=:personId", Item.class)
//                                         .setParameter("personId", personOneToMany.getId())
//                                         .getResultList();
        
    }
}
