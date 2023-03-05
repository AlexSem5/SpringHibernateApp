package ru.alexsem.springcourse.models;

import javax.persistence.*;
/**
 * Аннотацией @Entity помечаем класс, который связан с бд.
 * Класс с @Entity должен иметь пустой конструктор и поле с аннотацией @Id
 *
 * Сущесвуют определённые требования к классам. Вот самые главные из них:
 *
 * Все классы должны иметь ID для простой идентификации наших объектов в БД и в Hibernate.
 * Это поле класса соединяется с первичным ключём (primary key) таблицы БД.
 * Все классы должны иметь конструктор по умолчанию (пустой).
 * Все поля – классов должны иметь модификатор доступа private,
 * иметь набор getter-ов и setter-ов в стиле JavaBean.
 * Классы не должны содержать бизнес-логику.
 */
@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "item_name")
    private String itemName;
    
    //    Эта сущность является owning side - владеющая сторона (foreign key)
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;
    
    public Item() {
    }
    
    public Item(String itemName, Person owner) {
        this.itemName = itemName;
        this.owner=owner;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner(Person owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "Item{" +
               "id=" + id +
               ", itemName='" + itemName + '\'' +
               '}';
    }
}
