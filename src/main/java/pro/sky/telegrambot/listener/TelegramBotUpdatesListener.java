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
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    private NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            switch (update.message().text()) {
                case "/start":
                    startMessage(update.message().chat().id(), update.message().chat().firstName());
                    break;
                case "love":
                    loveMessage(update.message().chat().id(), update.message().chat().firstName());
                    break;
                case "Задача":
                    taskMessage1(update.message().chat().id(), update.message().chat().firstName());
                    break;
                default:
                    logger.info("Unexpected");
            }
            // Process your updates here
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    // создаем метод под старт бота - при нажатии /start срабатывает этот метод
    private void startMessage(long chatId, String userName) {
        SendMessage message = new SendMessage(chatId, "Здравствуйте, вы зарегистрировались в моей сети: " + userName + ". Напишите (/Задача), чтоб получить указания или (love), чтоб узнать кто кого любит.  ");

        SendResponse response = telegramBot.execute(message);
        if(response.isOk()){
            logger.info("Start button has been activated ^)");
        }else{
            logger.error("Error sending. Code: " + response.errorCode());

        }
    }
//    @Override
//    private int taskMessage(List<Update> updates) {
//        updates.forEach(update -> {
//            logger.info("текст задания: {}", update);
//            String msg = update.message().text();
//            if (msg.equals("/Задача")) {
//                SendMessage message = new SendMessage(
//                        update.message().chat().id(),
//                        String.format("Пользователь, %s. Введите задачу в формате dd.mm.yyyy hh:mm Задача (пример: 12.02.2023 10:45 Сходить в магазин)", update.message().chat().firstName())
//                );
//                telegramBot.execute(message);
//            }
//        });
//        return UpdatesListener.CONFIRMED_UPDATES_ALL;
//    }
    private void taskMessage1(long chatId, String userName) {
        SendMessage message = new SendMessage(chatId, "Пользователь - " +userName + ". Введите задачу в формате dd.mm.yyyy hh:mm Задача (пример: 12.02.2023 10:45 Сходить в магазин.)");
        SendResponse response = telegramBot.execute((message));
        if (response.isOk()) {
            logger.info("был использован метод задачи");

        } else {
            logger.error("не был использован метод задачи " +response.errorCode());
        }
    }


    private void loveMessage(long chatId, String userName) {
        SendMessage message = new SendMessage(chatId, userName + " - ты жопа ^), и я тебя ...:)");
        SendResponse response = telegramBot.execute((message));
        if (response.isOk()) {
            logger.info("был забрызнут метод про любовь и ...опу :) - тест версия");

        } else {
            logger.error("не написано про ..опу " +response.errorCode());
        }
    }
}
