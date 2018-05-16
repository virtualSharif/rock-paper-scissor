package com.game.rockpaperscissor.business.enums;

import lombok.Getter;

@Getter
public enum Result {

    DRAW(0),

    LOOSE(2),

    WIN(1);

    private final Integer value;

    Result(Integer value) {
        this.value = value;
    }
}

