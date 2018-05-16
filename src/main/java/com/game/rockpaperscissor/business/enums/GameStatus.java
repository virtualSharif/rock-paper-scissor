package com.game.rockpaperscissor.business.enums;

import lombok.Getter;

@Getter
public enum GameStatus {

    STARTED(0),

    FINISHED(1);

    private final Integer value;

    GameStatus(Integer value) {
        this.value = value;
    }
}
