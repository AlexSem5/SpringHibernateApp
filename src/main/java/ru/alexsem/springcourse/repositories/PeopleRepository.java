package ru.alexsem.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexsem.springcourse.models.Person;
/**
 * Репозиторий - для стандартных операций с данными (CRUD, например) -
 * он более высокоуровневый. Работает с сущностями.
 * DAO - для более сложных манипуляций с данными и БД, где нужно
 * вручную писать SQL/HQL, нестандартные запросы.
 */
import java.util.List;

/**
 * Указываем класс Person и тип id (Integer)
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    //    Создаём кастомные запросы:
    List<Person> findByName(String name);
    
    List<Person> findByNameOrderByAge(String name);
    
    List<Person> findByEmail(String email);
    
    List<Person> findByNameStartingWith(String startingWith);
}
