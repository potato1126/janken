package oit.is.z3614.kaizi.janken.model;

public class Janken {
  private String playerHand;
  private String cpuHand;
  private String result;

  public Janken(String playerHand) {
    this.playerHand = playerHand;
    this.cpuHand = "Gu";
    this.result = judge(this.playerHand, this.cpuHand);
  }

  private String judge(String player, String cpu) {
    if (player.equals(cpu)) {
      return "あいこ";
    } else if (player.equals("Gu") && cpu.equals("Choki")
        || (player.equals("Pa") && cpu.equals("Gu"))
        || (player.equals("Choki") && cpu.equals("Pa"))) {
      return "勝ち";
    }
    return "負け";
  }

  public String getPlayerHand() {
    return playerHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }

}
