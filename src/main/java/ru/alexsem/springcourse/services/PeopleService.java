package ru.alexsem.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.models.Person;
import ru.alexsem.springcourse.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Любая работа с данными осуществляется в сервисном слое.
 * В сервисе должна быть бизнес-логика(здесь её нет). Например,
 * вызываются методы из разных репозиториев (внедрены несколько репозиториев)
 * и данные обрабатываются.
 * (транзакции создаются в сервисе)
 */


@Service
//Берёт на себя работу с транзакциями:
@Transactional(readOnly = true)
public class PeopleService {
    
    private final PeopleRepository peopleRepository;
    
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    
    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }
    
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
//        Обновит значения у существующего человека (по id найдёт его):
        peopleRepository.save(updatedPerson);
    }
    
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
    
    public void test() {
        System.out.println("Testing here with debug. Inside Hibernate Transaction");
    }
}
