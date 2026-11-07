package oit.is.z3614.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3614.kaizi.janken.model.Match;
import oit.is.z3614.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);
  private volatile boolean dbUpdated = false;

  @Autowired
  MatchMapper matchMapper;

  public void syncInsertMatch(Match match) {
    matchMapper.insertMatch(match);
    this.dbUpdated = true;
  }

  @Async
  public void asyncShowResult(SseEmitter emitter) {
    try {
      while (true) {
        if (!dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        ArrayList<Match> results = matchMapper.selectActiveMatches();
        emitter.send(results);
        dbUpdated = false;
        for (Match m : results) {
          matchMapper.updateInactive(m.getId());
        }
        break;
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
  }
}
