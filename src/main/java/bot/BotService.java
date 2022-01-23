package bot;

import enums.BotState;
import model.Category;
import model.Product;
import model.User;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import service.*;
import util.BotConstants;
import util.BotMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BotService {

    public static UserService userService = new UserServicceImpl();
    public static CategoryService categoryService = new CategoryServiceImpl();
    public static ProductService productService = new ProductServiceImpl();


    public static SendMessage start(Update update) {
        // Register If new User
        registerUser(update);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        sendMessage.setReplyMarkup(getMenuKeyboard());


        return sendMessage;
    }

    private static ReplyKeyboard getMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);


        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(BotMenu.MENU));
        rows.add(keyboardRow1);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(BotMenu.CART));
        keyboardRow2.add(new KeyboardButton(BotMenu.SETTINGS));
        rows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(rows);

        return replyKeyboardMarkup;
    }

    private static void registerUser(Update update) {
        org.telegram.telegrambots.meta.api.objects.User from = update.getMessage().getFrom();

        if (!userService.existsByChatId(update.getMessage().getChatId())) {
            User user = new User(
                    update.getMessage().getChatId(),
                    from.getFirstName(),
                    from.getLastName(),
                    from.getUserName(),
                    update.getMessage().getContact() != null ? update.getMessage().getContact().getPhoneNumber() : "",
                    BotState.START
            );

            userService.save(user);


        }
    }

    public static SendMessage menu(Long chatId) {
        User user = userService.findByChatId(chatId);
        if (user != null) {
            user.setBotState(BotState.SHOW_MENU);
            userService.update(user);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        sendMessage.setReplyMarkup(getMenuKeyboard());

        List<Category> categoryList = categoryService.findAll();

        sendMessage.setReplyMarkup(getInlineKeyboards(categoryList));
        return sendMessage;
    }

    private static ReplyKeyboard getInlineKeyboards(List<Category> categoryList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyBoards = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Iterator<Category> iterator = categoryList.iterator();
        while (iterator.hasNext()) {
            Category next = iterator.next();
            buttons = new ArrayList<>();
            InlineKeyboardButton button1 = new InlineKeyboardButton(next.getPrefix() + " " + next.getName());
            button1.setCallbackData(next.getId().toString());
            buttons.add(button1);

            if (iterator.hasNext()) {
                next = iterator.next();
                InlineKeyboardButton button2 = new InlineKeyboardButton(next.getPrefix() + " " + next.getName());
                button2.setCallbackData(next.getId().toString());
                buttons.add(button2);


            }
            inlineKeyBoards.add(buttons);
        }


        inlineKeyboardMarkup.setKeyboard(inlineKeyBoards);
        return inlineKeyboardMarkup;
    }


    private static InlineKeyboardMarkup getInlineKeyboardsProduct(List<Product> productList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyBoards = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product next = iterator.next();
            buttons = new ArrayList<>();
            InlineKeyboardButton button1 = new InlineKeyboardButton(next.getName());
            button1.setCallbackData(next.getId().toString());
            buttons.add(button1);

            if (iterator.hasNext()) {
                next = iterator.next();
                InlineKeyboardButton button2 = new InlineKeyboardButton(next.getName());
                button2.setCallbackData(next.getId().toString());
                buttons.add(button2);


            }
            inlineKeyBoards.add(buttons);
        }


        inlineKeyboardMarkup.setKeyboard(inlineKeyBoards);
        return inlineKeyboardMarkup;
    }



    public static EditMessageText showProductsByCategory(Message message, long categoryId) {
        User user = userService.findByChatId(message.getChatId());
        if (user != null) {
            user.setBotState(BotState.SHOW_PRODUCTS);
            userService.update(user);
        }

        List<Product> products = productService.findAllByCategoryId(categoryId);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.MENU_HEADER);

        editMessageText.setReplyMarkup(getInlineKeyboardsProduct(products));


        return editMessageText;
    }
}
