package eu.senla.telegramservice.service;

import eu.senla.shared.dto.TgDataDto;
import eu.senla.shared.enums.TgMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static eu.senla.shared.enums.TgMessageType.CURRENCY_LIST;
import static eu.senla.shared.enums.TgMessageType.MY_INFO;
import static eu.senla.shared.enums.TgMessageType.CONVERSION;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private String botName;
    private Message message;

    @Value("${spring.kafka.topics.topic-tg-message}")
    private String tgMessageTopic;

    public TelegramBot(String botToken, String botName, KafkaTemplate<String, Object> kafkaTemplate) {
        super(botToken);
        this.botName = botName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onUpdateReceived(Update update) {
        message = update.getMessage();
        String messageText = message.getText();
        log.info("[TelegramBot.onUpdateReceived]Received tg message. Message: {}", messageText);

        if (messageText.equals("/start")) {
            sendAnswer("Let's go");
        } else if (messageText.equals(MY_INFO.getMessageType())) {
            sendEvent(MY_INFO, messageText);
        } else if (messageText.equals(CURRENCY_LIST.getMessageType())) {
            sendEvent(CURRENCY_LIST, messageText);
        } else if (messageText.equals(CONVERSION.getMessageType())) {
            sendAnswer("Please enter: <amount> <from> <to>. Example: 1 btc usd");
        } else if (messageText.matches("\\d+\\s[A-Za-z]+\\s[A-Za-z]+")) {
            sendEvent(CONVERSION, messageText);
        } else {
            sendAnswer("Please enter a valid request.");
        }
    }

    private void sendEvent(TgMessageType messageType, String messageText) {
        TgDataDto tgDataDto = TgDataDto.builder()
                .messageType(messageType)
                .messageText(messageText)
                .build();
        kafkaTemplate.send(tgMessageTopic, tgDataDto);
    }

    public void sendAnswer(String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(answer);

        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error sending answer to tg bot. Cause: {}", e.toString());
        }
    }

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(MY_INFO.getMessageType()));
        keyboardRow.add(new KeyboardButton(CURRENCY_LIST.getMessageType()));
        keyboardRow.add(new KeyboardButton(CONVERSION.getMessageType()));
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
