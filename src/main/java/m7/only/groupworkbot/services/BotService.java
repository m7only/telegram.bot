package m7.only.groupworkbot.services;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.List;

public interface BotService {
    void process(Update update);

    void showFrontEndAndMenu(Long chatId, String endpointText);

    void sendResponse(Long chatId, String content, List<InlineKeyboardButton> buttonList);

    InlineKeyboardMarkup buttonsByRows(List<InlineKeyboardButton> buttonList);

    boolean showSpecificMenu(Long chatId, String endpointText);

    InlineKeyboardButton getPrayButton();

    void executeEndpointStart(Long chatId);

    void executeEndpointMainMenu(Long chatId, String endpoint_text);

    void executeEndpointPray(Long chatId);

    void executeEndpointGetContacts(Long chatId, String endpointText);

    void executeEndpointReportInfo(Long chatId);

    void executeEndpointReportByPhoto(Message message);

    void executeEndpointReportByText(Long chatId, String endpointText);

    void executeEndpointViolation(Long chatId);
}
