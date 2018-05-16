package com.game.rockpaperscissor.business.dao;

import com.game.rockpaperscissor.business.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDAO extends JpaRepository<Game, Long> {

}
