package comp1110.ass2;

import comp1110.ass2.board.Board;
import comp1110.ass2.player.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modified by Zeqi Gao on 19/5/2023
 * Modified by Yufei Huang on 19/5/2023
 */
public class BlueLagoon {
    // The Game Strings for five maps have been created for you.
    // They have only been encoded for two players. However, they are
    // easily extendable to more by adding additional player statements.
    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String DEFAULT_GAME_3 = "a 13 3; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String DEFAULT_GAME_4 = "a 13 4; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String WHEELS_GAME = "a 13 2; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String WHEELS_GAME_3 = "a 13 3; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String WHEELS_GAME_4 = "a 13 4; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME_3 = "a 13 3; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME_4 = "a 13 4; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME =  "a 7 2; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME_3 =  "a 7 3; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME_4 =  "a 7 4; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME = "a 23 2; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME_3 = "a 23 3; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME_4 = "a 23 4; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

    /**
     * Return a new game based on button clicked
     *
     * @param game Text of the button
     * @return stateString of determined game
     */
    public static String getGame(String game) {
        char player = game.charAt(game.length() - 9);
        if (player == '4'){
            return switch (game.substring(0, game.length()-10)) {
                default -> DEFAULT_GAME_4;
                case "WHEELS_GAME" -> WHEELS_GAME_4;
                case "FACE_GAME" -> FACE_GAME_4;
                case "SIDES_GAME" -> SIDES_GAME_4;
                case "SPACE_INVADERS_GAME" -> SPACE_INVADERS_GAME_4;
            };
        }
        else if(player == '3')
            return switch (game.substring(0, game.length()-10)) {
                default -> DEFAULT_GAME_3;
                case "WHEELS_GAME" -> WHEELS_GAME_3;
                case "FACE_GAME" -> FACE_GAME_3;
                case "SIDES_GAME" -> SIDES_GAME_3;
                case "SPACE_INVADERS_GAME" -> SPACE_INVADERS_GAME_3;
            };
        else {
            return switch (game.substring(0, game.length()-10)) {
                default -> DEFAULT_GAME;
                case "WHEELS_GAME" -> WHEELS_GAME;
                case "FACE_GAME" -> FACE_GAME;
                case "SIDES_GAME" -> SIDES_GAME;
                case "SPACE_INVADERS_GAME" -> SPACE_INVADERS_GAME;
            };
        }
    }

    /**
     * Check if the string encoding of the game state is well-formed.
     * Note that this does not mean checking that the state is valid
     * (represents a state that players could reach in game play),
     * only that the string representation is syntactically well-formed.
     * <p>
     * A description of the state string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param stateString a string representing a game state
     * @return true if stateString is well-formed and false otherwise
     */
    public static boolean isStateStringWellFormed(String stateString){
        // Game Arrangement Statement
        String gaStatements = "a\\s\\d+\\s\\d;";

        // Current State Statement
        String csStatements = "c\\s\\d\\s[E|S];";

        // Island Statement
        String iStatements = "(\\si\\s\\d+(\\s\\d+,\\d+)+;)+";

        // Stones Statement
        String sStatements = "s(\\s\\d+,\\d+){32};";

        // Unclaimed Resources and Statuettes Statement
        String urAndsStatements = "r\\sC(\\s\\d+,\\d+)*\\sB(\\s\\d+,\\d+)*\\sW(\\s\\d+,\\d+)*\\sP(\\s\\d+,\\d+)*\\sS(\\s\\d+,\\d+)*;";

        // Player Statement
        String pStatements = "(\\sp\\s\\d\\s\\d+(\\s\\d+){5}\\sS(\\s\\d+,\\d+)*\\sT(\\s\\d+,\\d+)*;)+";

        // Formally Game State
        String gameState = gaStatements + " " + csStatements + iStatements + " " + sStatements + " " + urAndsStatements + pStatements;

        // Check whether the form of the stateString is well
        if (Pattern.matches(gameState,stateString)) {
            return true;
        }
        return false;
    }
    // FIXME Task 3


    /**
     * Check if the string encoding of the move is syntactically well-formed.
     * <p>
     * A description of the move string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param moveString a string representing a player's move
     * @return true if moveString is well-formed and false otherwise
     */
    public static boolean isMoveStringWellFormed(String moveString){
        // Move String
        String mstring = "[S|T]\\s(\\d+,\\d+)";

        // Check whether the form of the moveString is well
        if (Pattern.matches(mstring,moveString)){
            return true;
        }
        return false;
    }
    // FIXME Task 4


    /**
     * Given a state string which is yet to have resources distributed amongst the stone circles,
     * randomly distribute the resources and statuettes between all the stone circles.
     * <p>
     * There will always be exactly 32 stone circles.
     * <p>
     * The resources and statuettes to be distributed are:
     * - 6 coconuts
     * - 6 bamboo
     * - 6 water
     * - 6 precious stones
     * - 8 statuettes
     * <p>
     * The distribution must be random.
     *
     * @param stateString a string representing a game state without resources distributed
     * @return a string of the game state with resources randomly distributed
     */
    public static String distributeResources(String stateString) {
        String sState = stateString.substring(stateString.indexOf("s")+2, stateString.indexOf("r")-2);
        String[] stoneCircles = sState.split(" ");
        String[] rStates = {"C", "B", "W", "P", "S"};
        Random rand = new Random();

        //
        int[] resourcesArray = new int[32];
        for (int i = 0; i < 32; i++) {
            resourcesArray[i] = i<24 ? i/6 : 4;
        }

        // Fisher-Yates Shuffle
        for (int i = 0; i < resourcesArray.length; i++) {
            int temp = resourcesArray[i];
            int j = rand.nextInt(i, resourcesArray.length);   // Swap i with random j in [i, length)
            resourcesArray[i] = resourcesArray[j];
            resourcesArray[j] = temp;
        }
        for (int i = 0; i < 32; i++) {
            rStates[resourcesArray[i]] += (" " + stoneCircles[i]); // Distribute shuffled coordinates to resource types
        }

        // Form the Resource State String
        String rState = "";
        for (String resource : rStates) {
            rState += (" " + resource);
        }
        return stateString.substring(0, stateString.indexOf("r")+1) + rState + stateString.substring(stateString.indexOf("p")-2); // FIXME Task 6
    }
    // FIXME Task 6

