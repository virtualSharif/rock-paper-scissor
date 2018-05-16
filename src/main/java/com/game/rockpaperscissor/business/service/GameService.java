package com.game.rockpaperscissor.business.service;

import com.game.rockpaperscissor.business.exception.GameNotFoundException;
import com.game.rockpaperscissor.business.exception.GameOverException;
import com.game.rockpaperscissor.business.enums.Choice;
import com.game.rockpaperscissor.business.model.Game;

public interface GameService {

    Game start(String playerOneName, String playerTwoName);

    Game getStatus(Long id) throws GameNotFoundException;

    Game play(Long id, Choice playerOneChoice, Choice playerTwoChoice) throws GameNotFoundException, GameOverException;
}
