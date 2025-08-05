package com.hermann.cepservice.command;

public interface Command<T> {
    T execute(String input);
}