    public static boolean isResourcesDistributed(String stateString) {
        return false;
    }


    public static int getBoardHeight(String stateString) {
        Pattern pattern = Pattern.compile("a\\s\\d+\\s\\d;");
        Matcher matcher = pattern.matcher(stateString);
        matcher.find();
        return Integer.parseInt(stateString.substring(matcher.start(), matcher.end()-1).split(" ")[1]);
    }


    public static int getPlayerNumber(String stateString) {
        Pattern pattern = Pattern.compile("a\\s\\d+\\s\\d;");
        Matcher matcher = pattern.matcher(stateString);
        matcher.find();
        return Integer.parseInt(stateString.substring(matcher.start(), matcher.end()-1).split(" ")[2]);
    }


    public static int getCurrentPlayerId(String stateString) {
        Pattern pattern = Pattern.compile("c\\s\\d\\s[ES];");
        Matcher matcher = pattern.matcher(stateString);
        matcher.find();
        return Integer.parseInt(stateString.substring(matcher.start(), matcher.end()-1).split(" ")[1]);
    }

    public static char getPhase(String stateString) {
        Pattern pattern = Pattern.compile("c\\s\\d\\s[ES];");
        Matcher matcher = pattern.matcher(stateString);
        matcher.find();
        return stateString.charAt(matcher.end()-2);
    }

    public static List<String> getLands(String stateString) {
        List<String> lands = new ArrayList<>();

        Pattern pattern = Pattern.compile("i\\s\\d+(\\s\\d+,\\d+)+;");
        Matcher matcher = pattern.matcher(stateString);
        List<String> islands = new ArrayList<>();
        while (matcher.find())
            islands.add(stateString.substring(matcher.start(), matcher.end() - 1));   // Start index of an island statement
        for (String island : islands) {
            int start = island.indexOf(" ", 2);
            String[] coordinates = island.substring(start+1).split(" ");
            lands.addAll(Arrays.asList(coordinates));   // Add to list
        }
        return lands;
    }


    public static int[][] getTerrainsMatrix(String stateString) {
        int height = getBoardHeight(stateString);
        int[][] terrainsMatrix = new int[height][height];
        for (int i = 0; i < height; i += 2)
            terrainsMatrix[i][height-1] = -1;

        List<String> lands = getLands(stateString);
        for (String coordinate : lands) {
            Position pos = new Position(coordinate);
            terrainsMatrix[pos.getX()][pos.getY()] = 1;
        }

        List<String> stoneCircles = getStoneCircles(stateString);
        for (String coordinate : stoneCircles) {
            Position pos = new Position(coordinate);
            terrainsMatrix[pos.getX()][pos.getY()] = 2;
        }

        return terrainsMatrix;
    }


    public static int[][] getIslandsMatrix(String stateString) {
        int height = getBoardHeight(stateString);
        int[][] islandsMatrix = new int[height][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++)
                islandsMatrix[i][j] = -1;

        Pattern pattern = Pattern.compile("i\\s\\d+(\\s\\d+,\\d+)+;");
        Matcher matcher = pattern.matcher(stateString);
        ArrayList<String> islands = new ArrayList<>();
        while (matcher.find())
            islands.add(stateString.substring(matcher.start(), matcher.end() - 1));   // Start index of an island statement
        for (int id = 0; id < islands.size(); id++) {
            int start = islands.get(id).indexOf(" ", 2);
            String[] coordinates = islands.get(id).substring(start+1).split(" ");
            for (String coordinate : coordinates) {
                Position pos = new Position(coordinate);
                islandsMatrix[pos.getX()][pos.getY()] = id;
            }
        }

        return islandsMatrix;
    }


    public static List<Integer> getIslandsStats(String stateString) {
        List<Integer> islandsStats = new ArrayList<>();
        Pattern pattern = Pattern.compile("i\\s\\d+(\\s\\d+,\\d+)+;");
        Matcher matcher = pattern.matcher(stateString);
        while (matcher.find())
            islandsStats.add(Integer.parseInt(stateString.substring(matcher.start(), matcher.end() - 1).split(" ")[1]));

        return  islandsStats;
    }


    public static List<String> getStoneCircles(String stateString) {
        Pattern pattern = Pattern.compile("s(\\s\\d+,\\d+){32};");
        Matcher matcher = pattern.matcher(stateString);
        matcher.find();
        return List.of(stateString.substring(matcher.start() + 2, matcher.end() - 1).split(" "));
    }


    public static List<List<String>> getUnclaimedResources(String stateString) {
        List<List<String>> unclaimedResources = new ArrayList<>();

        String unclaimedResourcesState = stateString.substring(stateString.indexOf('r'), stateString.indexOf('p')-2);
        Pattern pattern = Pattern.compile("[CBWPS](\\s\\d+,\\d+)*");
        Matcher matcher = pattern.matcher(unclaimedResourcesState);
        while (matcher.find()) {
            List<String> resource = new ArrayList<>();
            if (matcher.end() - matcher.start() > 2) {
                resource.addAll(List.of(unclaimedResourcesState.substring(matcher.start() + 2, matcher.end()).split(" ")));
            }
            unclaimedResources.add(resource);
        }

        return unclaimedResources;
    }

