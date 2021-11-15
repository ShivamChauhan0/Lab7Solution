package pkgGame;

import java.util.ArrayList;
import java.util.UUID;

import eNum.eMoveResult;

public class ScoreMove {

	private UUID ScoreMoveID;
	private ArrayList<ScoreWord> arrScoreWord = new ArrayList<ScoreWord>();
	public boolean alltilesused = false;

	public ScoreMove() {
		super();
		ScoreMoveID = UUID.randomUUID();
	}

	public UUID getScoreMoveID() {
		return ScoreMoveID;
	}

	public void AddScoreWord(ScoreWord sw) {
		arrScoreWord.add(sw);
	}

	public ArrayList<ScoreWord> getArrScoreWord() {
		return arrScoreWord;
	}
	
	public boolean isalltilesused() {
		return alltilesused;
	}

	public int CalculateScoreMove() {
		int totalscore = 0;
		for(ScoreWord scoreWord : arrScoreWord) {
			totalscore += scoreWord.getScore();
		}
		if (alltilesused) {
			totalscore += 50;
		}
		return totalscore;
	}
	
	public eMoveResult findMoveResult()
	{
		for (ScoreWord SM: arrScoreWord)
		{
			if (SM.getMoveResult() != eMoveResult.GoodMove)
			{
				return SM.getMoveResult();
			}
		}
		return eMoveResult.GoodMove;
	}

}
