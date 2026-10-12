package oit.is.z3614.kaizi.janken.model;

public class Janken {
  private String playerHand;
  private String cpuHand;
  private String result;

  public Janken(String playerHand) {
    this.playerHand = playerHand;
    this.cpuHand = "グー";
    this.result = judge(this.playerHand, this.cpuHand);
  }

  private String judge(String player, String cpu) {
    if (player.equals(cpu)) {
      return "あいこ";
    } else if (player.equals("グー") && cpu.equals("チョキ")
        || (player.equals("パー") && cpu.equals("グー"))
        || (player.equals("チョキ") && cpu.equals("パー"))) {
      return "勝ち";
    }
    return "負け";
  }

  public String getPlayerHand() {
    return playerHand;
  }

  public String getCpuHand(){
    return cpuHand;
  }

  public String getResult() {
    return result;
  }

}
