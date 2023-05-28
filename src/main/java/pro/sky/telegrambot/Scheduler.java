package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Scheduler {
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public Scheduler(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    private void sendNotificationsByTime() {
        List<NotificationTask> notifications = notificationTaskRepository.findByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        notifications.forEach(n -> {
            SendMessage msg = new SendMessage(n.getChatId(), "Напоминалочка по времени: " + n.getTask());
            telegramBot.execute(msg);
        });
    }
}