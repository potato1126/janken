package oit.is.z3614.kaizi.janken.controller;

import java.util.ArrayList;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3614.kaizi.janken.model.Entry;
import oit.is.z3614.kaizi.janken.model.Janken;
import oit.is.z3614.kaizi.janken.model.User;
import oit.is.z3614.kaizi.janken.model.UserMapper;
import oit.is.z3614.kaizi.janken.model.Match;
import oit.is.z3614.kaizi.janken.model.MatchMapper;
import oit.is.z3614.kaizi.janken.model.MatchInfo;
import oit.is.z3614.kaizi.janken.model.MatchInfoMapper;

@Controller
public class jankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;
  @Autowired
  MatchMapper matchMapper;
  @Autowired
  MatchInfoMapper matchInfoMapper;

  /**
   *
   * @param model Thymeleafにわたすデータを保持するオブジェクト
   * @param prin  ログインユーザ情報が保持されるオブジェクト
   * @return
   */

  @GetMapping("/janken")
  public String janken(ModelMap model, Principal prin) {
    String loginUser = prin.getName(); // ログインユーザ情報
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    ArrayList<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatchInfo();
    model.addAttribute("login_user", loginUser);
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("activeMatches", activeMatches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    User enemy = userMapper.selectById(id);

    model.addAttribute("login_user", prin.getName());
    model.addAttribute("enemy", enemy);
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam String hand, @RequestParam Integer id, Principal prin, ModelMap model) {
    User me = userMapper.selectByname(prin.getName());
    MatchInfo waiting = matchInfoMapper.selectWaitingMatchInfo(id, me.getId());

    if (waiting == null) {
      MatchInfo matchInfo = new MatchInfo();
      matchInfo.setUser1(me.getId());
      matchInfo.setUser2(id);
      matchInfo.setUser1Hand(hand);
      matchInfo.setIsActive(true);
      matchInfoMapper.insertMatchInfo(matchInfo);
    } else {
      Match match = new Match();
      match.setUser1(waiting.getUser1());
      match.setUser2(me.getId());
      match.setUser1Hand(waiting.getUser1Hand());
      match.setUser2Hand(hand);
      match.setIsActive(true);
      matchMapper.insertMatch(match);

      matchInfoMapper.updateInactive(waiting.getId());
    }

    model.addAttribute("login_user", prin.getName());
    return "wait.html";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {

    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("janken", janken);
    return "janken.html";
  }
}
