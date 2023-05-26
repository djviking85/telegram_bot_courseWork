package pro.sky.telegrambot.repository;

import com.pengrad.telegrambot.model.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

}
