package de.techfak.gse.lwalkenhorst.model;

import de.techfak.gse.lwalkenhorst.event.EndGameEvent;
import de.techfak.gse.lwalkenhorst.event.EventRegister;
import de.techfak.gse.lwalkenhorst.event.EventSupport;
import de.techfak.gse.lwalkenhorst.event.RoundEvent;
import de.techfak.gse.lwalkenhorst.event.ScoreEvent;

public class GameImpl implements Game {

    private final EventSupport eventSupport;
    private final TurnValidator turnValidator;
    private final RuleManagerImpl ruleManager;
    private final BoardImpl board;

    private final GameStrategy gameStrategy;
    private Score score;
    private Round round;

    public GameImpl(final BoardImpl board, final TurnValidator turnValidator, final GameStrategy gameStrategy) {
        this.board = board;
        this.score = new Score();
        this.gameStrategy = gameStrategy;
        this.turnValidator = turnValidator;
        this.ruleManager = new RuleManagerImpl(board);
        this.eventSupport = new EventSupport();
    }

    @Override
    public void play() {
        updateScore();
        gameStrategy.firstRound(this::enterFirstRound);
    }

    @Override
    public void applyTurn(final Turn turn) throws InvalidTurnException {
        if (!turn.isEmpty()) {
            turnValidator.validateTurn(turn, getDiceResult());
            board.cross(turn.getPositionsToCross());
            updateScore();
        }
        gameStrategy.nextRound(this.round, this.score, this::enterNextRound);
    }

    private void enterFirstRound(final Round round) {
        this.round = round;
        final RoundEvent roundEvent = new RoundEvent(round.getIntValue());
        eventSupport.fireEvent(roundEvent);
    }

    private void updateScore() {
        score = ruleManager.calculatePoints();
        final ScoreEvent scoreEvent = new ScoreEvent(score.getValue(), getRuleManger().getFullColumns());
        eventSupport.fireEvent(scoreEvent);

        if (ruleManager.isGameFinished()) {
            final EndGameEvent endGameEvent = new EndGameEvent();
            eventSupport.fireEvent(endGameEvent);
        }
    }

    private void enterNextRound(final Round round) {
        if (!ruleManager.isGameFinished()) {
            this.round = round;
            final RoundEvent roundEvent = new RoundEvent(round.getIntValue());
            eventSupport.fireEvent(roundEvent);
        }
    }

    @Override
    public RuleManager getRuleManger() {
        return ruleManager;
    }

    @Override
    public Score getPoints() {
        return score;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public int getRound() {
        return round.getIntValue();
    }

    @Override
    public DiceResult getDiceResult() {
        return round.getDiceResult();
    }

    @Override
    public EventRegister event() {
        return eventSupport;
    }
}
