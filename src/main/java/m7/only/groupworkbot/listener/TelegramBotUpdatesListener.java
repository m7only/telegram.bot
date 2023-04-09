package m7.only.groupworkbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import m7.only.groupworkbot.services.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для получения сообщений ({@code update}) с сервера Telegram.
 * Инициализируем {@code Listener}, логируем успешную обработку и ошибки.
 * Дальнейшая обработка {@code update} передается в {@link #botService}
 */
@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final BotService botService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, BotService botService) {
        this.telegramBot = telegramBot;
        this.botService = botService;
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
                botService.process(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
