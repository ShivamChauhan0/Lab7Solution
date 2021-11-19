package pkgGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.UUID;

import eNum.eBonusType;
import eNum.eMoveResult;
import eNum.eMoveType;
import pkgCore.Dictionary;
import pkgCore.Word;

public class ScoreWord {

	private UUID ScoreWordID;
	private eMoveResult moveResult;
	private eMoveType moveType;
	private int Score;
	private Word word;
	private String strWord;
	private Dictionary dictionary;
	private ArrayList<Space> tiles;
	private Board board;
	private Space minSpace;
	private Space maxSpace;

	public ScoreWord(Dictionary Dictionary, ArrayList<Space> Tiles, Board Board, eMoveType MoveType) {
		this.ScoreWordID = UUID.randomUUID();
		this.dictionary = Dictionary;
		this.tiles = Tiles;
		this.board = Board;
		this.moveType = MoveType;

		switch (this.moveType) {
		case VERTICAL:
			minSpace = Tiles.stream().min(Comparator.comparing(Space::getRow)).orElseThrow(NoSuchElementException::new);

			maxSpace = Tiles.stream().max(Comparator.comparing(Space::getRow)).orElseThrow(NoSuchElementException::new);
			break;

		case HORIZONTAL:
			minSpace = Tiles.stream().min(Comparator.comparing(Space::getCol)).orElseThrow(NoSuchElementException::new);
			maxSpace = Tiles.stream().max(Comparator.comparing(Space::getCol)).orElseThrow(NoSuchElementException::new);

			break;
		}

		this.moveResult = Score();

	}

	private eMoveResult Score() {
		if (spaceInWord(this.board, this.moveType, this.minSpace, this.maxSpace)) {
			moveResult = eMoveResult.SpaceInWord;
			return moveResult;
		}

		strWord = this.buildWord(this.board, this.minSpace, this.maxSpace, this.moveType);
		if (strWord == null) {
			moveResult = eMoveResult.SingleSpace;
			return moveResult;
		}

		this.word = this.dictionary.findWord(strWord);
		if (this.word == null)
			return eMoveResult.NotAWord;

		this.Score = CalculateWordScore();

		return eMoveResult.GoodMove;
	}

	public ScoreWord(Dictionary Dictionary, ArrayList<Space> Tiles, Board Board, eMoveResult MoveResult) {
		this.ScoreWordID = UUID.randomUUID();
		this.dictionary = Dictionary;
		this.tiles = Tiles;
		this.board = Board;
		this.moveResult = MoveResult;
	}

	public eMoveResult getMoveResult() {
		return moveResult;
	}

	public void setMoveResult(eMoveResult MoveResult) {
		moveResult = MoveResult;
	}

	public int getScore() {
		return Score;
	}

	public UUID getScoreWordID() {
		return ScoreWordID;
	}

	public void SetWord(String strWord) {
		this.word = dictionary.findWord(strWord);
	}

	public boolean isWord() {
		return this.word != null;
	}

	public ArrayList<Space> getTiles() {
		return tiles;
	}

