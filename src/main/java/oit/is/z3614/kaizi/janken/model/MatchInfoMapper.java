package oit.is.z3614.kaizi.janken.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) values(#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);

  @Select("SELECT * FROM matchinfo WHERE isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  @Select("SELECT * FROM matchinfo WHERE isActive = TRUE AND user1 = #{user1} AND user2 = #{user2} ORDER BY id LIMIT 1")
  MatchInfo selectWaitingMatchInfo(@Param("user1") int user1, @Param("user2") int user2);

  @Update("UPDATE matchinfo SET isActive = FALSE WHERE id = #{id}")
  void updateInactive(int id);
}
