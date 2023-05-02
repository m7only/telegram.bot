package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.entity.shelter.AnimalType;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.repository.UserRepository;
import m7.only.groupworkbot.services.BotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledServiceImplTest {

    private static final Long CORRECT_CHAT_ID = 17L;
    private static final LocalDate CORRECT_LOCAL_DATE = LocalDate.now();
    private static final AnimalShelter CORRECT_ANIMAL_SHELTER = new AnimalShelter(
            "about",
            AnimalType.CAT,
            "contacts",
            "address",
            "opening hours",
            "security rules",
            "safety rules",
            "adoption rules"
    );
    private static final Volunteer CORRECT_VOLUNTEER = new Volunteer();

    private static final Report CORRECT_EXPIRED_REPORT;
    static {
        CORRECT_EXPIRED_REPORT = new Report();
        CORRECT_EXPIRED_REPORT.setReportDate(CORRECT_LOCAL_DATE.atStartOfDay().minusDays(3));
    }


    /**
     * Пользователь с отчетами более двух дней назад
     */
    private static final User USER_WITH_EXPIRED_REPORTS = new User(
            7L,
            "full name",
            "phone number",
            CORRECT_CHAT_ID,
            true,
            CORRECT_LOCAL_DATE,
            30,
            false,
            false,
            false,
            false,
            false,
            false,
            null,
            CORRECT_ANIMAL_SHELTER,
            CORRECT_VOLUNTEER,
            Set.of(CORRECT_EXPIRED_REPORT)
    );

    static {
        CORRECT_EXPIRED_REPORT.setUser(USER_WITH_EXPIRED_REPORTS);
    }

    /**
     * Пользователь для подстановки проверки
     */
    private static final User USER_FOR_TEST_1 = new User(
            25L,
            "Full Name",
            "+7123456",
            CORRECT_CHAT_ID,
            true,
            CORRECT_LOCAL_DATE,
            30,
            true,
            false,
            true,
            false,
            true,
            false,
            null,
            CORRECT_ANIMAL_SHELTER,
            CORRECT_VOLUNTEER,
            Set.of(CORRECT_EXPIRED_REPORT)
    );
    private static final List<User> USERS_LIST = List.of(
            USER_FOR_TEST_1
    );
    /**
     * Пользователь для проверки результата
     */
    private static final User USER_RESULT = new User(
            25L,
            "Full Name",
            "+7123456",
            CORRECT_CHAT_ID,
            true,
            CORRECT_LOCAL_DATE,
            30,
            true,
            true,
            true,
            true,
            true,
            true,
            null,
            CORRECT_ANIMAL_SHELTER,
            CORRECT_VOLUNTEER,
            Set.of(CORRECT_EXPIRED_REPORT));

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private BotService botServiceMock;

    @InjectMocks
    private ScheduledServiceImpl out;

    @Test
    void shouldSendRemindAndReminderVolunteer() {
        when(userRepositoryMock.findUserByStepParentIsTrue()).thenReturn(USERS_LIST);

        out.dailyTask();

        assertTrue(USERS_LIST.contains(USER_RESULT));

        verify(botServiceMock, times(7)).sendResponse(any(), any(), any());
    }
}