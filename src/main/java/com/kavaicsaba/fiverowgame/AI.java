package com.kavaicsaba.fiverowgame;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class AI {
    private static final int EPONT[][] = {{1, 10, 300, 700}, {1, 2, 8, 700}};
    private static final int SPONT[][] = {{1, 15, 500, 1000}, {1, 2, 400, 1000}};
    private static final int CSPONT[][] = {{100, 200}, {150, 250}};

    private static final Point DIRECTIONS[] = {
            new Point(-1, -1), new Point( -1, 0),
            new Point( -1, 1), new Point( 0, -1)};

    public Point aiMoves(int[][] field) {
        int biggestScore = 0;
        int nbOgBiggestScore = 0;
        int[][] scores = new int[field.length][];
        Rectangle dimensions = new Rectangle(field.length, field[0].length);

        for (int x = 0; x < field.length; x++) {
            scores[x] = new int[field[x].length];
            for (int y = 0; y < scores[x].length; y++) {
                if (field[x][y] != 0) {
                    continue;
                }
                for (int j = 0; j < 2; j++) {
                    int trap = 0;
                    for (int i = 0; i < 4; i++) {
                        int count = 0;
                        int block = 0;
                        int ke = 0;
                        int ta = 0;
                        int block1 = 0;
                        for (int z = 0; z < 2; z++) {
                            Point currentDirection = new Point();
                            Point looking = new Point();
                            currentDirection.x = z == 0 ? DIRECTIONS[i].x : DIRECTIONS[i].x * -1;
                            currentDirection.y = z == 0 ? DIRECTIONS[i].y : DIRECTIONS[i].y * -1;
                            looking.x = x + currentDirection.x;
                            looking.y = y + currentDirection.y;
                            while (dimensions.contains(looking) && field[looking.x][looking.y] == j + 1) {
                                count++;
                                looking.x += currentDirection.x;
                                looking.y += currentDirection.y;
                            }
                            if (dimensions.contains(looking) && field[looking.x][looking.y] == 2 - j) {
                                block++;
                            }
                            if (!dimensions.contains(looking)) {
                                block++;

                            }
                            looking.x = x + currentDirection.x;
                            looking.y = y + currentDirection.y;
                            while (dimensions.contains(looking) &&
                                    (field[looking.x][looking.y] == j + 1 ||
                                            field[looking.x][looking.y] == 0) && ke <= 1) {
                                if (field[looking.x][looking.y] == j + 1) {
                                    ta++;
                                }
                                if (field[looking.x][looking.y] == 0) {
                                    ke++;
                                }
                                looking.x += currentDirection.x;
                                looking.y += currentDirection.y;
                            }
                            if (ke == 2) {
                                ke = 1;
                            }
                            if (dimensions.contains(looking) &&
                                    field[looking.x - currentDirection.x][looking.y - currentDirection.y] == 0) {
                                ke--;
                            }
                            if (dimensions.contains(looking) && field[looking.x][looking.y] == 2 - j) {
                                block1++;
                            }
                        }
                        if (ta == 2 && block1 == 0) {
                            trap += 2;
                        }
                        if (ta == 3 && block1 < 2) {
                            trap += 3;
                        }
                        if (block == 0 || block == 1 || (block == 2 && count >= 4)) {
                            if (count > 4) {
                                count = 4;
                            }
                            if (block == 2) {
                                block = 1;
                            }
                            if (j == 1 && count != 0) {
                                scores[x][y] += SPONT[block][count - 1];
                            }
                            if (j == 0 && count != 0) {
                                scores[x][y] += EPONT[block][count - 1];
                            }
                        }
                    }
                    if (trap == 4) {
                        scores[x][y] += CSPONT[j][0];
                    }
                    if (trap >= 5) {
                        scores[x][y] += CSPONT[j][1];
                    }
                }
                if (scores[x][y] > biggestScore) {
                    biggestScore = scores[x][y];
                    nbOgBiggestScore = 1;
                } else if (scores[x][y] == biggestScore) {
                    nbOgBiggestScore++;
                }
            }
        }

        nbOgBiggestScore = RandomUtils.nextInt(0, nbOgBiggestScore);
        int z = 0;
        for (int x = 0; x < dimensions.width; x++) {
            for (int y = 0; y < dimensions.height; y++) {
                if (scores[x][y] == biggestScore) {
                    if (z == nbOgBiggestScore) {
                        field[x][y] = 2;
                        return new Point(x, y);
                    }
                    else {
                        z++;
                    }
                }
            }
        }
        return null;
    }

    public GameState isEnd(int[][] field) {
        Rectangle dimensions = new Rectangle(field.length, field[0].length);
        for (int x = 0; x < dimensions.width; x++) {
            for (int y = 0; y < dimensions.height; y++) {
                int player = field[x][y];
                if (player == 0) {
                    continue;
                }
                for (int i = 0; i < 8; i++) {
                    int dirX = i < 4 ? DIRECTIONS[i].x : DIRECTIONS[i - 4].x * -1;
                    int dirY = i < 4 ? DIRECTIONS[i].y : DIRECTIONS[i - 4].y * -1;
                    int counter = 0;
                    int curX = x;
                    int curY = y;

                    while (dimensions.contains(curX, curY) && field[curX][curY] == player) {
                        counter++;
                        curX += dirX;
                        curY += dirY;
                    }
                    if (counter == 5) {
                        return new GameState(GameState.State.WINNING, player, x, y, curX - dirX, curY - dirY) ;
                    }
                }
            }
        }
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == 0) {
                    return new GameState(GameState.State.GAME_IN_PROGRESS);
                }
            }
        }

        return new GameState(GameState.State.DRAW);
    }

}
