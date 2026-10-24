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

@Controller
public class jankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;
  @Autowired
  MatchMapper matchMapper;

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
    model.addAttribute("login_user", loginUser);
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    return "janken.html";
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
