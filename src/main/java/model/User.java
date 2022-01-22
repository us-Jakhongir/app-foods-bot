package model;

import enums.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:
 * Date: 1/21/2022
 * Time:
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Long chatId;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private BotState botState;


    public User(Long chatId, String firstname, String lastname, String username, String phoneNumber, BotState botState) {
        this.chatId = chatId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.botState = botState;
    }
}