    public static int[] getScores(String stateString) {
        int playerNum = getPlayerNumber(stateString);
        int[] scores = new int[playerNum];

        Pattern pattern = Pattern.compile("p\\s\\d\\s\\d+(\\s\\d+){5}\\sS(\\s\\d+,\\d+)*\\sT(\\s\\d+,\\d+)*;");
        Matcher matcher = pattern.matcher(stateString);

        int playerId = 0;
        while (matcher.find()) {
            scores[playerId] = Integer.parseInt(stateString.substring(matcher.start(), matcher.end()-1).split(" ")[2]);
            playerId++;
        }

        return scores;
    }

    public static List<List<String>> getPlaced(String stateString) {
        List<List<String>> placed = new ArrayList<>();

        Pattern pattern = Pattern.compile("p\\s\\d\\s\\d+(\\s\\d+){5}\\sS(\\s\\d+,\\d+)*\\sT(\\s\\d+,\\d+)*;");
        Matcher matcher = pattern.matcher(stateString);

        int playerCount = 0;
        while (matcher.find()) {
            List<String> placedSettler = new ArrayList<>();
            List<String> placedVillage = new ArrayList<>();
            String pState = stateString.substring(matcher.start(), matcher.end());
            Pattern pcPattern = Pattern.compile("[ST](\\s\\d+,\\d+)*");
            Matcher pcMatcher = pcPattern.matcher(pState);
            while (pcMatcher.find()) {
                if (pcMatcher.end() - pcMatcher.start() > 2) {
                    String[] coordinates = pState.substring(pcMatcher.start()+2, pcMatcher.end()).split(" ");
                    if (pState.charAt(pcMatcher.start()) == 'S')
                        placedSettler.addAll(Arrays.asList(coordinates));
                    else
                        placedVillage.addAll(Arrays.asList(coordinates));
                }
            }
            placed.add(placedSettler);
            placed.add(placedVillage);
            playerCount++;
        }
        return placed;
    }


    public static int[][] getPlacedMatrix(String stateString) {
        int height = getBoardHeight(stateString);
        int[][] placedMatrix = new int[height][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++)
                placedMatrix[i][j] = -1;

        Pattern pattern = Pattern.compile("p\\s\\d\\s\\d+(\\s\\d+){5}\\sS(\\s\\d+,\\d+)*\\sT(\\s\\d+,\\d+)*;");
        Matcher matcher = pattern.matcher(stateString);

        int playerId = 0;
        while (matcher.find()) {
            String pState = stateString.substring(matcher.start(), matcher.end());
            Pattern pcPattern = Pattern.compile("[ST](\\s\\d+,\\d+)*");
            Matcher pcMatcher = pcPattern.matcher(pState);
            while (pcMatcher.find()) {
                if (pcMatcher.end() - pcMatcher.start() > 2) {
                    String[] coordinates = pState.substring(pcMatcher.start()+2, pcMatcher.end()).split(" ");
                    for (String coordinate : coordinates) {
                        Position pos = new Position(coordinate);
                        placedMatrix[pos.getX()][pos.getY()] = playerId;
                    }
                }
            }
            playerId++;
        }
        return placedMatrix;
    }

    public static int[][] getClaimedResources(String stateString) {
        int playerNum = getPlayerNumber(stateString);
        int[][] claimedResources = new int[playerNum][5];

        Pattern pattern = Pattern.compile("p\\s\\d\\s\\d+(\\s\\d+){5}\\sS(\\s\\d+,\\d+)*\\sT(\\s\\d+,\\d+)*;");
        Matcher matcher = pattern.matcher(stateString);

        int playerId = 0;
        while (matcher.find()) {
            String[] stats = stateString.substring(matcher.start(), matcher.end()).split(" ");
            for (int i = 0; i < 5; i++)
                claimedResources[playerId][i] = Integer.parseInt(stats[i+3]);
            playerId++;
        }

        return claimedResources;
    }

