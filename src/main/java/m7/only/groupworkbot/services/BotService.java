package m7.only.groupworkbot.services;

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
}
