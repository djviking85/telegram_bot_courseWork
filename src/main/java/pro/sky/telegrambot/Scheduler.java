package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class Scheduler {
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;
    //делаем конструктор для шедулера
    public Scheduler(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }
    //рассылка нотификайий через файнд олл раз в минуту
    @Scheduled(fixedDelay = 60_000L)
    private void sendNotifications() {
        List<NotificationTask> notifications = notificationTaskRepository.findAll();
        notifications.forEach(n -> {
                    SendMessage msg = new SendMessage(n.getChatId(),
                            "Напоминалка: " + n);
                    telegramBot.execute(msg);
                }
        );
    }
//рассылка нотификайий через файнд олл раз в минуту
//    @Scheduled(fixedDelay = 60_000L)
//    private void sendNotifications() {
//        List<NotificationTask> notifications = notificationTaskRepository.findAll();
//        notifications.forEach(n -> {
//                    SendMessage msg = new SendMessage(n.getChatId(),
//                            "Напоминалка: " + n);
//                    telegramBot.execute(msg);
//                }
//        );
//    }
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void checkNotificationByMinutes() {
//        Optional<List<NotificationTask>> optionalNotificationsToSend = telegramBotUpdatesListener
//                .findNotificationsByTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
//        if (optionalNotificationsToSend.isEmpty())
//            return;
//        List<NotificationTask> notificationsToSend = optionalNotificationsToSend.get();
//        for (NotificationTask notificationTask : notificationsToSend) {
//            SendMessage message = new SendMessage(notificationTask.getChatId(), notificationTask.getTask());
//            sendMessage(message);
//            telegramBotUpdatesListener.deleteNotificationById(notificationTask.getId());
//        }
//    }
//    public void sendMessage(SendMessage message) {
//        SendResponse response = telegramBot.execute(message);
//        if (response.isOk()) {
//            logger.info("Message: {} sent", message);
//        } else {
//            logger.error("Error sending. Code: " + response.errorCode());
//        }
//    }
//    @Scheduled(cron = "0 0/1 * * * *")
//    private void checkAndSend() {
//        List<NotificationTask>> optionalNotificationsToSend = notificationTaskService
//                .findNotifications(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
//        if (optionalNotificationsToSend.isEmpty())
//            return;
//        List<NotificationTask> notificationsToSend = optionalNotificationsToSend.get();
//        for (NotificationTask notificationTask : notificationsToSend) {
//            SendMessage message = new SendMessage(notificationTask.getChatId(), notificationTask.getTask());
//            sendMessage(message);
//            notificationTaskService.deleteNotificationById(notificationTask.getId());
//        }
//    }

}
