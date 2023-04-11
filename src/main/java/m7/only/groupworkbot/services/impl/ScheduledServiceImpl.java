package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.services.ScheduledService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Сервисный класс для выполнения запланированных задач
 */
@Service
@EnableScheduling
public class ScheduledServiceImpl implements ScheduledService {


    /**
     * Напоминание усыновителю о необходимости предоставлять ежедневный отчет.
     * Запланировано на 20:30
     */
    @Override
    @Scheduled(cron = "* 30 20 * * *")
    public void reportReminder() {

    }

    /**
     * Каждые два дня метод проверяет всех усыновителей и наличие у них отчетов за последние двое суток.
     * Если отчетов нет, сливаем волонтеру
     */
    @Override
    @Scheduled(fixedRateString = "P2D")
    public void reportReminderVolunteer() {

    }

    /**
     * Ежедневно получаем из базы всех усыновителей и производим необходимые проверки в методах
     * {@code sendTrialEndCongratulations}, {@code User.sendExtendTrial}, {@code User.sendFailure}
     */
    @Override
    @Scheduled(fixedRateString = "P1D")
    public void dailyTask() {
        List<User> users = new ArrayList<>(); // здесь должен быть что то типа userRepository.findAll

        sendTrialEndCongratulations(users);
        sendExtendTrial(users);
        sendFailure(users);
        requestTrialSuccess(users);
    }


    /**
     * При ежедневном проходе по всем усыновителям проверяем закончился ли
     * испытательный срок по флагу {@code User.trialSuccess}
     * и состояние флага {@code User.trialSuccessInformed} информирования об этом.
     * Если срок вышел, флаг не стоит - отправляем поздравлялку, устанавливаем флаг у пользователя
     *
     * @param users лист со всеми пользователями
     */
    private void sendTrialEndCongratulations(List<User> users) {

    }

    /**
     * При ежедневном проходе по всем усыновителям проверяем назначен ли усыновителю дополнительный
     * испытательный срок {@code User.trialExtended}
     * и установлен ли флаг {@code User.trialExtendedInformed} информирования об этом.
     * Если срок назначен и флаг не стоит, то отправляем усыновителю соответствующее стандартное сообщение
     * и устанавливаем флаг, что проинформировали
     *
     * @param users лист со всеми пользователями
     */
    private void sendExtendTrial(List<User> users) {

    }

    /**
     * При ежедневном проходе по всем усыновителям проверяем
     * установлен ли флаг {@code User.trialFailure} о провале испытательного срока
     * и флаг {@code User.trialFailureInformed} информирования об этом.
     * Если флаг провала стоит и усыновитель не проинформирован, то информируем его,
     * отправляем стандартное сообщение с дальнейшими действиями и устанавливаем флаг информировнаия
     *
     * @param users лист со всеми пользователями
     */
    private void sendFailure(List<User> users) {

    }

    /**
     * При ежедневном проходе по всем усыновителям проверяем закончился ли истытательный срок.
     * Если испытательный срок вышел, и флаг {@code User.trialSuccess} не установлен,
     * то отправляем запрос волонтеру о необходимости принятия решения по усыновителю
     *
     * @param users лист со всеми пользователями
     */
    private void requestTrialSuccess(List<User> users) {

    }
}
