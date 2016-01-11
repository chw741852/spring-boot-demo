package com.hong;

/**
 * Created by Hongwei on 2016/1/10.
 */
public interface MessageRepository {
    Iterable<Message> findAll();

    Message save(Message message);

    Message findMessage(Long id);

    void deleteMessage(Long id);
}
