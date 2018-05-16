package com.game.rockpaperscissor.business.service.impl;

import com.game.rockpaperscissor.business.dao.GameDAO;
import com.game.rockpaperscissor.business.dao.RoundDAO;
import com.game.rockpaperscissor.business.exception.GameNotFoundException;
import com.game.rockpaperscissor.business.exception.GameOverException;
import com.game.rockpaperscissor.business.enums.Choice;
import com.game.rockpaperscissor.business.model.Game;
import com.game.rockpaperscissor.business.enums.GameStatus;
import com.game.rockpaperscissor.business.service.GameService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameServiceImplTest {

    private GameDAO gameDAO;

    private RoundDAO roundDAO;

    private GameService classUnderTest;

    @Before
    public void setup() {
        gameDAO = mock(GameDAO.class);
        roundDAO = mock(RoundDAO.class);
        classUnderTest = new GameServiceImpl(gameDAO, roundDAO);
    }


    @Test(expected = GameNotFoundException.class)
    public void shouldThrowBadGameNotFoundException() throws GameNotFoundException {

        // given
        when(gameDAO.findById(any())).thenReturn(Optional.empty());

        // when
        classUnderTest.getStatus(1L);
    }

    @Test(expected = GameOverException.class)
    public void shouldThrowBadGameOverException() throws GameOverException, GameNotFoundException {

        // given
        Game givenGame = new Game();
        givenGame.setGameStatus(GameStatus.FINISHED);
        Long givenId = new Long(1L);
        when(gameDAO.findById(any())).thenReturn(Optional.ofNullable(givenGame));

        // when
        classUnderTest.play(givenId, Choice.getRandom(), Choice.getRandom());

    }

}