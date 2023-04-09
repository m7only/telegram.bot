package m7.only.groupworkbot.services;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledService {
    @Scheduled(cron = "* 30 20 * * *")
    void reportReminder();

    @Scheduled(fixedRateString = "P2D")
    void reportReminderVolunteer();

    @Scheduled(fixedRateString = "P1D")
    void dailyTask();
}
