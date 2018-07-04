package com.kavaicsaba.fiverowgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;

@RestController
public class GameController {

    @Autowired
    private AI ai;

    @PostMapping(value = "/moveai", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MoveResponse moveAI(@RequestBody int[][] field) throws IOException {
        final GameState end1 = ai.isEnd(field);
        if (end1.getState() == GameState.State.DRAW || end1.getState() == GameState.State.WINNING) {
            return new MoveResponse(end1);
        }
        final Point point = ai.aiMoves(field);
        return new MoveResponse(ai.isEnd(field), point.x, point.y);
    }

    @GetMapping(value = "/warmup")
    public void warmup() {
    }

}
