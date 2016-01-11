package com.hong;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Hongwei on 2016/1/10.
 */
public class InMemoryMessageRepository implements MessageRepository {
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Message> messages = new ConcurrentHashMap<>();

    @Override
    public Iterable<Message> findAll() {
        return messages.values();
    }

    @Override
    public Message save(Message message) {
        Long id = message.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            message.setId(id);
        }
        messages.put(id, message);

        return message;
    }

    @Override
    public Message findMessage(Long id) {
        return messages.get(id);
    }

    @Override
    public void deleteMessage(Long id) {
        messages.remove(id);
    }
}
