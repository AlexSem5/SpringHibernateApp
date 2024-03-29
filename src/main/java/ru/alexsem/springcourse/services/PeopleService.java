package ru.alexsem.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.dao.ProblemNPlus1DAO;
import ru.alexsem.springcourse.models.Mood;
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
 * В сервисе можно внедрять и DAO, и репозитории.
 * (транзакции создаются в сервисе)
 */


@Service
//Берёт на себя работу с транзакциями:
@Transactional(readOnly = true)
public class PeopleService {
    
    private final PeopleRepository peopleRepository;
    private final ProblemNPlus1DAO problemNPlus1DAO;
    
    @Autowired
    public PeopleService(PeopleRepository peopleRepository, ProblemNPlus1DAO problemNPlus1DAO) {
        this.peopleRepository = peopleRepository;
        this.problemNPlus1DAO = problemNPlus1DAO;
    }
    
    public List<Person> findAll() {
        problemNPlus1DAO.testNPlus1();
        return peopleRepository.findAll();
    }
    
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    
    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        person.setMood(Mood.CALM);
        peopleRepository.save(person);
    }
    
    /**
     * В репозитории есть соглашение, что для добавления
     * и обновления сущности используется один и тот же метод save(person)
     * @param id
     * @param updatedPerson
     */
    
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
