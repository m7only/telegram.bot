package m7.only.groupworkbot.services.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.services.AnimalShelterService;
import m7.only.groupworkbot.services.BotService;
import m7.only.groupworkbot.services.EndpointService;
import m7.only.groupworkbot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Класс для обработки эндпоинтов.
 * Эндпоинты с логикой опрделены как константы, private т.к. используются только в этом классе
 * и не предполагается их использование в других классах
 * Энпоинты без логиги (по сути простой фронт с меню) хранятся в базе, их Entity - {@code Endpoint}
 */
@Service
public class BotServiceImpl implements BotService {
    /**
     * Текст привествия пользователя в эндпоинте "/start"
     */
    private static final String GREETINGS_TEXT = "Здравствуйте! Я бот приюта для животных. Выберите, пожалуйста, интересующее животное.";

    // ----- ENDPOINTS CONSTANT -----
    /**
     * Эндпоинт стартового приветствия, выбора приюта
     */
    private static final String ENDPOINT_START = "/start";

    /**
     * Эндпоинт главного меню с выбором этапа
     */
    private static final String ENDPOINT_MAIN_MENU = "/mainMenu";

    /**
     * Эндпоинт для вызова волонтера
     */
    private static final String ENDPOINT_PRAY = "/pray";

    /**
     * Заголовок для энпоинта вызова волонтера,
     * для формирования кнопки, которая используется в нескольких местах
     */
    private static final String ENDPOINT_PRAY_TITLE = "Обратиться к волонтеру";

    /**
     * Эндпоинт для приема контактных данных от пользователя
     */
    private static final String ENDPOINT_GET_CONTACTS = "/getContacts";

    /**
     * Эндпоинт для информирования человека о форме отчета
     */
    private static final String ENDPOINT_REPORT_INFO = "/reportInfo";

    /**
     * Эндпоинт для приема отчета от усыновителя(пользователя)
     */
    private static final String ENDPOINT_REPORT = "/report";

    /**
     * Эндпоинт для выдачи леща усыновителю(пользователю)
     */
    private static final String ENDPOINT_VIOLATION = "/violation";

    /**
     * Список наших энпоинтов, чтоб не приступать к перебору если искомого в нем нет
     */
    private static final Set<String> ENDPOINTS = Set.of(
            ENDPOINT_START,
            ENDPOINT_MAIN_MENU,
            ENDPOINT_PRAY,
            ENDPOINT_GET_CONTACTS,
            ENDPOINT_REPORT_INFO,
            ENDPOINT_REPORT,
            ENDPOINT_VIOLATION
    );
    // ----- ENDPOINTS CONSTANT -----
    /**
     * Количество кнопок в одной строке
     */
    private static final int BUTTONS_IN_ROW = 2;
    private final Logger logger = LoggerFactory.getLogger(BotServiceImpl.class);
    private final TelegramBot telegramBot;
    private final EndpointService endpointService;
    private final UserService userService;
    private final AnimalShelterService animalShelterService;

    public BotServiceImpl(TelegramBot telegramBot, EndpointService endpointService, UserService userService, AnimalShelterService animalShelterService) {
        this.telegramBot = telegramBot;
        this.endpointService = endpointService;
        this.userService = userService;
        this.animalShelterService = animalShelterService;
    }

