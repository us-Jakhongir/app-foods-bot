package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }



        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            EditMessageText editMessageText = null;


            if (data.contains("category")) {
                Long categoryId= Long.parseLong(data.substring(9).trim());
                editMessageText = BotService.showProductsByCategory(message, categoryId);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (data.contains("product")) {
                long productId = Long.parseLong(data.substring(8).trim());
                SendPhoto sendPhoto = BotService.showProductInfoById(message, productId);

                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotSettings.USER_NAME_BOT;
    }
}
