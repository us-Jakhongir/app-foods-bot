package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.BotMenu;
import util.BotSettings;

public class AppFoodBot extends TelegramLongPollingBot {


    @Override
    public String getBotToken() {
        return BotSettings.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage();

            if (message.hasText()) {
                String text = message.getText();
                switch (text) {
                    case BotMenu.START:
                        sendMessage= BotService.start(update);
                        break;
                    case BotMenu.MENU:
                        sendMessage = BotService.menu(message.getChatId());
                        break;
                }

            }

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();

            long categoryId = Long.parseLong(data);

            EditMessageText editMessageText = BotService.showProductsByCategory(message, categoryId);

            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public String getBotUsername() {
        return BotSettings.USER_NAME_BOT;
    }
}
