package ru.alexsem.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.models.Item;
import ru.alexsem.springcourse.models.Person;
import ru.alexsem.springcourse.repositories.ItemsRepository;

import java.util.List;

/**
 * Любая работа с данными осуществляется в сервисном слое.
 * В сервисе должна быть бизнес-логика(здесь её нет). Например,
 * вызываются методы из разных репозиториев и данные обрабатываются.
 * (транзакции создаются в сервисе)
 */

@Service
@Transactional(readOnly = true)
public class ItemsService {
    
    private final ItemsRepository itemsRepository;
    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }
    
    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }
    
    public List<Item> findByOwner(Person owner) {
        return itemsRepository.findByOwner(owner);
    }
    
    
}
