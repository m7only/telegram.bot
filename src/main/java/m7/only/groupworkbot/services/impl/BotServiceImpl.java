package m7.only.groupworkbot.services.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.*;

/**
 * Класс для обработки {@linkplain Endpoint эндпоинтов}.
 * Эндпоинты с логикой опрделены как константы, private т.к. используются только в этом классе
 * и не предполагается их использование в других классах
 * Энпоинты без логиги (по сути простой фронт с меню) хранятся в базе, их Entity - {@code Endpoint}
 */
@Service
public class BotServiceImpl implements BotService {

    // ----- FEEDBACK CONSTANT -----
    /**
     * Текст привествия пользователя в эндпоинте "/start"
     */
    private static final String GREETINGS_TEXT = "Здравствуйте! Я бот приюта для животных. Выберите, пожалуйста, интересующее животное.";

    /**
     * Текст сообщения, если тип файла(фотографии) не поддерживается
     */
    private static final String UNSUPPORTED_IMAGE_FORMAT = "К сожалению, я не поддерживаю такой формат.";

    /**
     * Сообщение об успешной загрузке фотографии
     */
    private static final String REPORT_PHOTO_SUCCESS = "Фотография загружена.";

    /**
     * Сообщение об успешной загрузке текста отчета
     */
    private static final String REPORT_TEXT_SUCCESS = "Описание состояния животного принято.";

    /**
     * Сообщение-напоминание о необходимости рассказать о животном в отчете
     */
    private static final String REPORT_REMIND_NEED_TEXT = "Напоминаю, что помимо фотографии необходимо рассказать о состоянии животного. В отчете за сегодня она отсутствует, пожалуйста, отправьте фотографию.";

    /**
     * Сообщение-напоминание о необходимости отправить фотографию
     */
    private static final String REPORT_REMIND_NEED_PHOTO = "Напоминаю, что помимо описания состояния животного нужно отправить его фотографию. В отчете за сегодня оно отсутствует, пожалуйста, отправьте описание.";

    /**
     * Сообщение об успешном отчете: в отчете за текущие сутки есть описание и хотя бы одна фотография.
     */
    private static final String REPORT_FULL_SUCCESS = "Отчет за сегодня полностью сформирован.";
    // ----- FEEDBACK CONSTANT -----

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

    // ----- SETTINGS CONSTANT -----
    /**
     * Количество кнопок в одной строке
     */
    // ----- SETTINGS CONSTANT -----
    private static final int BUTTONS_IN_ROW = 2;


    private final Logger logger = LoggerFactory.getLogger(BotServiceImpl.class);
    private final TelegramBot telegramBot;
    private final EndpointService endpointService;
    private final UserService userService;
    private final AnimalShelterService animalShelterService;
    private final ReportService reportService;

