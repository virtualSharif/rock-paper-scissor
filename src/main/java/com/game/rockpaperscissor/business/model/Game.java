package com.game.rockpaperscissor.business.model;

import com.game.rockpaperscissor.business.enums.GameStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private GameStatus gameStatus;

    private String playerOneName;

    private Integer playerOneScore;

    private String playerTwoName;

    private Integer playerTwoScore;

    @OneToMany
    private List<Round> rounds;

    public Game(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.rounds = new ArrayList<>();
    }
}