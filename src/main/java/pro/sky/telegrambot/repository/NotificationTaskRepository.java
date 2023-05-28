package pro.sky.telegrambot.repository;

import com.pengrad.telegrambot.model.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    List<NotificationTask> findByDateTime(LocalDateTime now);
}