	/**
	 * CalculateScore - Calculate the word score.
	 * Each space on the board could have a BonusSpace, but it could be null.
	 * Check BonusSpace.  Is it used?
	 * Process DoubleLetter/Star and TripleLetter first
	 * Process DoubleWord and TripleWord last
	 * @return - TotalScore for the word
	 * 
	 */
	private int CalculateWordScore() {
		int iTotalScore = 0;
        int lengthOfWord = word.getWord().length();
        int initialRowPosition = minSpace.getRow();
        int finalRowPosition = maxSpace.getRow();
        int rowDifference = initialRowPosition – finalRowPosition;
        if (rowDifferenece == 0) {
               int lockedSpace = minSpace.getRow();
               for (int x = 0; x < lengthOfWord; x++) {
                     int horiz = minSpace.getCol() + x;
                     Space space = board.getPuzzle()[startingSpace][horiz];
                     BonusSquare bonusSquare = space.getBonusSquare();
                     Letter letter = new Letter(word.getWord().charAt(x));
                     int l = letter.getiScore();
                     if (bonusSquare.isUsed() != true && bonusSquare != null) {
                            eBonusType bonusType = bonusSquare.getBonusType();
                            if (bonusType == eBonusType.DoubleLetter) {
                                  l = l * 2;
                            }
                            if (bonusType == eBonusType.TripleLetter) {
                                  l = l* 3;
                            }
                            iTotalScore += letterScore;
                     }
               }
               for (int x = 0; x < lengthOfWord; x++) {
                     int horiz = minSpace.getCol() + x;
                     Space space = board.getPuzzle()[startingSpace][horiz];
                     BonusSquare bonusSquare = space.getBonusSquare();
                     if (bonusSquare.isUsed() != true && bonusSquare != null) {
                            eBonusType bonusType = bonusSquare.getBonusType();
                            if (bonusType == eBonusType.DoubleWord || bonusType == eBonusType.Star) {
                                  iTotalScore = iTotalScore * 2;
                            }
                            if (bonusType == eBonusType.TripleWord) {
                                  iTotalScore = iTotalScore * 3;
                            }
                     }
               }
        } else {
               int lockedSpace = minSpace.getCol();
               for (int y = 0; y < lengthOfWord; y++) {
                     int vert = minSpace.getRow() + y;
                     Space space = board.getPuzzle()[vert][startingSpace];
                     BonusSquare bonusSquare = space.getBonusSquare();
                     Letter letter = new Letter(word.getWord().charAt(y));
                     int l = letter.getiScore();
                     if (bonusSquare.isUsed() != true && bonusSquare != null) {
                            eBonusType bonusType = bonusSquare.getBonusType();
                            if (bonusType == eBonusType.DoubleLetter) {
                                  l = l * 2;
                            }
                            if (bonusType == eBonusType.TripleLetter) {
                                  l = l * 3;
                            }
                            iTotalScore += letterScore;
                     }
               }
               for (int y = 0; y < lengthOfWord; y++) {
                     int horiz = minSpace.getCol() + y;
                     Space space = board.getPuzzle()[vert][ startingSpace];
                     BonusSquare bonusSquare = space.getBonusSquare();
                     if (bonusSquare.isUsed() != true && bonusSquare != null) {
                            eBonusType bonusType = bonusSquare.getBonusType();
                            if (bonusType == eBonusType.DoubleWord || bonusType == eBonusType.Star) {
                                  iTotalScore = iTotalScore * 2;
                            }
                            if (bonusType == eBonusType.TripleWord) {
                                  iTotalScore = iTotalScore * 3;
                            }
                     }
               }
        }
        return iTotalScore;
        }
	}

	private Space FindSpace(Board b, int Row, int Col) {
		for (Space s : this.tiles) {
			if (s.getRow() == Row && s.getCol() == Col)
				return s;
		}
		return b.getPuzzle()[Row][Col];
	}

	private String buildWord(Board b, Space minSpace, Space maxSpace, eMoveType MoveType) {
		StringBuilder sb = new StringBuilder();
		switch (MoveType) {
		case VERTICAL:
			if (maxSpace.getRow() - minSpace.getRow() == 0)
				// There are no words, single space
				return null;
			for (int iLetter = minSpace.getRow(); iLetter < maxSpace.getRow() + 1; iLetter++) {
				Space s = FindSpace(b, iLetter, minSpace.getCol());
				sb.append(s.getLetter().getChLetter());
			}
			return sb.toString();
		case HORIZONTAL:
			if (maxSpace.getCol() - minSpace.getCol() == 0)
				// There are no words, single space
				return null;
			for (int iLetter = minSpace.getCol(); iLetter < maxSpace.getCol() + 1; iLetter++) {
				Space s = FindSpace(b, minSpace.getRow(), iLetter);
				sb.append(s.getLetter().getChLetter());
			}
			return sb.toString();
		}
		return null;
	}

}
