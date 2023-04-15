package m7.only.groupworkbot.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import m7.only.groupworkbot.services.BotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramBotUpdatesListenerTest {
    public static final String CORRECT_CHAT_ID = "17";

    @Mock
    private BotService botServiceMock;

    @InjectMocks
    private TelegramBotUpdatesListener out;

    private Update getUpdate(String text) throws URISyntaxException, IOException {
        String JSON = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("update.json").toURI()));
        String json = JSON.replaceAll("%text%", text);
        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID);
        return BotUtils.fromJson(json, Update.class);
    }

    @Test
    void shouldReturnConfirmedUpdatesAndCallProcess() throws URISyntaxException, IOException {
        List<Update> updateList = List.of(
                getUpdate("text1"),
                getUpdate("text2"),
                getUpdate("text3")
        );
        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, out.process(updateList));
        verify(botServiceMock, times(updateList.size())).process(any());
    }
}