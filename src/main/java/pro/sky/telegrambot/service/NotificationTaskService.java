package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Service
//public class NotificationTaskService {
//    private final NotificationTaskRepository notificationTaskRepository;
//
//    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
//        this.notificationTaskRepository = notificationTaskRepository;
//    }


//    public NotificationTask saveTask(NotificationTask task){
//        return notificationTaskRepository.save(task);
//    }
//ТУТ МЫ РАССМОТРИМ НУЭЕН ЛИ НАМ ТАСК СЕРВИС??
//    public Optional<List<NotificationTask>> findNotificationsByTime(LocalDateTime localDateTime) {
//        return notificationTaskRepository.findAllBySendDateTime(localDateTime);
//    }
//
//
//    public void deleteNotificationById(Long id){
//        notificationTaskRepository.deleteById(id);
//    }
//}
