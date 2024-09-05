package comp1110.ass2;


/**
 * Created by Zeqi Gao on 18/5/2023
 * Modified by Zeqi Gao on 19/5/2023
 */
public class CurrentState {
    private String statement;
    private Integer playerId;
    private String phase;

    public CurrentState(String[] states) {
        this.statement = states[0];
        this.playerId = Integer.parseInt(states[1]);
        this.phase = states[2];
    }

    public String getStatement() {
        return statement;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return statement + " " + playerId + " " + phase;
    }

    public void nextPlayer(int playerNumber) {
        this.playerId = (this.playerId + 1) % playerNumber;
    }

    public void updatePhase() {
        this.phase = this.phase.equals("E") ? "S" : "E";
    }
}