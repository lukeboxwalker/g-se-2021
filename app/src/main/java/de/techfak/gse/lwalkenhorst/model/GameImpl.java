package de.techfak.gse.lwalkenhorst.model;

import java.beans.PropertyChangeListener;

public class GameImpl implements Game {

    private final PropertyListenerSupport propertyListenerSupport;
    private final TurnValidator turnValidator;
    private final RuleManagerImpl ruleManager;
    private final BoardImpl board;

    private final GameStrategy gameStrategy;
    private Score score;
    private Round round;

    public GameImpl(final BoardImpl board, final TurnValidator turnValidator) {
        this.board = board;
        this.score = new Score();
        this.gameStrategy = new SinglePlayerStrategy();
        this.turnValidator = turnValidator;
        this.ruleManager = new RuleManagerImpl(board);
        propertyListenerSupport = new PropertyListenerSupport(this);
    }

    @Override
    public void play() {
        updateScore();
        enterFirstRound();
    }

    @Override
    public void applyTurn(final Turn turn) throws InvalidTurnException {
        if (!turn.isEmpty()) {
            turnValidator.validateTurn(turn, getDiceResult());
            board.cross(turn.getPositionsToCross());
            updateScore();
        }
        enterNextRound();
    }

    private void enterFirstRound() {
        final Round oldRound = round;
        round = Round.enterFirst(gameStrategy);
        propertyListenerSupport.firePropertyChange(PropertyChange.ROUND, oldRound, round);
    }

    private void updateScore() {
        final Score oldScore = score;
        score = ruleManager.calculatePoints();
        propertyListenerSupport.firePropertyChange(PropertyChange.SCORE, oldScore, score);
        propertyListenerSupport.firePropertyChange(PropertyChange.FINISHED, false, ruleManager.isGameFinished());
    }

    private void enterNextRound() {
        if (!ruleManager.isGameFinished()) {
            final Round oldRound = round;
            round = round.next(gameStrategy);
            propertyListenerSupport.firePropertyChange(PropertyChange.ROUND, oldRound, round);
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
    public DiceResult getDiceResult() {
        return round.getDiceResult();
    }

    @Override
    public void addListener(final PropertyChange propertyChange, final PropertyChangeListener listener) {
        propertyListenerSupport.addPropertyChangeListener(propertyChange, listener);
    }
}