    public BotServiceImpl(TelegramBot telegramBot, EndpointService endpointService, UserService userService, AnimalShelterService animalShelterService, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.endpointService = endpointService;
        this.userService = userService;
        this.animalShelterService = animalShelterService;
        this.reportService = reportService;
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
     * 3. Если в {@code message} есть фотографии, то вызываем метод {@link #executeEndpointReportByPhoto(Message)}
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
            // если была нажата кнопка
            chatId = message.chat().id();
            optionalCommand = Optional.ofNullable(message.text());
        } else {
            // если была текстовая команда
            optionalCommand = Optional.of(callbackQuery.data());
            chatId = update.callbackQuery().message().chat().id();
        }

        if (optionalCommand.isPresent()) {
            // если есть команда от кнопки или текстовая
            String endpointText = optionalCommand.get();
            if (!showSpecificMenu(chatId, endpointText)) {
                showFrontEndAndMenu(chatId, endpointText);
            }
        } else {
            if (message.photo() != null || message.document() != null) {
                // если есть фото или документ
                executeEndpointReportByPhoto(message);
            } else {
                logger.error("Запрос не распознан: {}", update);
            }
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
                .ifPresentOrElse(endpoint -> {
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
                }, () -> {
                    logger.info("Команда не распознана: {}", endpointText);
                    sendResponse(chatId, "Команда не распознана", null);
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
        if (buttonList != null) {
            // --- KEYBOARD ---
            // из листа кнопок формируем клавиатуру
            InlineKeyboardMarkup keyboard = buttonsByRows(buttonList);
            // формируем ответ и добавляем к нему клавиатуру
            sendMessage.replyMarkup(keyboard);
            // --- KEYBOARD ---
        }
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
        parsed = parsed.split("\s")[0];
        if (!ENDPOINTS.contains(parsed)) {
            return false;
        }
        switch (parsed) {
            case ENDPOINT_START -> executeEndpointStart(chatId);
            case ENDPOINT_MAIN_MENU -> executeEndpointMainMenu(chatId, endpointText);
            case ENDPOINT_PRAY -> executeEndpointPray(chatId);
            case ENDPOINT_GET_CONTACTS -> executeEndpointGetContacts(chatId);
            case ENDPOINT_REPORT_INFO -> executeEndpointReportInfo(chatId);
            case ENDPOINT_REPORT -> executeEndpointReportByText(chatId, endpointText);
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
    private void executeEndpointStart(Long chatId) {
        User user = userService.findUserByChatId(chatId);
        if (user == null) {
            userService.save(new User(chatId));
        }
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        animalShelterService.findAllShelters().forEach(animalShelter ->
                buttonList
                        .add(new InlineKeyboardButton(
                                animalShelter
                                        .getAnimalType()
                                        .getAnimalTitle())
                                .callbackData(ENDPOINT_MAIN_MENU + "_" + animalShelter.getAnimalType().name())));
        sendResponse(chatId, GREETINGS_TEXT, buttonList);
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
     * Метод для отработки энпоинта {@link #ENDPOINT_REPORT}, если прислали фото
     *
     * @param message - идентификатор чата с пользователем
     */
    private void executeEndpointReportByPhoto(Message message) {
        Long chatId = message.chat().id();
        Document document = message.document();
        String fileId = null;

        if (message.photo() != null) {
            fileId = message
                    .photo()[message.photo().length - 1]
                    .fileId();
        }

        if (document != null && document.mimeType().equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)) {
            fileId = document.fileId();
        }

        if (fileId == null) {
            sendResponse(chatId, UNSUPPORTED_IMAGE_FORMAT, null);
            logger.info("Неподдерживаемый формат: {}", message);
            return;
        }

        Report report = reportService.saveReport(
                userService.findUserByChatId(chatId),
                message.caption(),
                fileId
        );

        sendResponse(chatId, REPORT_PHOTO_SUCCESS, null);

        if (message.caption() == null && (report.getReport() == null || report.getReport().isBlank())) {
            sendResponse(chatId, REPORT_REMIND_NEED_TEXT, null);
        } else {
            sendResponse(chatId, REPORT_TEXT_SUCCESS, null);
        }

        if ((report.getReport() != null && !report.getReport().isBlank()) && (report.getPhotos() != null && report.getPhotos().size() != 0)) {
            sendResponse(chatId, REPORT_FULL_SUCCESS, null);
            executeEndpointStart(chatId);
        }
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_REPORT},
     * если прислана команда {@link #ENDPOINT_REPORT}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointReportByText(Long chatId, String endpointText) {
        Report report = reportService.saveReport(
                userService.findUserByChatId(chatId),
                endpointText.replaceAll(ENDPOINT_REPORT, ""),
                null
        );
        sendResponse(chatId, REPORT_TEXT_SUCCESS, null);
        if (report.getPhotos() == null || report.getPhotos().size() == 0) {
            sendResponse(chatId, REPORT_REMIND_NEED_PHOTO, null);
        } else {
            sendResponse(chatId, REPORT_PHOTO_SUCCESS, null);
        }

        if (report.getReport() != null && (report.getReport() != null && report.getPhotos().size() != 0)) {
            sendResponse(chatId, REPORT_FULL_SUCCESS, null);
            executeEndpointStart(chatId);
        }
    }

    /**
     * Метод для отработки энпоинта {@link #ENDPOINT_VIOLATION}
     *
     * @param chatId - идентификатор чата с пользователем
     */
    private void executeEndpointViolation(Long chatId) {
    }

}
