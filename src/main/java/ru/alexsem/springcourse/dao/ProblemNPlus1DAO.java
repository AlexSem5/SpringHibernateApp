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
     *
     * We create an EntityManagerFactory based on a PersistenceUnit (xml-file)
     * then we use emf to create em. Each em manages a context. Context is used by
     * the JPA implementation to manage  entity instances and their lifecycle
     * (Transaction-Scoped Persistence Context).
     *
     * entityManager object manages a context consisting of entities.
     * context manages entity lifecycle (Transaction-Scoped Persistence Context).
     */
    
    //EntityManagerFactory should be there instead
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

//        Join fetch tells the persistence provider to not only join the 2 database tables
//        within the query but to also initialize (подгрузить связанные сущности)
//        the association on the returned entity.

//        Difference between fetch and without fetch:
//        https://stackoverflow.com/questions/17431312/what-is-the-difference-between-join-and-join-fetch-when-using-jpa-and-hibernate/59468551#59468551

       /**
        Returned posts List contains two references of the same Person entity object. This is
        because the JOIN duplicates the parent record for every child row that’s going to be fetched.
 
        https://vladmihalcea.com/jpql-distinct-jpa-hibernate/
        https://thorben-janssen.com/hibernate-tips-apply-distinct-to-jpql-but-not-sql-query/
        */
        List<Person> people = session.createQuery("""
            select distinct p from Person p left join fetch p.items
            """, Person.class)
                .setHint("hibernate.query.passDistinctThrough", false)
                                     .getResultList();
        people.forEach(person -> System.out.println(person.getName() + " " + person.getItems()));
    
 
        //HQL example
        // We refer to object Item, not to the table. i.owner is a property, not a table column
//            List<Item> items = session.createQuery("select i from Item i where i.owner.id=:personId", Item.class)
//                                         .setParameter("personId", personOneToMany.getId())
//                                         .getResultList();
    }
}
