package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.repository.UserRepository;
import m7.only.groupworkbot.services.BotService;
import m7.only.groupworkbot.services.ScheduledService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервисный класс для выполнения запланированных задач
 */
@Service
@EnableScheduling
public class ScheduledServiceImpl implements ScheduledService {

    // ----- FEEDBACK CONSTANT -----
    private static final String DAILY_REMINDER_MESSAGE = "Напоминаем вам о необходимости отправки ежедневного отчета. " +
            "Если вы еще не присылали отчёт, то сейчас самое время";
    private static final String USER_WITHOUT_REPORTS_MESSAGE = "Отсутствуют отчеты за последние два дня";
    private static final String CONGRATULATION_MESSAGE = "Поздравляем с успешным окончанием испытательного срока";
    private static final String SEND_EXTEND_TRIAL_MESSAGE = "Вам назначен дополнительный период";
    private static final String SEND_FAILURE_MESSAGE = "Испытательный срок провален";
    private static final String TRIAL_SUCCESS_MESSAGE = "Закончился испытательный срок";
    // ----- FEEDBACK CONSTANT -----

    private final UserRepository userRepository;
    private final BotService botService;
    private List<User> users = new ArrayList<>();

    public ScheduledServiceImpl(UserRepository userRepository, BotService botService) {
        this.userRepository = userRepository;
        this.botService = botService;
    }

    /**
     * Ежедневно получаем из базы всех усыновителей и производим необходимые проверки в методах
     * {@code sendTrialEndCongratulations}, {@code User.sendExtendTrial}, {@code User.sendFailure}
     */
    @Override
    @Scheduled(cron = "00 30 20 * * *")
    public void dailyTask() {
        users = userRepository.findUserByStepParentIsTrue();
        sendTrialEndCongratulations(users);
        sendExtendTrial(users);
        sendFailure(users);
        requestTrialSuccess(users);
        reportReminder();
        reportReminderVolunteer();
    }

    /**
     * Напоминание усыновителю о необходимости предоставлять ежедневный отчет.
     * Запланировано на 20:30
     */
    @Override
    public void reportReminder() {
        users.stream()
                .peek(e -> {
                    botService.sendResponse(e.getChatId(), DAILY_REMINDER_MESSAGE, null);
                })
                .collect(Collectors.toList());
    }

    /**
     * Каждые два дня метод проверяет всех усыновителей и наличие у них отчетов за последние двое суток.
     * Если отчетов нет, сливаем волонтеру
     */
    @Override
    public void reportReminderVolunteer() {
        List<Report> reports = users
                .stream()
                .flatMap(e -> e.getReports()
                        .stream()
                        .filter(report ->
                                (report.getReportDate().isBefore(LocalDateTime.now().minusDays(2)))))
                .toList();

        reports.forEach(report -> {
            botService.sendResponse(report.getUser().getVolunteer().getChatId(), USER_WITHOUT_REPORTS_MESSAGE + report.getUser(), null);
        });
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
        users.stream()
                .filter(User::getTrialSuccess)
                .peek(e -> {
                    if (!e.getTrialSuccessInformed()) {
                        botService.sendResponse(e.getChatId(), CONGRATULATION_MESSAGE, null);
                        e.setTrialSuccessInformed(true);
                    }
                })
                .collect(Collectors.toList());


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
        users.stream()
                .filter(User::getTrialExtended)
                .peek(e -> {
                    if (!e.getTrialExtendedInformed()) {
                        botService.sendResponse(e.getChatId(), SEND_EXTEND_TRIAL_MESSAGE, null);
                        e.setTrialExtendedInformed(true);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * При ежедневном проходе по всем усыновителям проверяем
     * установлен ли флаг {@code User.trialFailure} о провале испытательного срока
     * и флаг {@code User.trialFailureInformed} информирования об этом.
     * Если флаг провала стоит и усыновитель не проинформирован, то информируем его,
     * отправляем стандартное сообщение с дальнейшими действиями и устанавливаем флаг информирования
     *
     * @param users лист со всеми пользователями
     */
    private void sendFailure(List<User> users) {
        users.stream()
                .filter(User::getTrialFailure)
                .peek(e -> {
                    if (!e.getTrialFailureInformed()) {
                        botService.sendResponse(e.getChatId(), SEND_FAILURE_MESSAGE, null);
                        e.setTrialFailureInformed(true);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * При ежедневном проходе по всем усыновителям проверяем закончился ли испытательный срок.
     * Если испытательный срок вышел, и флаг {@code User.trialSuccess} не установлен,
     * то отправляем запрос волонтеру о необходимости принятия решения по усыновителю
     *
     * @param users лист со всеми пользователями
     */
    private void requestTrialSuccess(List<User> users) {
        users.stream()
                .filter(User::getTrialSuccess)
                .peek(e -> {
                    if (e.getTrialSuccess()) {
                        botService.sendResponse(e.getVolunteer().getChatId(), TRIAL_SUCCESS_MESSAGE + e, null);
                    }
                })
                .collect(Collectors.toList());
    }
}
