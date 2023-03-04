package ru.alexsem.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexsem.springcourse.models.Item;
import ru.alexsem.springcourse.models.Person;

import java.util.List;
/**
 * Репозиторий - для стандартных операций с данными (CRUD, например) -
 * он более высокоуровневый. Работает с сущностями.
 * DAO - для более сложных манипуляций с данными и БД, где нужно
 * вручную писать SQL/HQL, нестандартные запросы.
 */
@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    //    Создаём кастомные запросы:
    List<Item> findByItemName(String itemName);
    
    //person.getItems()
    List<Item> findByOwner(Person owner);
}
