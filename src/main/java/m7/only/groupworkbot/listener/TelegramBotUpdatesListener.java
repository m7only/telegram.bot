package m7.only.groupworkbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Обработан update: {}", update);
                Message message = update.message();
                Long chatId = message.chat().id();
                Optional<String> optionalText = Optional.ofNullable(message.text());
                if (optionalText.isPresent()) {
                    String text = optionalText.get();
                    if ("/start".equals(text)) {
                        SendMessage sendMessage = new SendMessage(chatId, "Вас приветствует бот приюта для животных!");
                        SendResponse sendResponse = telegramBot.execute(sendMessage);
                        if (!sendResponse.isOk()) {
                            logger.error("Ошибка при отправлении ответа: {}", sendResponse.description());
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
