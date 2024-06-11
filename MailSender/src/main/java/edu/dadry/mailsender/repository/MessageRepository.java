package edu.dadry.mailsender.repository;

import edu.dadry.mailsender.models.MessageData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends ElasticsearchRepository<MessageData, String> {
    List<MessageData> findBySuccess(Boolean success);
}
