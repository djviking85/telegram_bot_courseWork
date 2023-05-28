package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    //    @Autowired
    private TelegramBot telegramBot;

    private NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

//    создание старта, и прикрепление паттерна, паттенр проверяется на наличие ошибки

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String msg = update.message().text();
            if (msg.equals("/start")) {
                SendMessage message = new SendMessage(
                        update.message().chat().id(),
                        String.format("Здравствуйте %s, вы зарегистрировались в моей сети. Напишите /Задача, чтоб получить указания или /love, чтоб узнать кто кого любит. Введите задачу в формате dd.MM.yyyy HH:mm Задача (пример: 12.02.2023 10:45 Сходить в магазин.)", update.message().chat().firstName())
                );
                logger.info("Start button has been activated ^)");
                telegramBot.execute(message);
            }

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W\\w+]+)");
            Matcher matcher = pattern.matcher(msg);
            if (matcher.matches()) {
                String dateTimeStr = msg.substring(0, 16);
                String text = msg.substring(17);
                System.out.println(dateTimeStr);
                LocalDateTime dateTime;
                try {
                    dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    System.out.println(text);
                    NotificationTask notificationTask = new NotificationTask(update.message().chat().id(), dateTime, text);
                    notificationTaskRepository.save(notificationTask);
                    logger.info("Напоминание {} успешно сохранено", notificationTask);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

//    ниже решение для себя через кейсы и ключевые слова.

//    public int process(List<Update> updates) {
//        updates.forEach(update -> {
//            logger.info("Processing update: {}", update);
//            switch (update.message().text()) {
//                case "/start":
//                    startMessage(update.message().chat().id(), update.message().chat().firstName());
//                    break;
//                case "love":
//                    loveMessage(update.message().chat().id(), update.message().chat().firstName());
//                    break;
//                case "Задача":
//                    taskMessage1(update.message().chat().id(), update.message().chat().firstName());
//                    break;
//                default:
//                    logger.info("Unexpected");
//
//            }
////            // Process your updates here
//        });
//        return UpdatesListener.CONFIRMED_UPDATES_ALL;
//    }
//
////    выведены методы под ключевые слова
//    private void taskMessage1(long chatId, String userName) {
//        SendMessage message = new SendMessage(chatId, "Пользователь - " + userName + ". Введите задачу в формате dd.MM.yyyy HH:mm Задача (пример: 12.02.2023 10:45 Сходить в магазин.)");
//        SendResponse response = telegramBot.execute((message));
//        if (response.isOk()) {
//            logger.info("был использован метод задачи");
//
//        } else {
//            logger.error("не был использован метод задачи " + response.errorCode());
//        }
//    }
//// создаем метод под старт бота - при нажатии /start срабатывает этот метод
//    private void startMessage(long chatId, String userName) {
//        SendMessage message = new SendMessage(chatId, "Здравствуйте, вы зарегистрировались в моей сети: " + userName + ". Напишите (/Задача), чтоб получить указания или (love), чтоб узнать кто кого любит.  ");
//
//        SendResponse response = telegramBot.execute(message);
//        if (response.isOk()) {
//            logger.info("Start button has been activated ^)");
//        } else {
//            logger.error("Error sending. Code: " + response.errorCode());
//
//        }
//    }
//    private void loveMessage(long chatId, String userName) {
//        SendMessage message = new SendMessage(chatId, userName + " - ты милая ^), и я тебя ... ЛЮБЛЮ:)");
//        SendResponse response = telegramBot.execute((message));
//        if (response.isOk()) {
//            logger.info("был  метод про любовь и ...опу :) - тест версия");
//
//        } else {
//            logger.error("не написано про ..опу " + response.errorCode());
//        }
//    }

}