    public static List<Position> getNeighbors(Position pos, int height) {
        List<Position> neighbors = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Not out of bound
                if(x+i >= 0 && x+i < height && y+j >= 0 && ((x+i)%2 == 0 ? (y+j<height-1) : (y+j<height))) {
                    // If x is even row
                    if (x % 2 == 0) {
                        if (!(i != 0 && j == -1) && !(i == 0 && j == 0)) {
                            neighbors.add(new Position(x+i, y+j));
                        }
                    }
                    // If x is odd row
                    else {
                        if (!(i != 0 && j == 1) && !(i == 0 && j == 0)) {
                            neighbors.add(new Position(x+i, y+j));
                        }
                    }
                }
            }
        }
        return neighbors;
    }


    /**
     * Given a state string and a move string, determine if the move is
     * valid for the current player.
     * <p>
     * For a move to be valid, the player must have enough pieces left to
     * play the move. The following conditions for each phase must also
     * be held.
     * <p>
     * In the Exploration Phase, the move must either be:
     * - A settler placed on any unoccupied sea space
     * - A settler or a village placed on any unoccupied land space
     *   adjacent to one of the player's pieces.
     * <p>
     * In the Settlement Phase, the move must be:
     * - Only a settler placed on an unoccupied space adjacent to
     *   one of the player's pieces.
     * Importantly, players can now only play on the sea if it is
     * adjacent to a piece they already own.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return true if the current player can make the move and false otherwise
     */
    public static boolean isMoveValid(String stateString, String moveString){
        if (!isStateStringWellFormed(stateString) || !isMoveStringWellFormed(moveString))
            return false;

        String toMove = moveString.substring(2);
        Position pos = new Position(toMove);

        int height = getBoardHeight(stateString);
        int players = getPlayerNumber(stateString);
        int playerId = getCurrentPlayerId(stateString);
        char phase = getPhase(stateString);

        // Villages are discarded in settlement phase
        if (phase == 'S' && moveString.charAt(0) == 'T')
            return false;

        // If coordinate is out of bound
        if(pos.getX() < 0 || pos.getX() >= height || pos.getY() < 0 || (pos.getX() % 2 == 0 ? pos.getY() >= height-1 : pos.getY() >= height))
            return false;

        // Get placed (by current player) and occupied (by others)
        List<List<String>> placed = getPlaced(stateString);
        List<String> placedSettler = placed.get(2 * playerId);
        List<String> placedVillage = placed.get(2 * playerId + 1);
        List<String> occupied = new ArrayList<>();
        for (List<String> strings : placed) {
            occupied.addAll(strings);
        }

        // If the place is already occupied
        if (occupied.contains(toMove))
            return false;

        // If pieces ran out
        if (moveString.charAt(0) == 'T' && placedVillage.size() >= 5)
            return false;
        else {
            if (moveString.charAt(0) == 'S' && placedSettler.size() >= switch (players) {
                case 2 -> 30;
                case 3 -> 25;
                default -> 20;
            })
                return false;
        }

        // Find all land spaces
        List<String> lands = getLands(stateString);

        // If place is sea in exploration phase (free for only settler)
        if (phase == 'E' && !lands.contains(toMove))
            return moveString.charAt(0) != 'T';

        // If place is land, or sea in settlement phase (adjacent rule)
        else {
            // Find all neighbors
            List<Position> neighbors = getNeighbors(pos, height);
            for (Position neighbor : neighbors) {
                if (placedSettler.contains(neighbor.toString()) || placedVillage.contains(neighbor.toString()))
                    return true;
            }
            return false;
        }
    }
    // FIXME Task 7


    /**
     * Given a state string, generate a set containing all move strings playable
     * by the current player.
     * <p>
     * A move is playable if it is valid.
     *
     * @param stateString a string representing a game state
     * @return a set of strings representing all moves the current player can play
     */
    public static Set<String> generateAllValidMoves(String stateString){
        Set<String> moves = new HashSet<>();

        int height = getBoardHeight(stateString);
        int playerNumber = getPlayerNumber(stateString);
        int playerId = getCurrentPlayerId(stateString);
        char phase = getPhase(stateString);

        List<List<String>> placed = getPlaced(stateString);
        List<String> placedSettler = placed.get(2 * playerId);
        List<String> placedVillage = placed.get(2 * playerId + 1);
        List<String> occupied = new ArrayList<>();
        for (int i = 0; i < placed.size(); i++) {
            occupied.addAll(placed.get(i));
        }

        int settlerAvailable = switch (playerNumber) {
            case 2 -> 30;
            case 3 -> 25;
            default -> 20;
        } - placedSettler.size();
        int villageAvailable = 5 - placedVillage.size();
        if (settlerAvailable == 0 && villageAvailable == 0)
            return moves;

        // Terrain Grid
        int[][] terrains = new int[height][height];
        for (int i = 0; i < height; i++) {
            if (i%2 == 0)
                terrains[i][height-1] = -1;
        }
        List<String> lands = getLands(stateString);
        for (String index : lands) {
            int x = Integer.parseInt(index.substring(0, index.indexOf(",")));
            int y = Integer.parseInt(index.substring(index.indexOf(",")+1));
            terrains[x][y] = 1; // Set terrain to land
        }

        // Check every position to move
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                // Unoccupied by any
                if (!occupied.contains(i + "," + j)) {
                    // Sea in Settle Phase
                    if (terrains[i][j] == 0 && phase == 'E' && settlerAvailable > 0) {
                        moves.add("S " + i + ',' + j);
                    }
                    else if (terrains[i][j] != -1){
                        List<Position> neighbors = getNeighbors(new Position(i,j), height);
                        for (Position neighbor : neighbors) {
                            if (placedSettler.contains(neighbor.toString()) || placedVillage.contains(neighbor.toString())) {
                                if (settlerAvailable > 0)
                                    moves.add("S " + i + ',' + j);
                                if (villageAvailable > 0 && terrains[i][j] == 1 && phase == 'E')
                                    moves.add("T " + i + ',' + j);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }
    // FIXME Task 8


    /**
     * Given a state string, determine whether it represents an end of phase state.
     * <p>
     * A phase is over when either of the following conditions hold:
     * - All resources (not including statuettes) have been collected.
     * - No player has any remaining valid moves.
     *
     * @param stateString a string representing a game state
     * @return true if the state is at the end of either phase and false otherwise
     */
    public static boolean isPhaseOver(String stateString){
        // Extract the resources from the state string
        String resources = stateString.substring(stateString.indexOf("r"), stateString.indexOf("p"));
        // Check if the resource is empty
        if (Arrays.stream(resources.substring(0, resources.indexOf("S")).split(" ")).noneMatch(s -> s.contains(","))) {
            return true; // If no resources are found, the phase is over
        }

        // Split the state string into individual player states
        String[] states = stateString.split("; *");

        // Initialize an ArrayList to hold Player objects
        ArrayList<Player> players = new ArrayList<>();

        // Iterate over the state strings and add Player objects to the list where the state string starts with 'p'
        for (String state : states) {
            if (state.charAt(0) == 'p') {
                players.add(new Player(state.split(" ")));
            }
        }

        // Construct a CurrentState object using the second state string
        // Parse the first state string to get the player number
        // Fetch the current player ID from the CurrentState object
        CurrentState currentState = new CurrentState(states[1].split(" "));
        int playerNumber = Integer.parseInt(states[0].split(" ")[2]);
        int currentPlayer = currentState.getPlayerId();

        // Check if there are no valid moves for the current player
        if (generateAllValidMoves(stateString).isEmpty()) {
            // Check if any other player has valid moves
            for (int i = 0; i < playerNumber; i++) {
                if (i == currentPlayer) continue; // Skip the current player

                // Initialize the temporary state to determine if another player's state can be moved
                String tempString = stateString.replaceFirst("c " + currentPlayer, "c " + i);
                // If any player has valid moves, the phase is not over
                if (generateAllValidMoves(tempString).size() != 0) {
                    return false;
                }
            }
            return true; // If no player has valid moves, the phase is over
        }

        // Calculate the number of settlers and villages based on the player count
        int settlerNum = 30 - (playerNumber - 2) * 5;
        int villageNum = 5;

        // Determine whether the player ends the game
        for (Player p : players) {
            // If any player does not have the required number of settlers or villages (in phase E), the phase is not over
            if (p.getSettlerCoords().size() != settlerNum) {
                return false;
            }
            else if (currentState.getPhase().equals("E") && p.getVillageCoords().size() != villageNum)
                return false;
        }
        return true; // If all conditions are met, the phase is over
    }

    // This helper function searches for a given string in an array of strings and returns its index
    public static int searchStr(String[] strings, String s) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(s)) {
                return i; // Found the string, return its index
            }
        }
        return -1; // String not found in the array
    }
    // FIXME Task 9


    /**
     * Given a state string and a move string, place the piece associated with the
     * move on the board. Ensure the player collects any corresponding resource or
     * statuettes.
     * <p>
     * Do not handle switching to the next player here.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a new state string achieved by placing the move on the board
     */
    public static String placePiece(String stateString, String moveString){
        // Before proceeding, check whether the move is valid and the state string is well-formed.
        if (!isMoveValid(stateString, moveString) || !isStateStringWellFormed(stateString)) {
            return stateString; // If either condition is not met, simply return the original state string
        }
        // Split the state string into individual player states
        String[] states = stateString.split("; *");

        // Get the player number and player ID from the first two states
        int playerNumber = Integer.parseInt(states[0].split(" ")[2]);
        int playerId = Integer.parseInt(states[1].split(" ")[1]);

        // Calculate the index of the current player's state within the states array
        int index = states.length - (playerNumber - playerId);

        // Extract the resources string from the last state in the array
        String resources = states[states.length - 1 - playerNumber];
        // Split the move string into an array
        String[] moveStr = moveString.split(" ");
        // Initialize a Resource object to hold the resource associated with the move, if any
        Resource resource = null;

        // Collect resources, resource points to delete
        if (resources.contains(moveStr[1])) {
            // Get the Resource object for the move
            resource = getResource(resources, moveStr[1]);
            // Remove the resource position from the resources string
            states[states.length - 1 - playerNumber] = removeResourcePosition(states[states.length - 1 - playerNumber], moveStr[1]);
        }

        // Create a Player object for the current player and add the resource to the player's resources
        Player player = new Player(states[index].split(" "));
        player.addResource(resource);

        // Add coordinates collected by the player
        if (moveString.charAt(0) == 'S') {
            player.addSettlerCoords(new Position(moveStr[1]));
        } else {
            player.addVillageCoords(new Position(moveString.split(" ")[1]));
        }

        // Update the player's state in the states array and join the states into a new state string
        states[index] = player.toString();
        return joinStates(states);
    }

    // This function joins an array of state strings into a single state string
    public static String joinStates(String[] states) {
        // Use a StringBuilder to efficiently build the state string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < states.length - 1; i++) {
            sb.append(states[i]).append("; ");
        }
        if (states.length > 0) {
            sb.append(states[states.length - 1]).append(";");
        }
        return sb.toString(); // Return the final state string
    }

    // This function removes the position of a resource from the resources string
    public static String removeResourcePosition(String resource, String position) {
        // Add a space to the resource string to ensure proper matching
        resource = resource + " ";
        String pattern = "( "+position+" )";
        StringBuffer operatorStr=new StringBuffer(resource + " ");

        // Create a Pattern and Matcher to find the position in the string
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(resource);

        // Use the Matcher to find and replace the position in the string
        while(m.find()) {
            operatorStr.replace(m.start(1),m.end(1)," ");
            m = p.matcher(operatorStr);
        }
        return operatorStr.toString().trim(); // Return the modified string, with trailing and leading spaces removed
    }

    // This function retrieves the corresponding Resource object for a given move from the resources string
    public static Resource getResource(String resources, String move) {
        // Split the resources string into an array
        String[] split = resources.split(" ");

        // Loop over the resources array, comparing each resource to the move
        for (int i = 1; i < split.length; i++) {
            Resource resource = Resource.fromChar(split[i]);
            for (int j = i + 1; j < split.length; j++) {
                if (!split[j].contains(",")) {
                    i = j - 1;
                    break;
                }
                if (split[j].equals(move)) {
                    return resource; // If a match is found, return the corresponding Resource object
                }
            }
        }
        return null; // If no match is found, return null
    }
    // FIXME Task 10


    /**
     * Given a state string, calculate the "Islands" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Islands" portion is calculated for each player as follows:
     * - If the player has pieces on 8 or more islands, they score 20 points.
     * - If the player has pieces on 7 islands, they score 10 points.
     * - No points are scored otherwise.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Islands" portion of
     * the score for each player
     */
    public static int[] calculateTotalIslandsScore(String stateString){
        int playerNum = getPlayerNumber(stateString);
        int[][] islandsMatrix = getIslandsMatrix(stateString);
        List<Integer> islandsStats = getIslandsStats(stateString);
        int[][] placedMatrix = getPlacedMatrix(stateString);

        // whether player reached each island
        boolean[][] reachedIslands = new boolean[playerNum][islandsStats.size()];
        for (int i = 0; i < placedMatrix.length; i++) {
            for (int j = 0; j < placedMatrix[i].length; j++) {
                if (placedMatrix[i][j] != -1 && islandsMatrix[i][j] != -1)
                    reachedIslands[placedMatrix[i][j]][islandsMatrix[i][j]] = true;
            }
        }

        // Calculate total islands score for each player based on rule
        int[] totalIslandsScore = new int[playerNum];
        for(int i = 0; i < reachedIslands.length; i++) {
            int totalIslandsNum = 0;
            for (boolean occupied : reachedIslands[i])
                if (occupied)
                    totalIslandsNum += 1;
            if (totalIslandsNum >= 8)
                totalIslandsScore[i] = 20;
            else if (totalIslandsNum == 7)
                totalIslandsScore[i] = 10;
            else
                totalIslandsScore[i] = 0;
        }

        return totalIslandsScore;
    }
    // FIXME Task 11


    /**
     * Given a state string, calculate the "Links" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * Players earn points for their chain of pieces that links the most
     * islands. For each island linked by this chain, they score 5 points.
     * <p>
     * Note the chain needn't be a single path. For instance, if the chain
     * splits into three or more sections, all of those sections are counted
     * towards the total.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Links" portion of
     * the score for each player
     */
    public static int[] calculateIslandLinksScore(String stateString){
        int height = getBoardHeight(stateString);
        int playerNum = getPlayerNumber(stateString);
        int[][] islandsMatrix = getIslandsMatrix(stateString);
        int[][] placedMatrix = getPlacedMatrix(stateString);
        boolean[][] visited = new boolean[height][height];      // Don't check if a piece is already visited

        int[] linksScore = new int[playerNum];
        for (int playerId = 0; playerId < playerNum; playerId++) {
            int longest = 0;
            for (int i = 0; i < height; i++)
                for (int j = 0; j < height; j++) {
                    if (placedMatrix[i][j] == playerId && !visited[i][j]) {
                        List<Integer> reached = new ArrayList<>();
                        visit(new Position(i, j), visited, reached, placedMatrix, islandsMatrix);
                        longest = Math.max(reached.size(), longest);
                    }
                }
            linksScore[playerId] = Math.max(longest * 5, linksScore[playerId]);
        }

        return linksScore;
    }

    /**
     * Using DFS to check all connected pieces
     *
     * @param pos Position of current piece
     * @param visited Position that visited
     * @param reached Islands this link have reached
     * @param placedMatrix Placed pieces on the board
     * @param islandsMatrix Islands of the board
     */
    private static void visit(Position pos, boolean[][] visited, List<Integer> reached, int[][] placedMatrix, int[][] islandsMatrix) {
        int x = pos.getX();
        int y = pos.getY();
        visited[x][y] = true;
        if (islandsMatrix[x][y] != -1 && !reached.contains(islandsMatrix[x][y]))
            reached.add(islandsMatrix[x][y]);   // Reach a new island
        List<Position> neighbors = getNeighbors(pos, islandsMatrix.length);
        for (Position neighbor : neighbors) {
            int i = neighbor.getX();
            int j = neighbor.getY();
            if (!visited[i][j] && placedMatrix[i][j] == placedMatrix[x][y]) {
                visit(neighbor, visited, reached, placedMatrix, islandsMatrix); // Visit next neighbor
            }
        }
    }
    // FIXME Task 11


    /**
     * Given a state string, calculate the "Majorities" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Majorities" portion is calculated for each island as follows:
     * - The player with the most pieces on the island scores the number
     *   of points that island is worth.
     * - In the event of a tie for pieces on an island, those points are
     *   divided evenly between those players rounding down. For example,
     *   if two players tied for an island worth 7 points, they would
     *   receive 3 points each.
     * - No points are awarded for islands without any pieces.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Majorities" portion
     * of the score for each player
     */
    public static int[] calculateIslandMajoritiesScore(String stateString){
        int playerNum = getPlayerNumber(stateString);
        int[][] islandsMatrix = getIslandsMatrix(stateString);
        List<Integer> islandsStats = getIslandsStats(stateString);
        int[][] placedMatrix = getPlacedMatrix(stateString);

        // Player's occupation on each island
        int[][] occupation = new int[islandsStats.size()][playerNum];
        for (int i = 0; i < placedMatrix.length; i++) {
            for (int j = 0; j < placedMatrix[i].length; j++) {
                if (placedMatrix[i][j] != -1 && islandsMatrix[i][j] != -1)
                    occupation[islandsMatrix[i][j]][placedMatrix[i][j]] += 1;
            }
        }

        // Calculate majority score for each island
        int[] majoritiesScore = new int[playerNum];
        for (int i = 0; i < occupation.length; i++) {
            int max = 0;
            for (int placedNum : occupation[i])
                max = Math.max(placedNum, max);
            // There are piece(s) on this island
            if (max > 0) {
                int count = 0;
                for (int placedNum : occupation[i])
                    if (placedNum == max)
                        count++;
                int score = islandsStats.get(i) / count;
                for (int playerId = 0; playerId < playerNum; playerId++) {
                    if (occupation[i][playerId] == max)
                        majoritiesScore[playerId] += score;
                }
            }
        }

        return majoritiesScore;
    }
    // FIXME Task 11


    /**
     * Given a state string, calculate the "Resources" and "Statuettes" portions
     * of the score for each player as if it were the end of a phase. The return
     * value is an integer array sorted by player number containing the calculated
     * score for the respective player.
     * <p>
     * Note that statuettes are not resources.
     * <p>
     * In the below "matching" means a set of the same resources.
     * <p>
     * The "Resources" portion is calculated for each player as follows:
     * - For each set of 4+ matching resources, 20 points are scored.
     * - For each set of exactly 3 matching resources, 10 points are scored.
     * - For each set of exactly 2 matching resources, 5 points are scored.
     * - If they have all four resource types, 10 points are scored.
     * <p>
     * The "Statuettes" portion is calculated for each player as follows:
     * - A player is awarded 4 points per statuette in their possession.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Resources" and "Statuettes"
     * portions of the score for each player
     */
    public static int[] calculateResourcesAndStatuettesScore(String stateString){
        int playerNum = getPlayerNumber(stateString);
        int[][] playerResources = getClaimedResources(stateString);
        int[] resourcesScore = new int[playerNum];

        for (int playerId = 0; playerId < playerNum; playerId++) {
            int[] resource = playerResources[playerId];
            int sum = 0;
            int count = 0;
            for (int i = 0; i < 4; i++) {
                if (resource[i] > 0)
                    count++;

                if (resource[i] >= 4)
                    sum += 20;
                else if (resource[i] == 3)
                    sum += 10;
                else if (resource[i] == 2)
                    sum += 5;
            }
            if (count == 4)
                sum += 10;
            sum += (resource[4] * 4);   // Statuette Score

            resourcesScore[playerId] = sum;
        }
        return resourcesScore;
    }
    // FIXME Task 11


    /**
     * Given a state string, calculate the scores for each player as if it were
     * the end of a phase. The return value is an integer array sorted by player
     * number containing the calculated score for the respective player.
     * <p>
     * It is recommended to use the other scoring functions to assist with this
     * task.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated scores for each player
     */
    public static int[] calculateScores(String stateString){
        int playerNum = getPlayerNumber(stateString);
        int[] totalIslandsScore = calculateTotalIslandsScore(stateString);
        int[] linksScore = calculateIslandLinksScore(stateString);
        int[] majoritiesScore = calculateIslandMajoritiesScore(stateString);
        int[] resourcesScore = calculateResourcesAndStatuettesScore(stateString);

        int[] scores = new int[playerNum];
        for (int playerId = 0; playerId < playerNum; playerId++)
            scores[playerId] = totalIslandsScore[playerId] + linksScore[playerId] + majoritiesScore[playerId] + resourcesScore[playerId];
        return scores;
    }
    // FIXME Task 11


    /**
     * Given a state string representing an end of phase state, return a new state
     * achieved by following the end of phase rules. Do not move to the next player
     * here.
     * <p>
     * In the Exploration Phase, this means:
     * - The score is tallied for each player.
     * - All pieces are removed from the board excluding villages not on stone circles.
     * - All resources and statuettes remaining on the board are removed. All resources are then
     *   randomly redistributed between the stone circles.
     * <p>
     * In the Settlement Phase, this means:
     * - Only the score is tallied and added on for each player.
     *
     * @param stateString a string representing a game state at the end of a phase
     * @return a string representing the new state achieved by following the end of phase rules
     */
    public static String endPhase(String stateString){
        // Split state into separate states
        String[] states = stateString.split("; *");

        // Index of first player state
        int pIndex = -1;
        // List to hold player states
        List<Player> players = new ArrayList<>();

        // Get all player states
        for (int i = 0; i < states.length; i++) {
            if (states[i].charAt(0) == 'p') {
                players.add(new Player(states[i].split(" ")));
                if (pIndex < 0) pIndex = i;
            }
        }

        // Calculate scores
        int[] scores = calculateScores(stateString);
        // Handle Exploration Phase
        if (states[1].contains("E")) {
            states = distributeResources(stateString).split("; *"); // Distribute resources
            Resources resources = new Resources(states[pIndex - 1].split(" ")); // Create Resources object
            CurrentState currentState = new CurrentState(states[1].split(" ")); // Create CurrentState object
            currentState.setPhase("S"); // Change phase to Settlement
            states[1] = currentState.toString(); // Update state string

            // Compute resources and update player states
            for (int i = 0, index = pIndex; i < players.size(); i++) {
                players.get(i).calculateResources(resources); // Calculate resources
                players.get(i).clearResourcesScore(); // Clear resource scores
                players.get(i).removeNotResources(resources); // Remove non-resource items
                players.get(i).setScore(scores[i]); // Set scores
                states[index ++] = players.get(i).toString(); // Update player states in state array
            }
            return joinStates(states); // Return new state string
        }

        // Handle Settlement Phase
        for (int i = 0, index = pIndex; i < players.size(); i++) {
            players.get(i).addScore(scores[i]); // Add scores to players
            states[index ++] = players.get(i).toString(); // Update player states in state array
        }
        return joinStates(states); // Return new state string
    }

    // Reads the stone circles from the given string array and returns a list of positions
    private static List<Position> readStoneCircles(String[] stones) {
        // List to hold stone circle positions
        List<Position> stoneList = new ArrayList<>();

        // Add each stone circle position to the list
        for (int i = 1; i < stones.length; i++) {
            stoneList.add(new Position(stones[i]));
        }
        return stoneList; // Return list of stone circle positions
    }
    // FIXME Task 12


    /**
     * Given a state string and a move string, apply the move to the board.
     * <p>
     * If the move ends the phase, apply the end of phase rules.
     * <p>
     * Advance current player to the next player in turn order that has a valid
     * move they can make.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a string representing the new state after the move is applied to the board
     */
    public static String applyMove(String stateString, String moveString) {
        // Apply the move and split the resulting state string
        String[] states = placePiece(stateString, moveString).split("; *");

        // Create CurrentState object from state string
        String[] start = states[0].split(" ");
        CurrentState currentState = new CurrentState(states[1].split(" "));
        int playerNumber = Integer.parseInt(start[2]); // Get the player number

        // If phase is over, apply end of phase rules
        if (isPhaseOver(joinStates(states))) {
            String s = endPhase(joinStates(states)); // End phase and get new state string

            // If the phase is still over, return the new state string
            if (isPhaseOver(s)) {
                return s;
            }

            // If the phase is not over, update player and state string
            states = s.split("; *");
            currentState = new CurrentState(states[1].split(" "));
            currentState.nextPlayer(playerNumber); // Switch to next player
            states[1] = currentState.toString(); // Update state string

            // Keep switching to the next player until a valid move is found
            while (generateAllValidMoves(joinStates(states)).isEmpty()) {
                currentState.nextPlayer(playerNumber);
                states[1] = currentState.toString();
            }
            return joinStates(states); // Return new state string
        }

        // If phase is not over, update player and state string
        int index = currentState.getPlayerId();
        currentState.nextPlayer(playerNumber); // Switch to next player
        states[1] = currentState.toString(); // Update state string

        // Keep switching to the next player until a valid move is found
        while (generateAllValidMoves(joinStates(states)).isEmpty()) {
            index = (index + 1) % playerNumber;
            currentState.setPlayerId(index);
            states[1] = currentState.toString();
        }
        return joinStates(states); // Return new state string
    }
    // FIXME Task 13


    /**
     * Given a state string, returns a valid move generated by your AI.
     * <p>
     * As a hint, generateAllValidMoves() may prove a useful starting point,
     * maybe if you could use some form of heuristic to see which of these
     * moves is best?
     * <p>
     * Your AI should perform better than randomly generating moves,
     * see how good you can make it!
     *
     * @param stateString a string representing a game state
     * @return a move string generated by an AI
     */
    public static String generateAIMove(String stateString){
        List<String> moves = new ArrayList<>(generateAllValidMoves(stateString));
        Board board = new Board(stateString);
        int height = board.getHeight();
        int currentPlayerId = board.getCurrentPlayer();

        // Priority benchmark of each available move
        double[] priorities = new double[moves.size()];

        // Top Priority: Resource Claim (Settler > Village, Wanted > Unwanted)
        for (int i = 0; i < moves.size(); i++) {
            String move = moves.get(i);
            char pieceType = move.charAt(0);
            Position pos = new Position(move.substring(2));

            if (board.getSpot(pos).getTerrain() == 2) {
                priorities[i] += 1000;

                // If there are other players near this resource
                List<Position> neighbors = getNeighbors(pos, height);
                for (Position neighbor : neighbors) {
                    if (board.getSpot(neighbor).getOccupier() != currentPlayerId)
                        priorities[i] *= 1.3;
                }
            }

            if (pieceType == 'T')
                priorities[i] *= 0.9;

            if (board.players[currentPlayerId].getSettlersAvailable() < 10)
                priorities[i] *= Math.pow(1.2, 10 - board.players[currentPlayerId].getSettlersAvailable());
        }

        // High Priority: Resource Distance (2 Steps, Village > Settler)
        // One-Step Distance
        int[][] firstStep = new int[height][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++) {
                if (board.isSpot(i, j)) {
                    List<Position> neighbors = getNeighbors(new Position(i, j), height);
                    for (Position neighbor : neighbors) {
                        if (!board.getSpot(neighbor).isOccupied()) {
                            firstStep[i][j] += switch (board.getSpot(neighbor).getTerrain()) {
                                case 0 -> 2;
                                case 1 -> 5;
                                case 2 -> 30;
                                default ->
                                        throw new IllegalStateException("Unexpected value: " + board.getSpot(neighbor).getTerrain());
                            };
                        }
                        if (board.getSpot(neighbor).getOccupier() == currentPlayerId) {
                            firstStep[i][j] -= 5;
                        }
                    }
                }
                else
                    firstStep[i][j] = -1;

            }

        // Two-Step Distance
        for (int i = 0; i < moves.size(); i++) {
            String move = moves.get(i);
            char pieceType = move.charAt(0);
            Position pos = new Position(move.substring(2));

            List<Position> neighbors = getNeighbors(pos, height);
            for (Position neighbor : neighbors) {
                if (!board.getSpot(neighbor).isOccupied()) {
                    priorities[i] += firstStep[neighbor.getX()][neighbor.getY()]
                            + switch (board.getSpot(neighbor).getTerrain()) {
                        case 0 -> 5;
                        case 1 -> 10;
                        case 2 -> 90;
                        default ->
                                throw new IllegalStateException("Unexpected value: " + board.getSpot(neighbor).getTerrain());
                    };
                }
                if (pieceType == 'T' && board.getSpot(pos).getTerrain() == 1) {
                    priorities[i] += 10 * board.players[currentPlayerId].getVillagesAvailable();
                }
            }
        }

        // Medium Priority: Scoring Move (Direct Scoring)
//        int totalScoreBefore = calculateIslandLinksScore(stateString)[currentPlayerId];
//        for (int i = 0; i < moves.size(); i++) {
//            int totalScoreAfter = calculateIslandLinksScore(placePiece(stateString, moves.get(i)))[currentPlayerId];
//            priorities[i] += (totalScoreAfter - totalScoreBefore) * 30.0;
//        }


        // Low Priority: Linking
        for (int i = 0; i < moves.size(); i++) {
            String move = moves.get(i);
            char pieceType = move.charAt(0);
            Position pos = new Position(move.substring(2));

            List<Position> neighbors = getNeighbors(pos, height);
            for (Position neighbor : neighbors) {
                if (board.getSpot(neighbor).getOccupier() == currentPlayerId) {
                    if (pieceType == 'T' && board.getSpot(neighbor).isVillage())
                        priorities[i] *= 0.2;
                    else
                        priorities[i] += 5;
                }
            }
        }

//        for (int i = 0; i < priorities.length; i++)
//            System.out.println(moves.get(i) + "\t" + priorities[i]);

        // Return the highest priority move
        int best = 0;
        for (int i = 0; i < priorities.length; i++)
            if (priorities[i] > priorities[best])
                best = i;
        return moves.get(best); // FIXME Task 16
    }

    public static String generateRandomMove(String stateString) {
        List<String> moves = new ArrayList<>(generateAllValidMoves(stateString));
        Random random = new Random();

        return moves.get(random.nextInt(moves.size()));
    }
}
