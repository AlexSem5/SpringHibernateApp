package ru.alexsem.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexsem.springcourse.models.Person;
import ru.alexsem.springcourse.services.ItemsService;
import ru.alexsem.springcourse.services.PeopleService;

import javax.validation.Valid;

/**
 *
 * !!!!!НЕОБХОДИМО РЕАЛИЗОВАТЬ PERSONVALIDATOR И ВНЕДРИТЬ В ЭТОТ КЛАСС (СМ ПРОЕКТ Spring_MVCApp1
 * HTTP методы и URL для паттерна REST указаны в CRUD_App1
 * <p>
 * Использование валидации personValidator см в проекте Spring_MVCApp1
 * В контроллере должно быть минимум кода.
 * Можем подгружать разные сервисы.
 */
@Controller
@RequestMapping("/people")
public class PeopleController {
    
    private final PeopleService peopleService;
    private final ItemsService itemsService;
    
    @Autowired
    public PeopleController(PeopleService peopleService, ItemsService itemsService) {
        this.peopleService = peopleService;
        this.itemsService = itemsService;
    }
//    private final PersonDAO personDAO;
//
//    @Autowired
//    public PeopleController(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
    
    /**
     * Получаем все записи с сервера(DB->DAO->Controller->View)
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param model
     * @return
     */
    @GetMapping()
    public String index(Model model) {
        //  Получим всех людей из DAO и передадим на отображение в представление
//        List<Person> people = personDAO.index();
        model.addAttribute("people", peopleService.findAll());
        
//        Эти методы для примера работы с debug:
        itemsService.findByItemName("Airpods");
        itemsService.findByOwner(peopleService.findAll().get(0));
        peopleService.test();
        
        return "people/index";
    }
    
    /**
     * Получаем одну запись с сервера (DB->DAO->Controller->View)
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/show";
    }
    
    /**
     * Метод возвращает с сервера html-форму (Thymeleaf) для создания человека.
     * Метод создаёт Person, добавляет объект в модель и отправляет в Thymeleaf.
     *
     * Далее поля инициализируются данными из формы
     * и передаются в метод create() в POST запрос /people
     *
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param person
     * @return
     */
    
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        //        альтернативный вариант создания объекта Person:
//         model.addAttribute("person", new Person);
        return "people/new";
    }
    
    /**
     * Метод принимает на вход post-запрос по адресу /people,
     * берёт данные из этого post-запроса
     * и добавляет нового человека в базу данных с помощью DAO
     * View->Controller->DAO->DB
     * <p>
     * В модель будет положен новый объект Person со значениями полей из формы.
     * Аннотация @ModelAttribute ДЛЯ POST-ЗАПРОСА сама создаёт объект с вставленными в
     * форму полями и кладёт его в модель.
     * Альтернативный вариант:
     * Данные полей передаются ИЗ HTML-ФОРМЫ в теле Post-request в формате name=Имя
     * по адресу action="/people" и далее извлекаются через @RequestParam и
     * создаётся Person. См на салйдеCRUD_App2.
     *
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param person
     * @param bindingResult
     * @return
     */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }
    
    /**
     * Метод возвращает с сервера html-страницу для редактирования человека.
     * Аннотация @PathVariable("id") позволяет извлечь id из адреса запроса
     * и кладёт в переменную int id. Далее мы внедряем в модель объект класса
     * Person  с уже имеющимися полями.
     *
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    
    /**
     * View->Controller->DAO->DB
     * Метод принимает Patch-запрос
     * Аннотация @ModelAttribute создаёт объект Person
     * с вставленными в форму полями и кладёт в переменную person.
     * Это альтернатива @RequestParam (см на салйдеCRUD_App2).
     *
     * ПЕРЕДАЁМ ДАННЫЕ НА ПРЕДСТАВЛЕНИЕ ЧЕРЕЗ МОДЕЛЬ (КОНТЕЙНЕР НАШЕГО
     * ПРИЛОЖЕНИЯ) В ФОРМАТЕ КЛЮЧ-ЗНАЧЕНИЕ
     *
     * @param person
     * @param bindingResult
     * @param id
     * @return
     */
    
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }
    
    /**
     * View->Controller->DAO->DB
     * Метод принимает Delete-запрос
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
