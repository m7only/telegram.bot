package m7.only.groupworkbot.entity.user;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VolunteerTest {

    public static final Long CORRECT_ID = 3147483647L;
    public static final String CORRECT_FULL_NAME = "Чннгизов Имгабур Абрамбабаевич";
    public static final String CORRECT_PHONE = "4564551";
    public static final Long CORRECT_CHAT_ID = 5147483647L;
    public static final Set<User> CORRECT_USERS = Set.of(new User());

    private Volunteer out;

    @Test
    void shouldCreateVolunteerWithAllArgs() {
        out = new Volunteer(CORRECT_ID,
                CORRECT_FULL_NAME,
                CORRECT_PHONE,
                CORRECT_CHAT_ID,
                CORRECT_USERS);
        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_FULL_NAME, out.getFullName());
        assertEquals(CORRECT_PHONE, out.getPhone());
        assertEquals(CORRECT_CHAT_ID, out.getChatId());
        assertEquals(CORRECT_USERS, out.getUsers());
    }

    @Test
    void setAndGetId() {
        out = new Volunteer();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void setAndGetFullName() {
        out = new Volunteer();
        out.setFullName(CORRECT_FULL_NAME);
        assertEquals(CORRECT_FULL_NAME, out.getFullName());
    }

    @Test
    void setAndGetPhone() {
        out = new Volunteer();
        out.setPhone(CORRECT_PHONE);
        assertEquals(CORRECT_PHONE, out.getPhone());
    }

    @Test
    void setAndGetChatId() {
        out = new Volunteer();
        out.setChatId(CORRECT_CHAT_ID);
        assertEquals(CORRECT_CHAT_ID, out.getChatId());
    }

    @Test
    void setAndGetUsers() {
        out = new Volunteer();
        out.setUsers(CORRECT_USERS);
        assertEquals(CORRECT_USERS, out.getUsers());
    }
}