package m7.only.groupworkbot.entity.user;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    public static final Long CORRECT_ID = 5147483647L;
    public static final String CORRECT_FULL_NAME = "Чннгизов Имгабур Абрамбабаевич";
    public static final String CORRECT_PHONE = "4564551";
    public static final Long CORRECT_CHAT_ID = 6147483647L;

    public static final Boolean CORRECT_STEP_PARENT = true;
    public static final LocalDate CORRECT_TRIAL_START = LocalDate.now();
    public static final Integer CORRECT_TRIAL_PERIOD = 30;
    public static final Boolean CORRECT_TRIAL_EXTENDED = true;
    public static final Boolean CORRECT_TRIAL_EXTENDED_INFORMED = true;
    public static final Boolean CORRECT_TRIAL_FAILURE = true;
    public static final Boolean CORRECT_TRIAL_FAILURE_INFORMED = true;
    public static final Boolean CORRECT_TRIAL_SUCCESS = true;
    public static final Boolean CORRECT_TRIAL_SUCCESS_INFORMED = true;
    public static final Dialog CORRECT_DIALOG = null;
    public static final AnimalShelter CORRECT_ANIMAL_SHELTER = new AnimalShelter();
    public static final Volunteer CORRECT_VOLUNTEER = new Volunteer();
    public static final Set<Report> CORRECT_REPORTS = Set.of(new Report());


    private User out;

    @Test
    void shouldCreatePersonWithAllArgs() {
        out = new User(CORRECT_ID,
                CORRECT_FULL_NAME,
                CORRECT_PHONE,
                CORRECT_CHAT_ID,
                CORRECT_STEP_PARENT,
                CORRECT_TRIAL_START,
                CORRECT_TRIAL_PERIOD,
                CORRECT_TRIAL_EXTENDED,
                CORRECT_TRIAL_EXTENDED_INFORMED,
                CORRECT_TRIAL_FAILURE,
                CORRECT_TRIAL_FAILURE_INFORMED,
                CORRECT_TRIAL_SUCCESS,
                CORRECT_TRIAL_SUCCESS_INFORMED,
                CORRECT_DIALOG,
                CORRECT_ANIMAL_SHELTER,
                CORRECT_VOLUNTEER,
                CORRECT_REPORTS);

        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_FULL_NAME, out.getFullName());
        assertEquals(CORRECT_PHONE, out.getPhone());
        assertEquals(CORRECT_CHAT_ID, out.getChatId());
        assertTrue(out.getStepParent());
        assertEquals(CORRECT_TRIAL_START, out.getTrialStart());
        assertEquals(CORRECT_TRIAL_PERIOD, out.getTrialPeriod());
        assertTrue(out.getTrialExtended());
        assertTrue(out.getTrialExtendedInformed());
        assertTrue(out.getTrialFailure());
        assertTrue(out.getTrialFailureInformed());
        assertTrue(out.getTrialSuccess());
        assertTrue(out.getTrialSuccessInformed());
        assertEquals(CORRECT_DIALOG, out.getDialog());
        assertEquals(CORRECT_ANIMAL_SHELTER, out.getAnimalShelter());
        assertEquals(CORRECT_VOLUNTEER, out.getVolunteer());
        assertEquals(CORRECT_REPORTS, out.getReports());
    }

    @Test
    void shouldCreateWithChatIdAndDefaultValues() {
        out = new User(CORRECT_CHAT_ID);
        assertEquals(CORRECT_CHAT_ID, out.getChatId());
        assertFalse(out.getStepParent());
        assertFalse(out.getTrialExtended());
        assertFalse(out.getTrialExtendedInformed());
        assertFalse(out.getTrialFailure());
        assertFalse(out.getTrialFailureInformed());
        assertFalse(out.getTrialSuccess());
        assertFalse(out.getTrialSuccessInformed());
    }

    @Test
    void setAndGetId() {
        out = new User();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void setAndGetFullName() {
        out = new User();
        out.setFullName(CORRECT_FULL_NAME);
        assertEquals(CORRECT_FULL_NAME, out.getFullName());
    }

    @Test
    void setAndGetPhone() {
        out = new User();
        out.setPhone(CORRECT_PHONE);
        assertEquals(CORRECT_PHONE, out.getPhone());
    }

    @Test
    void setAndGetChatId() {
        out = new User();
        out.setChatId(CORRECT_CHAT_ID);
        assertEquals(CORRECT_CHAT_ID, out.getChatId());
    }

    @Test
    void setAndGetStepParent() {
        out = new User();
        out.setStepParent(CORRECT_STEP_PARENT);
        assertEquals(CORRECT_STEP_PARENT, out.getStepParent());
    }

    @Test
    void setAndGetTrialStart() {
        out = new User();
        out.setTrialStart(CORRECT_TRIAL_START);
        assertEquals(CORRECT_TRIAL_START, out.getTrialStart());
    }

    @Test
    void setAndGetTrialPeriod() {
        out = new User();
        out.setTrialPeriod(CORRECT_TRIAL_PERIOD);
        assertEquals(CORRECT_TRIAL_PERIOD, out.getTrialPeriod());
    }

    @Test
    void setAndGetTrialExtended() {
        out = new User();
        out.setTrialExtended(CORRECT_TRIAL_EXTENDED);
        assertEquals(CORRECT_TRIAL_EXTENDED, out.getTrialExtended());
    }

    @Test
    void setAndGetTrialExtendedInformed() {
        out = new User();
        out.setTrialExtendedInformed(CORRECT_TRIAL_EXTENDED_INFORMED);
        assertEquals(CORRECT_TRIAL_EXTENDED_INFORMED, out.getTrialExtendedInformed());
    }

    @Test
    void setAndGetTrialFailure() {
        out = new User();
        out.setTrialFailure(CORRECT_TRIAL_FAILURE);
        assertEquals(CORRECT_TRIAL_FAILURE, out.getTrialFailure());
    }

    @Test
    void setAndGetTrialFailureInformed() {
        out = new User();
        out.setTrialFailureInformed(CORRECT_TRIAL_FAILURE_INFORMED);
        assertEquals(CORRECT_TRIAL_FAILURE_INFORMED, out.getTrialFailureInformed());
    }

    @Test
    void setAndGetTrialSuccess() {
        out = new User();
        out.setTrialSuccess(CORRECT_TRIAL_SUCCESS);
        assertEquals(CORRECT_TRIAL_SUCCESS, out.getTrialSuccess());
    }

    @Test
    void setAndGetTrialSuccessInformed() {
        out = new User();
        out.setTrialSuccessInformed(CORRECT_TRIAL_SUCCESS_INFORMED);
        assertEquals(CORRECT_TRIAL_SUCCESS_INFORMED, out.getTrialSuccessInformed());
    }

    @Test
    void setAndGetDialog() {
        out = new User();
        out.setDialog(CORRECT_DIALOG);
        assertEquals(CORRECT_DIALOG, out.getDialog());
    }

    @Test
    void setAndGetAnimalShelter() {
        out = new User();
        out.setAnimalShelter(CORRECT_ANIMAL_SHELTER);
        assertEquals(CORRECT_ANIMAL_SHELTER, out.getAnimalShelter());
    }

    @Test
    void setAndGetVolunteer() {
        out = new User();
        out.setVolunteer(CORRECT_VOLUNTEER);
        assertEquals(CORRECT_VOLUNTEER, out.getVolunteer());
    }

    @Test
    void setAndGetReports() {
        out = new User();
        out.setReports(CORRECT_REPORTS);
        assertEquals(CORRECT_REPORTS, out.getReports());
    }
}