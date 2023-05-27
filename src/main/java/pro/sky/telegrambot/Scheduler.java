package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.util.List;

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
