package org.example;

import java.util.Objects;
import java.util.stream.Stream;

public enum Action {
    EXIT(0, false),
    CREATE(1, true, "([A-z]*(,[A-z]*)){4}[0-9]+"),
    UPDATE(2, true, "[0-9],+([A-z]*(,[A-z]*)){4}[0-9]+"),
    DELETE(3, true, "[0-9]+"),
    STATS_BY_COURSE(4, false),
    STATS_BY_CITY(5, false),
    SEARCH(6, true, "^([A-Za-z]+(?:,[A-Za-z]+)?)?$"),
    ERROR(-1, false);

    private Integer code;
    private boolean requireAdditionalData;
    private String regex;

    Action(Integer code, boolean requireAdditionalData, String regex) {
        this.code = code;
        this.requireAdditionalData = requireAdditionalData;
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
    Action(Integer code, boolean requireAdditionalData) {
        this.code = code;
        this.requireAdditionalData = requireAdditionalData;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isRequireAdditionalData() {
        return requireAdditionalData;
    }

    public static Action fromCode(Integer code) {
        return Stream.of(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Неизвестный код действия " + code);
                    return Action.ERROR;
                });
    }
}
