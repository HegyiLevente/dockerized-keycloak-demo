package com.example.demo.repository;

import com.example.demo.entity.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findAll();

}
