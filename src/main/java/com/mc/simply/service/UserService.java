package com.mc.simply.service;

import com.mc.simply.dao.UserRepository;
import com.mc.simply.messaging.impl.SenderWithException;
import com.mc.simply.messaging.impl.UserSender;
import com.mc.simply.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSender sender;
    private final SenderWithException exceptionSender;
    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }
    public User findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void update(User updateData) {
        User fromDb = this.findById(updateData.getId());
        fromDb.setName(updateData.getName());
        this.save(fromDb);
        sender.send("User update: " + fromDb.getId() + ", " + fromDb.getName());
    }

    public void updateWithSenderExceptionNonTransactional(User updateData) {
        User fromDb = this.findById(updateData.getId());
        fromDb.setName(updateData.getName());
        this.save(fromDb);
        // изменения пишутся, сообщение не отправляется
        exceptionSender.send("User update: " + fromDb.getId() + ", " + fromDb.getName());
    }

    @Transactional
    public void updateWithSenderExceptionTransactional(User updateData) {
        User fromDb = this.findById(updateData.getId());
        fromDb.setName(updateData.getName());
        this.save(fromDb);
        // изменения не пишутся, т.к. ошибка в транзакции
        exceptionSender.send("User update: " + fromDb.getId() + ", " + fromDb.getName());
    }

    @Transactional
    public void updateWithRequiredNewTrSenderExceptionTransactional(User updateData) {
        User fromDb = this.findById(updateData.getId());
        fromDb.setName(updateData.getName());
        this.save(fromDb);
        // запишутся ли изменения? По идее должны, ведь упавшая отправка сообщения требует новую транзакцию
        // но нет, авторы не врали - механизм прокси превращает кейс в не совсем прозрачный, но новая транзакция не открывается, изменения не пишутся
        sendMessageWithExceptionRequiredNewTrct(fromDb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void sendMessageWithExceptionRequiredNewTrct(User user) {
        exceptionSender.send("User update: " + user.getId() + ", " + user.getName());
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
