package oit.is.z3614.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

  @Select("SELECT * FROM matches")
  ArrayList<Match> selectAllMatches();

  @Insert("insert into matches (user1, user2, user1Hand, user2Hand) values(#{user1},#{user2},#{user1Hand},#{user2Hand})")
  void insertMatch(Match match);
}
