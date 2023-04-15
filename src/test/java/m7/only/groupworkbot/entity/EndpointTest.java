package m7.only.groupworkbot.entity;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EndpointTest {
    public static final Long CORRECT_ID = 5145483648L;
    public static final String CORRECT_ENDPOINT_TEXT = "text";
    public static final String CORRECT_TITLE = "title";
    public static final String CORRECT_CONTENT = "content";
    public static final AnimalShelter CORRECT_ANIMAL_SHELTER = new AnimalShelter();
    public static final Endpoint CORRECT_PARENT = new Endpoint();
    public static final Set<Endpoint> CORRECT_CHILD = Set.of(new Endpoint());

    private Endpoint out;

    @Test
    void shouldCreateEndpointWithAllArgs() {
        out = new Endpoint(
                CORRECT_ID,
                CORRECT_ENDPOINT_TEXT,
                CORRECT_TITLE,
                CORRECT_CONTENT,
                CORRECT_ANIMAL_SHELTER,
                CORRECT_PARENT,
                CORRECT_CHILD);

        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_ENDPOINT_TEXT, out.getEndpointText());
        assertEquals(CORRECT_TITLE, out.getTitle());
        assertEquals(CORRECT_CONTENT, out.getContent());
        assertEquals(CORRECT_ANIMAL_SHELTER, out.getAnimalShelter());
        assertEquals(CORRECT_PARENT, out.getParent());
        assertEquals(CORRECT_CHILD, out.getChild());
    }

    @Test
    void shouldCreateEndpointWithAllArgsExcludeIdAndChild() {
        out = new Endpoint(
                CORRECT_ENDPOINT_TEXT,
                CORRECT_TITLE,
                CORRECT_CONTENT,
                CORRECT_ANIMAL_SHELTER,
                CORRECT_PARENT);

        assertNull(out.getId());
        assertEquals(CORRECT_ENDPOINT_TEXT, out.getEndpointText());
        assertEquals(CORRECT_TITLE, out.getTitle());
        assertEquals(CORRECT_CONTENT, out.getContent());
        assertEquals(CORRECT_ANIMAL_SHELTER, out.getAnimalShelter());
        assertEquals(CORRECT_PARENT, out.getParent());
        assertTrue(out.getChild().isEmpty());
    }

    @Test
    void setAndGetId() {
        out = new Endpoint();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void setAndGetEndpointText() {
        out = new Endpoint();
        out.setEndpointText(CORRECT_ENDPOINT_TEXT);
        assertEquals(CORRECT_ENDPOINT_TEXT, out.getEndpointText());
    }

    @Test
    void setAndGetTitle() {
        out = new Endpoint();
        out.setTitle(CORRECT_TITLE);
        assertEquals(CORRECT_TITLE, out.getTitle());
    }

    @Test
    void setAndGetContent() {
        out = new Endpoint();
        out.setContent(CORRECT_CONTENT);
        assertEquals(CORRECT_CONTENT, out.getContent());
    }

    @Test
    void setAndGetAnimalShelter() {
        out = new Endpoint();
        out.setAnimalShelter(CORRECT_ANIMAL_SHELTER);
        assertEquals(CORRECT_ANIMAL_SHELTER, out.getAnimalShelter());
    }

    @Test
    void setAndGetParent() {
        out = new Endpoint();
        out.setParent(CORRECT_PARENT);
        assertEquals(CORRECT_PARENT, out.getParent());
    }

    @Test
    void setAndGetChild() {
        out = new Endpoint();
        out.setChild(CORRECT_CHILD);
        assertEquals(CORRECT_CHILD, out.getChild());
    }
}