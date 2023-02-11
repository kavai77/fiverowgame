package com.himadri.fiverowgame;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final AI ai;

    @PostMapping(value = "/moveai", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MoveResponse moveAI(@RequestBody Integer[][] fieldInteger) {
        int[][] field = new int[fieldInteger.length][];
        for (int x = 0; x < fieldInteger.length; x++) {
            field[x] = new int[fieldInteger[x].length];
            for (int y = 0; y < fieldInteger[x].length; y++) {
                field[x][y] = fieldInteger[x][y];
            }
        }
        final GameState end1 = ai.isEnd(field);
        if (end1.getState() == GameState.State.DRAW || end1.getState() == GameState.State.WINNING) {
            return new MoveResponse(end1, 0, 0);
        }
        final Point point = ai.aiMoves(field);
        return new MoveResponse(ai.isEnd(field), point.x, point.y);
    }
}
