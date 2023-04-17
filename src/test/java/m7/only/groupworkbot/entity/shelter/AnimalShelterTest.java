package m7.only.groupworkbot.entity.shelter;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.user.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnimalShelterTest {

    public static final Long CORRECT_ID = 8147483647L;
    public static final String CORRECT_ABOUT = "about";
    public static final AnimalType CORRECT_ANIMAL_TYPE = AnimalType.DOG;
    public static final String CORRECT_CONTACTS = "contacts";
    public static final String CORRECT_ADDRESS = "address";
    public static final String CORRECT_OPENING_HOURS = "opening hours";
    public static final String CORRECT_SECURITY_RULES = "security rules";
    public static final String CORRECT_SAFETY_RULES = "safety rules";
    public static final String CORRECT_ADOPTION_RULES = "adoption rules";
    public static final Set<Endpoint> CORRECT_ENDPOINTS = Set.of(new Endpoint());
    public static final Set<User> CORRECT_USERS = Set.of(new User());


    private AnimalShelter out;

    @Test
    void shouldCreateAnimalShelterWithAllArgs() {
        out = new AnimalShelter(CORRECT_ID,
                CORRECT_ABOUT,
                CORRECT_ANIMAL_TYPE,
                CORRECT_CONTACTS,
                CORRECT_ADDRESS,
                CORRECT_OPENING_HOURS,
                CORRECT_SECURITY_RULES,
                CORRECT_SAFETY_RULES,
                CORRECT_ADOPTION_RULES,
                CORRECT_ENDPOINTS,
                CORRECT_USERS);

        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_ABOUT, out.getAbout());
        assertEquals(CORRECT_ANIMAL_TYPE, out.getAnimalType());
        assertEquals(CORRECT_CONTACTS, out.getContacts());
        assertEquals(CORRECT_ADDRESS, out.getAddress());
        assertEquals(CORRECT_OPENING_HOURS, out.getOpeningHours());
        assertEquals(CORRECT_SECURITY_RULES, out.getSecurityRules());
        assertEquals(CORRECT_SAFETY_RULES, out.getSafetyRules());
        assertEquals(CORRECT_ADOPTION_RULES, out.getAdoptionRules());
        assertEquals(CORRECT_ENDPOINTS, out.getEndpoints());
        assertEquals(CORRECT_USERS, out.getUsers());
    }

    @Test
    void shouldCreateAnimalShelterWithAllArgsExcludeIdAndEndpointsAndUsers() {
        out = new AnimalShelter(CORRECT_ABOUT,
                CORRECT_ANIMAL_TYPE,
                CORRECT_CONTACTS,
                CORRECT_ADDRESS,
                CORRECT_OPENING_HOURS,
                CORRECT_SECURITY_RULES,
                CORRECT_SAFETY_RULES,
                CORRECT_ADOPTION_RULES);

        assertNull(out.getId());
        assertEquals(CORRECT_ABOUT, out.getAbout());
        assertEquals(CORRECT_ANIMAL_TYPE, out.getAnimalType());
        assertEquals(CORRECT_CONTACTS, out.getContacts());
        assertEquals(CORRECT_ADDRESS, out.getAddress());
        assertEquals(CORRECT_OPENING_HOURS, out.getOpeningHours());
        assertEquals(CORRECT_SECURITY_RULES, out.getSecurityRules());
        assertEquals(CORRECT_SAFETY_RULES, out.getSafetyRules());
        assertEquals(CORRECT_ADOPTION_RULES, out.getAdoptionRules());
        assertNull(out.getEndpoints());
        assertNull(out.getUsers());
    }

    @Test
    void shouldSetAndGetId() {
        out = new AnimalShelter();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void shouldSetAndGetAbout() {
        out = new AnimalShelter();
        out.setAbout(CORRECT_ABOUT);
        assertEquals(CORRECT_ABOUT, out.getAbout());
    }

    @Test
    void shouldSetAndGetType() {
        out = new AnimalShelter();
        out.setAnimalType(CORRECT_ANIMAL_TYPE);
        assertEquals(CORRECT_ANIMAL_TYPE, out.getAnimalType());
    }

    @Test
    void shouldSetAndGetContacts() {
        out = new AnimalShelter();
        out.setContacts(CORRECT_CONTACTS);
        assertEquals(CORRECT_CONTACTS, out.getContacts());
    }

    @Test
    void shouldSetAndGetAddress() {
        out = new AnimalShelter();
        out.setAddress(CORRECT_ADDRESS);
        assertEquals(CORRECT_ADDRESS, out.getAddress());
    }

    @Test
    void shouldSetAndGetOpeningHours() {
        out = new AnimalShelter();
        out.setOpeningHours(CORRECT_OPENING_HOURS);
        assertEquals(CORRECT_OPENING_HOURS, out.getOpeningHours());
    }

    @Test
    void shouldSetAndGetSecurityRules() {
        out = new AnimalShelter();
        out.setSecurityRules(CORRECT_SECURITY_RULES);
        assertEquals(CORRECT_SECURITY_RULES, out.getSecurityRules());
    }

    @Test
    void shouldSetAndGetSafetyRules() {
        out = new AnimalShelter();
        out.setSafetyRules(CORRECT_SAFETY_RULES);
        assertEquals(CORRECT_SAFETY_RULES, out.getSafetyRules());
    }

    @Test
    void shouldSetAndGetAdoptionRules() {
        out = new AnimalShelter();
        out.setAdoptionRules(CORRECT_ADOPTION_RULES);
        assertEquals(CORRECT_ADOPTION_RULES, out.getAdoptionRules());
    }

    @Test
    void shouldSetAndGetEndpoints() {
        out = new AnimalShelter();
        out.setEndpoints(CORRECT_ENDPOINTS);
        assertEquals(CORRECT_ENDPOINTS, out.getEndpoints());
    }

    @Test
    void shouldSetAndGetUsers() {
        out = new AnimalShelter();
        out.setUsers(CORRECT_USERS);
        assertEquals(CORRECT_USERS, out.getUsers());
    }
}