    /**
     * Главный метод для обработки входящих эндпоинтов. В методе происходит:<br>
     * 1. определение эндпоинта -  он может быть в тексте сообщения {@code update.message()}
     * или в теле {@code update.callbackQuery()}<br>
     * 2. определение идентификатора чата {@code chatId}<br>
     * Проверяем, удалось ли распарсить эндпоинт<br>
     * Далее, если эндпоинт с логикой (т.е. под него существует константа),
     * то отрабатывает метод {@code showSpecificMenu()}
     * если нет, то с помощью метода {@code showFrontMenu(...)}
     * вызываем простое фронт меню, энпоинты которого хранятся в базе
     *
     * @param update полученный от сервера телеграмм объект Update
     */
    @Override
    public void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        Optional<String> optionalCommand;
        Long chatId;
        if (callbackQuery == null) {
            chatId = message.chat().id();
            optionalCommand = Optional.ofNullable(message.text());
        } else {
            optionalCommand = Optional.of(callbackQuery.data());
            chatId = update.callbackQuery().message().chat().id();
        }
        if (optionalCommand.isPresent()) {
            String endpointText = optionalCommand.get();
            if (!showSpecificMenu(chatId, endpointText)) {
                showFrontEndAndMenu(chatId, endpointText);
            }
        } else {
            logger.error("Запрос не распознан: {}", update);
        }
    }

    /**
     * Метод для отображения фронт части приложения на базе сущности {@code Endpoint}.<br>
     * По коду метода есть комментарии, что делает тот или иной отрывок кода.
     *
     * @param chatId       идентификатор чата с пользователем
     * @param endpointText искомый эндпоинт
     */
    @Override
    public void showFrontEndAndMenu(Long chatId, String endpointText) {
        endpointService.findAllEndpoints().stream()
                .filter(endpoint -> endpoint.getEndpointText().equals(endpointText))
                .findFirst()
                .ifPresent(endpoint -> {
                    // --- KEYBOARD ---
                    // лист для кнопок
                    List<InlineKeyboardButton> buttonList = new ArrayList<>();
                    // получаем всех наследников текущего эндпойнта и для каждого наследника
                    endpoint.getChild().forEach(childEndpoint -> {
                        // создаем кнопку с текстовым заголовком childEndpoint.getTitle()
                        // назначаем кнопке команду (эндпоинт!), которая будет отправлена при нажатии
                        // добавляем созданую кнопку в лист
                        buttonList.add(
                                new InlineKeyboardButton(childEndpoint.getTitle())
                                        .callbackData(childEndpoint.getEndpointText()));
                    });
                    // если у текущего эндпоинта есть родитель,
                    // добавляем к листу с кнопками еще и кнопку типа "назад", в меню родителя
                    Endpoint parentEndpoint = endpoint.getParent();
                    if (parentEndpoint != null) {
                        buttonList.add(new InlineKeyboardButton(parentEndpoint.getTitle())
                                .callbackData(parentEndpoint.getEndpointText()));
                    }
                    // --- KEYBOARD ---
                    sendResponse(chatId, endpoint.getContent(), buttonList);
                });
    }

    /**
     * Метод для формирования и отправки ответа с клавиатурой пользователю
     *
     * @param chatId     идентификатор чата телеграм
     * @param content    текстовое содержание ответа
     * @param buttonList кнопки, которые необходимо добавить к ответу
     */
    @Override
    public void sendResponse(Long chatId, String content, List<InlineKeyboardButton> buttonList) {
        SendMessage sendMessage = new SendMessage(chatId, content);
        // из листа кнопок формируем клавиатуру
        InlineKeyboardMarkup keyboard = buttonsByRows(buttonList);
        // формируем ответ и добавляем к нему клавиатуру
        sendMessage.replyMarkup(keyboard);
        // --- KEYBOARD ---
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (sendResponse != null && !sendResponse.isOk()) {
            logger.error("Ошибка при отправлении ответа: {}", sendResponse.description());
        }
    }

    /**
     * Метод для разбивки листа кнопок на строки с заданым в {@link #BUTTONS_IN_ROW} колчеством в строке
     *
     * @param buttonList лист с кнопками, который нужно разбить. Или не нужно разбивать.
     * @return Клавиатуру {@code InlineKeyboardMarkup} с разбитыми по строкам кнопками
     */
    // ------------------- СОВСЕМ УРОДСКИЙ МЕТОД, ЧТО-ТО ПРИДУМАТЬ ДРУГОЕ -------------------
    @Override
    public InlineKeyboardMarkup buttonsByRows(List<InlineKeyboardButton> buttonList) {
        if (buttonList.size() > BUTTONS_IN_ROW) {
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < buttonList.size(); i++) {
                row.add(buttonList.get(i));
                if ((i + 1) % BUTTONS_IN_ROW == 0 || i == buttonList.size() - 1) {
                    keyboard.addRow(row.toArray(InlineKeyboardButton[]::new));
                    row = new ArrayList<>();
                }
            }
            return keyboard;
        }
        return new InlineKeyboardMarkup().addRow(buttonList.toArray(InlineKeyboardButton[]::new));
    }

    /**
     * Метод для обработки эндпоинтов в константах посредством обычного перебора.
     * Если в списке константы отсутсвует искомый эндпоинт, то сразу вываливаемся из метода, чтобы не перебирать
     *
     * @param chatId       идентификатор чата с пользователем
     * @param endpointText искомый эндпоинт
     * @return {@code false} если эндпоинт не найден, {@code true} - если произошел вызов метода для эндпоинта
     */
    private boolean showSpecificMenu(Long chatId, String endpointText) {
        String parsed = endpointText.split("_")[0];
        if (!ENDPOINTS.contains(parsed)) {
            return false;
        }
        switch (parsed) {
            case ENDPOINT_START -> executeEndpointStart(chatId, endpointText);
            case ENDPOINT_MAIN_MENU -> executeEndpointMainMenu(chatId, endpointText);
            case ENDPOINT_PRAY -> executeEndpointPray(chatId);
            case ENDPOINT_GET_CONTACTS -> executeEndpointGetContacts(chatId);
            case ENDPOINT_REPORT_INFO -> executeEndpointReportInfo(chatId);
            case ENDPOINT_REPORT -> executeEndpointReport(chatId);
            case ENDPOINT_VIOLATION -> executeEndpointViolation(chatId);
        }
        return true;
    }

    /**
     * Метод для формирования кнопки вызова волонтера
     *
     * @return {@code InlineKeyboardButton} кнопка вызова волонтера
     */
    private InlineKeyboardButton getPrayButton() {
        return new InlineKeyboardButton(ENDPOINT_PRAY_TITLE).callbackData(ENDPOINT_PRAY);
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_START}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointStart(Long chatId, String endpoint_text) {
        User user = userService.findUserByChatId(chatId);
        if (user == null) {
            List<InlineKeyboardButton> buttonList = new ArrayList<>();
            animalShelterService.findAllShelters().forEach(animalShelter ->
                    buttonList
                            .add(new InlineKeyboardButton(
                                    animalShelter
                                            .getAnimalType()
                                            .getAnimalTitle())
                                    .callbackData(ENDPOINT_MAIN_MENU + "_" + animalShelter.getAnimalType().name())));
            sendResponse(chatId, GREETINGS_TEXT, buttonList);
            userService.save(new User(chatId));
        } else {
            executeEndpointMainMenu(chatId, endpoint_text);
        }
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_MAIN_MENU}
     *
     * @param chatId - идентификатор чата с пользователем
     * @throws NoSuchElementException если пользователь не найден
     */
    private void executeEndpointMainMenu(Long chatId, String endpoint_text) {
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        Endpoint endpoint = endpointService.findEndpointByEndpointText(endpoint_text);
        if (endpoint != null) {
            endpoint.getChild().forEach(childEndpoint -> buttonList
                    .add(new InlineKeyboardButton(childEndpoint.getTitle())
                            .callbackData(childEndpoint.getEndpointText())));
            buttonList.add(getPrayButton());
            sendResponse(chatId, endpoint.getContent(), buttonList);
        } else {
            logger.error("Эндпоинт не найден: {}", endpoint_text);
        }
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_PRAY}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointPray(Long chatId) {
        sendResponse(
                chatId,
                "Зову волонтера...",
                List.of(new InlineKeyboardButton(ENDPOINT_START)
                        .callbackData(ENDPOINT_START)));
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_GET_CONTACTS}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointGetContacts(Long chatId) {
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_REPORT_INFO}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointReportInfo(Long chatId) {
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_REPORT}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointReport(Long chatId) {
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_VIOLATION}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointViolation(Long chatId) {
    }

}
