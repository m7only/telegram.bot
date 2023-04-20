package m7.only.groupworkbot.services.impl;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.report.ReportPhoto;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.entity.shelter.AnimalType;
import m7.only.groupworkbot.entity.user.Dialog;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.listener.TelegramBotUpdatesListenerTest;
import m7.only.groupworkbot.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BotServiceImplTest {
    private static final String CORRECT_PRAY_TEXT = "Зову волонтера...";
    private static final String CORRECT_PRAY_TEXT_WITHOUT_CONTACTS = "Необходимо оставить контактные данные для того, что бы волонтер смог с вами связаться";
    private static final String CORRECT_PHONE_NUMBER = "4648964";
    private static final Volunteer CORRECT_VOLUNTEER = new Volunteer();
    private static final String CORRECT_FRONT_MENU_TEXT_ENDPOINT = "/security_CAT";
    private static final String CORRECT_FRONT_MENU_ENDPOINT_TITlE = "title";
    private static final String CORRECT_FRONT_MENU_ENDPOINT_CONTENT = "content";
    private static final LocalDateTime CORRECT_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final String CORRECT_DOCUMENT_FILE_ID = "123";
    private static final String[] CORRECT_PHOTO_FILE_ID = {"100", "200", "300", "400"};
    private static final String CORRECT_MIME_TYPE = "image/jpeg";
    private static final Long CORRECT_CHAT_ID = 17L;
    private static final User CORRECT_USER = new User(CORRECT_CHAT_ID);
    private static final Report CORRECT_REPORT_FULL = new Report(
            1L,
            "report text",
            CORRECT_LOCAL_DATE_TIME,
            CORRECT_USER,
            Set.of(new ReportPhoto())
    );
    private static final String CORRECT_FRONT_MENU_ENDPOINT_WITH_PARENT = "/security_DOG";
    private static final String JSON;
    // ----- FEEDBACK CONSTANT -----
    private static final String CORRECT_GREETINGS_TEXT = "Здравствуйте! Я бот приюта для животных. Выберите, пожалуйста, интересующее животное.";
    private static final String UNSUPPORTED_ENDPOINT = "Команда не распознана";
    private static final String CORRECT_UNSUPPORTED_IMAGE_FORMAT = "К сожалению, я не поддерживаю такой формат.";
    private static final String CORRECT_REPORT_PHOTO_SUCCESS = "Фотография загружена.";
    private static final String CORRECT_REPORT_TEXT_SUCCESS = "Описание состояния животного принято.";
    private static final String CORRECT_REPORT_REMIND_NEED_TEXT = "Напоминаю, что помимо фотографии необходимо рассказать о состоянии животного. В отчете за сегодня она отсутствует, пожалуйста, отправьте фотографию.";
    private static final String CORRECT_REPORT_REMIND_NEED_PHOTO = "Напоминаю, что помимо описания состояния животного нужно отправить его фотографию. В отчете за сегодня оно отсутствует, пожалуйста, отправьте описание.";
    private static final String CORRECT_REPORT_FULL_SUCCESS = "Отчет за сегодня полностью сформирован.";
    private static final String CORRECT_GET_CONTACTS_GREETINGS = "Сейчас вы можете оставить или изменить свои контактные данные. Введите свои ФИО.";
    private static final String CORRECT_GET_CONTACTS_PHONE = "Введите номер своего телефона.";
    private static final String CORRECT_GET_CONTACTS_SUCCESS = "Контактные данные сохранены.";
    private static final String CORRECT_REPORT_INFO = "Для отправки отчета просто пришлите нам фотографию, в подписи которой опишите состояние животного: его рацион, самочувствие, как привыкает к новому месту, а так же приобретенные новые привычки. Фото и описание обязательны. Если вы отправили фотографию, но забыли описать состояние животного, ничего страшного: можете прислать еще одну фотографию или перед текстом сообщения поставьте команду \"/report \", например: \"/report Животное чувствует себя ...\"";
    // ----- FEEDBACK CONSTANT -----
    private static final String CORRECT_ENDPOINT_TEXT_START = "/start";
    private static final String INCORRECT_ENDPOINT_FRONT_END_TEXT = "null";
    private static final String CORRECT_ENDPOINT_TEXT_MAIN_MENU = "/mainMenu_CAT";
    private static final String INCORRECT_ENDPOINT_TEXT_MAIN_MENU = "/mainMenu_ASDFASDD";
    private static final String CORRECT_ENDPOINT_TEXT_PRAY = "/pray";
    private static final String CORRECT_ENDPOINT_TEXT_GET_CONTACTS = "/getContacts";
    private static final String CORRECT_ENDPOINT_TEXT_REPORT = "/report report text";
    private static final String CORRECT_ENDPOINT_TEXT_REPORT_INFO = "/reportInfo";
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
    private static final Endpoint CORRECT_FRONT_ENDPOINT = new Endpoint(
            CORRECT_FRONT_MENU_TEXT_ENDPOINT,
            CORRECT_FRONT_MENU_ENDPOINT_TITlE,
            CORRECT_FRONT_MENU_ENDPOINT_CONTENT,
            CORRECT_ANIMAL_SHELTER,
            null);
    private static final Endpoint CORRECT_FRONT_ENDPOINT_WITH_PARENT = new Endpoint(
            CORRECT_FRONT_MENU_ENDPOINT_WITH_PARENT,
            CORRECT_FRONT_MENU_ENDPOINT_TITlE,
            CORRECT_FRONT_MENU_ENDPOINT_CONTENT,
            CORRECT_ANIMAL_SHELTER,
            CORRECT_FRONT_ENDPOINT
    );
    private static final List<Endpoint> ENDPOINTS = List.of(
            CORRECT_FRONT_ENDPOINT,
            CORRECT_FRONT_ENDPOINT_WITH_PARENT
    );

    static {
        try {
            JSON = Files.readString(
                    Path.of(TelegramBotUpdatesListenerTest.class.getResource("update.json").toURI())
            );
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    private TelegramBot telegramBotMock;

    @Mock
    private EndpointService endpointServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private AnimalShelterService animalShelterServiceMock;

    @Mock
    private ReportService reportServiceMock;

    @Mock
    private VolunteerService volunteerServiceMock;

    @InjectMocks
    private BotServiceImpl out;

    private Update getUpdate(String text) {
        String json = JSON.replaceAll("%text%", text);
        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID.toString());
        return BotUtils.fromJson(json, Update.class);
    }

    @Test
    public void shouldCallExecuteEndpointStartByCallbackData() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("updateByCallbackQuery.json").toURI()));
        json = json.replaceAll("%callbackQueryId%", CORRECT_CHAT_ID.toString());
        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID.toString());
        json = json.replaceAll("%callbackData%", CORRECT_ENDPOINT_TEXT_START);
        Update update = BotUtils.fromJson(json, Update.class);

        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(null);
        when(userServiceMock.add(any())).thenReturn(CORRECT_USER);
        when(animalShelterServiceMock.findAllShelters()).thenReturn(List.of(CORRECT_ANIMAL_SHELTER));

        out.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GREETINGS_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldSendUnsupportedEndpoint() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("updateWithAudio.json").toURI()));
        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID.toString());
        json = json.replaceAll("\"%audioFileId%\"", "null");
        Update update = BotUtils.fromJson(json, Update.class);

        out.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(UNSUPPORTED_ENDPOINT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallShowFrontEndAndMenuAndSendUnsupportedEndpoint() {
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);

        out.process(getUpdate(INCORRECT_ENDPOINT_FRONT_END_TEXT));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(UNSUPPORTED_ENDPOINT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointStart() {
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(null);
        when(userServiceMock.add(any())).thenReturn(CORRECT_USER);
        when(animalShelterServiceMock.findAllShelters()).thenReturn(List.of(CORRECT_ANIMAL_SHELTER));

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_START));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GREETINGS_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointMainMenu() {
        CORRECT_FRONT_ENDPOINT.getChild().addAll(
                List.of(
                        new Endpoint("1", null, null, null, null),
                        new Endpoint("2", null, null, null, null),
                        new Endpoint("3", null, null, null, null)
                ));

        when(endpointServiceMock.findEndpointByEndpointText(any())).thenReturn(CORRECT_FRONT_ENDPOINT);

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_MAIN_MENU));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_FRONT_MENU_ENDPOINT_CONTENT, capturedSendMessage.getParameters().get("text"));

        CORRECT_FRONT_ENDPOINT.setChild(new HashSet<>());
    }

    @Test
    public void shouldCallExecuteEndpointMainMenuAndSendUnsupportedEndpoint() {
        when(endpointServiceMock.findEndpointByEndpointText(any())).thenReturn(null);

        out.process(getUpdate(INCORRECT_ENDPOINT_TEXT_MAIN_MENU));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(UNSUPPORTED_ENDPOINT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallSendResponseForFrontMenuWithOneButton() {
        CORRECT_FRONT_ENDPOINT.getChild().add(new Endpoint());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(null));

        when(endpointServiceMock.findAllEndpoints()).thenReturn(ENDPOINTS);
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(new User());

        out.process(getUpdate(CORRECT_FRONT_MENU_TEXT_ENDPOINT));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_FRONT_MENU_ENDPOINT_CONTENT, capturedSendMessage.getParameters().get("text"));
        assertEquals(inlineKeyboardMarkup, capturedSendMessage.getParameters().get("reply_markup"));

        CORRECT_FRONT_ENDPOINT.setChild(new HashSet<>());
    }

    @Test
    public void shouldCallSendResponseForFrontMenuWithParentAndFewButtons() {
        CORRECT_FRONT_ENDPOINT_WITH_PARENT.getChild().addAll(
                List.of(
                        new Endpoint("1", null, null, null, null),
                        new Endpoint("2", null, null, null, null),
                        new Endpoint("3", null, null, null, null)
                ));

        when(endpointServiceMock.findAllEndpoints()).thenReturn(ENDPOINTS);
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);

        out.process(getUpdate(CORRECT_FRONT_MENU_ENDPOINT_WITH_PARENT));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_FRONT_MENU_ENDPOINT_CONTENT, capturedSendMessage.getParameters().get("text"));

        CORRECT_FRONT_ENDPOINT_WITH_PARENT.setChild(new HashSet<>());
    }

    @Test
    public void shouldCallExecuteEndpointPrayWithoutContacts() {
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_PRAY));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(2)).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_PRAY_TEXT_WITHOUT_CONTACTS, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointPrayWithContacts() {
        CORRECT_USER.setPhone(CORRECT_PHONE_NUMBER);
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(volunteerServiceMock.getAll()).thenReturn(List.of(CORRECT_VOLUNTEER));
        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_PRAY));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(2)).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_PRAY_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointGetContacts() {
        CORRECT_USER.setDialog(null);
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(userServiceMock.add(any())).thenReturn(CORRECT_USER);

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_GET_CONTACTS));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GET_CONTACTS_GREETINGS, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointGetContactsGetPhone() {
        CORRECT_USER.setDialog(Dialog.GET_CONTACTS_FULL_NAME);

        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(userServiceMock.add(any())).thenReturn(CORRECT_USER);

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_GET_CONTACTS));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GET_CONTACTS_PHONE, capturedSendMessage.getParameters().get("text"));

        CORRECT_USER.setDialog(null);
    }

    @Test
    public void shouldCallExecuteEndpointGetContactsSuccess() {
        CORRECT_USER.setDialog(Dialog.GET_CONTACTS_PHONE);

        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(userServiceMock.add(any())).thenReturn(CORRECT_USER);

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_GET_CONTACTS));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(2)).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getAllValues().get(0);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GET_CONTACTS_SUCCESS, capturedSendMessage.getParameters().get("text"));

        CORRECT_USER.setDialog(null);
    }

    private Update returnUpdateWithDocumentAndPhoto() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest
                        .class
                        .getResource("updateWithPhotoAndDocument.json").toURI())
        );

        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID.toString());
        json = json.replaceAll("%documentFileId%", CORRECT_DOCUMENT_FILE_ID);
        json = json.replaceAll("%mimeType%", CORRECT_MIME_TYPE);
        for (int i = 0; i < CORRECT_PHOTO_FILE_ID.length; i++) {
            json = json.replaceAll("%photo" + i + "_fileId%", CORRECT_PHOTO_FILE_ID[i]);
        }
        return BotUtils.fromJson(json, Update.class);
    }

    private Update returnUpdateWithDocumentAndPhotoWithoutFileId() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest
                        .class
                        .getResource("updateWithPhotoAndDocument.json").toURI())
        );

        json = json.replaceAll("%chatId%", CORRECT_CHAT_ID.toString());
        json = json.replaceAll("\"%documentFileId%\"", "null");
        json = json.replaceAll("%mimeType%", CORRECT_MIME_TYPE);
        for (int i = 0; i < CORRECT_PHOTO_FILE_ID.length; i++) {
            json = json.replaceAll("\"%photo" + i + "_fileId%\"", "null");
        }
        return BotUtils.fromJson(json, Update.class);
    }

    @Test
    public void shouldCallExecuteEndpointReportByPhotoWithoutReportText() throws URISyntaxException, IOException {
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(reportServiceMock.saveReport(any(), any(), any())).thenReturn(new Report());

        out.process(returnUpdateWithDocumentAndPhoto());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(2)).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getAllValues().get(0);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_PHOTO_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(1);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_REMIND_NEED_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointReportByPhotoFullSuccess() throws URISyntaxException, IOException {
        when(userServiceMock.findUserByChatIdOrCreateNew(any())).thenReturn(CORRECT_USER);
        when(reportServiceMock.saveReport(any(), any(), any())).thenReturn(CORRECT_REPORT_FULL);

        out.process(returnUpdateWithDocumentAndPhoto());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(4)).execute(argumentCaptor.capture());

        SendMessage capturedSendMessage = argumentCaptor.getAllValues().get(0);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_PHOTO_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(1);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_TEXT_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(2);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_FULL_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(3);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GREETINGS_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointReportByPhotoWithoutFileId() throws URISyntaxException, IOException {
        Update update = returnUpdateWithDocumentAndPhotoWithoutFileId();
        out.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_UNSUPPORTED_IMAGE_FORMAT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldExecuteEndpointReportByTextSuccess() {
        when(reportServiceMock.saveReport(any(), any(), any())).thenReturn(CORRECT_REPORT_FULL);

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_REPORT));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(4)).execute(argumentCaptor.capture());

        SendMessage capturedSendMessage = argumentCaptor.getAllValues().get(0);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_TEXT_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(1);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_PHOTO_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(2);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_FULL_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(3);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_GREETINGS_TEXT, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldExecuteEndpointReportByTextWithoutPhoto() {
        when(reportServiceMock.saveReport(any(), any(), any())).thenReturn(new Report());

        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_REPORT));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock, times(2)).execute(argumentCaptor.capture());

        SendMessage capturedSendMessage = argumentCaptor.getAllValues().get(0);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_TEXT_SUCCESS, capturedSendMessage.getParameters().get("text"));

        capturedSendMessage = argumentCaptor.getAllValues().get(1);
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_REMIND_NEED_PHOTO, capturedSendMessage.getParameters().get("text"));
    }

    @Test
    public void shouldCallExecuteEndpointReportByText() {
        out.process(getUpdate(CORRECT_ENDPOINT_TEXT_REPORT_INFO));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBotMock).execute(argumentCaptor.capture());
        SendMessage capturedSendMessage = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedSendMessage.getParameters().get("chat_id"));
        assertEquals(CORRECT_REPORT_INFO, capturedSendMessage.getParameters().get("text"));
    }
}