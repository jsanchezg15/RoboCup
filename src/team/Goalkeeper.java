package src.team;

public class Goalkeeper extends Player {

    public Goalkeeper(String team_name, int num) {
        super(team_name, num);
        super.init_cmd = "(init " + team_name + "(goalie))";
    }

}
