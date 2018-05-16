package com.game.rockpaperscissor.business.service.impl;

import com.game.rockpaperscissor.config.Constants;
import com.game.rockpaperscissor.business.dao.GameDAO;
import com.game.rockpaperscissor.business.dao.RoundDAO;
import com.game.rockpaperscissor.business.exception.GameNotFoundException;
import com.game.rockpaperscissor.business.exception.GameOverException;
import com.game.rockpaperscissor.business.model.*;
import com.game.rockpaperscissor.business.enums.Choice;
import com.game.rockpaperscissor.business.enums.GameStatus;
import com.game.rockpaperscissor.business.enums.Result;
import com.game.rockpaperscissor.business.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameDAO gameDAO;

    private final RoundDAO roundDAO;

    @Autowired
    public GameServiceImpl(GameDAO gameDAO, RoundDAO roundDAO) {
        this.gameDAO = gameDAO;
        this.roundDAO = roundDAO;
    }

    @Override
    public Game start(String playerOneName, String playerTwoName) {
        Game game = createGame(playerOneName, playerTwoName);
        return gameDAO.save(game);
    }

    @Override
    public Game getStatus(Long id) throws GameNotFoundException {
        return gameDAO.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }

    @Override
    public Game play(Long id, Choice playerOneChoice, Choice playerTwoChoice) throws GameNotFoundException, GameOverException {
        Game game = getStatus(id);
        validateGameStatus(game);
        Round round = createRound(playerOneChoice, playerTwoChoice, game);
        addRound(game, round);
        incrementScore(round, game);
        checkGameWinner(game);
        return gameDAO.save(game);
    }

    private void addRound(Game game, Round round) {
        if (CollectionUtils.isEmpty(game.getRounds())) {
            List<Round> rounds = new ArrayList<>();
            game.setRounds(rounds);
        }
        game.getRounds().add(round);
    }

    private Round createRound(Choice playerOneChoice, Choice playerTwoChoice, Game game) {
        Result result = evaluateChoices(playerOneChoice, playerTwoChoice);
        Round round = new Round(playerOneChoice, playerTwoChoice, result, game);
        return roundDAO.save(round);
    }

    private void checkGameWinner(Game game) {
        if (Constants.WINNING_SCORE.equals(game.getPlayerOneScore()) ||
                Constants.WINNING_SCORE.equals(game.getPlayerTwoScore())) {
            game.setGameStatus(GameStatus.FINISHED);
        }
    }

    private void incrementScore(Round round, Game game) {
        if (round.getPlayerOneResult().equals(Result.WIN)) {
            game.setPlayerOneScore(game.getPlayerOneScore() + 1);
        } else if (round.getPlayerOneResult().equals(Result.LOOSE)) {
            game.setPlayerTwoScore(game.getPlayerTwoScore() + 1);
        }
    }

    private Result evaluateChoices(Choice playerOne, Choice playerTwo) {
        validateChoice(playerOne);
        Result result = Result.DRAW;
        if (playerOne.isBetterThan(playerTwo)) {
            result = Result.WIN;
        } else if (playerTwo.isBetterThan(playerOne)) {
            result = Result.LOOSE;
        }
        return result;
    }

    private Game createGame(String playerOneName, String playerTwoName) {
        Game game = new Game(playerOneName, playerTwoName);
        game.setGameStatus(GameStatus.STARTED);
        return game;
    }

    private void validateChoice(Choice playerOneChoice) {
        if (Objects.isNull(playerOneChoice)) {
            String msg = String.format("Choice cannot be empty, playerOneChoice: {}", playerOneChoice);
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateGameStatus(Game game) throws GameOverException {
        if (!GameStatus.STARTED.equals(game.getGameStatus())) {
            throw new GameOverException("Game is over, please create new game");
        }
    }

}
