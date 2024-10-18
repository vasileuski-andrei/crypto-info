package eu.senla.telegramservice.service;

import eu.senla.shared.dto.TgDataDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

import static eu.senla.shared.enums.TgMessageType.CURRENCY_LIST;
import static eu.senla.shared.enums.TgMessageType.MY_INFO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TelegramBotTest {

    private static final Long ID = 1L;
    private static final String TOPIC_TG_MESSAGE = "TG.MESSAGE";

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private TelegramBot telegramBot;

    @Captor
    private ArgumentCaptor<TgDataDto> tgDataDtoCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(telegramBot, "tgMessageTopic", TOPIC_TG_MESSAGE);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("onUpdateReceivedDataProvider")
    void onUpdateReceived(String messageText) {
        // GIVEN
        Update update = buildUpdate(messageText);

        // WHEN
        telegramBot.onUpdateReceived(update);

        // THEN
        verify(kafkaTemplate).send(eq(TOPIC_TG_MESSAGE), tgDataDtoCaptor.capture());
        assertEquals(messageText, tgDataDtoCaptor.getValue().getMessageText());
    }

    private static Stream<Arguments> onUpdateReceivedDataProvider() {
        return Stream.of(
                Arguments.of(MY_INFO.getMessageType()),
                Arguments.of(CURRENCY_LIST.getMessageType()),
                Arguments.of("1 btc usd")
        );
    }

    private Update buildUpdate(String messageText) {
        Chat chat = new Chat();
        chat.setId(ID);
        Message message = new Message();
        message.setText(messageText);
        message.setMessageId(Math.toIntExact(ID));
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}