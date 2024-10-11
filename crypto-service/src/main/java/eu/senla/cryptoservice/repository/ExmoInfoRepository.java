package eu.senla.cryptoservice.repository;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExmoInfoRepository extends MongoRepository<ExmoInfoEntity, String> {
}
