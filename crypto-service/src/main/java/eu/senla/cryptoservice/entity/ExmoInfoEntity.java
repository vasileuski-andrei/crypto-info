package eu.senla.cryptoservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Document("EXMO_INFO")
public class ExmoInfoEntity {

    @Id
    private String id;
    private String uid;
    private LocalDateTime dateTime;
    private List<String> balances;
}

