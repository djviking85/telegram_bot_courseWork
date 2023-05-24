package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

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
//                case "love":
//                    loveMessage(update.message().chat().id(), update.message().chat().firstName());
//                    break;
                default:
                    logger.info("Unexpected");
            }
            // Process your updates here
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    // создаем метод под старт бота - при нажатии /start срабатывает этот метод
    private void startMessage(long chatId, String userName) {
        SendMessage message = new SendMessage(chatId, "Здравствуйте, вы зарегистрировались в моей сети: " + userName + ".");

        SendResponse response = telegramBot.execute(message);
        if(response.isOk()){
            logger.info("Start button has been activated ^)");
        }else{
            logger.error("Error sending. Code: " + response.errorCode());

        }
    }

//    private void loveMessage(long chatId, String userName) {
//        SendMessage message = new SendMessage(chatId, userName + " - ты жопа, и я тебя ...:)");
//        SendResponse response = telegramBot.execute((message));
//        if (response.isOk()) {
//            logger.info("был забрызнут метод про любовь и жопу :) - тест версия");
//
//        } else {
//            logger.error("не написано про попу " +response.errorCode());
//        }
//    }
}
