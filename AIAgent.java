import java.util.*;

public class AIAgent{
  Random rand;

  public AIAgent(){
    rand = new Random();
  }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

  public Move randomMove(Stack possibilities){

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move : "+moveID);
    for(int i=1;i < (possibilities.size()-(moveID));i++){
      possibilities.pop();
    }
    Move selectedMove = (Move)possibilities.pop();
    return selectedMove;
  }
/*
    Next Best MOVE
*/
public Move nextBestMove(Stack movesWhite, Stack movesBlack) {
    Stack random = (Stack) movesWhite.clone();
    Stack blackStackM = (Stack) movesBlack.clone();
    Move bestMove = null;
    Move whiteMove;
    Move presentMove;
    Square blackPosition;
    int strength = 0;
    int chosenPieceStrength = 0;

    while (!movesWhite.empty()) {
        whiteMove = (Move) movesWhite.pop();
        presentMove = whiteMove;

        //check if the centre of the board is occupied or not (co ordinates)
        if ((presentMove.getStart().getYC() < presentMove.getLanding().getYC())
        && (presentMove.getLanding().getXC() == 3) && (presentMove.getLanding().getYC() == 3)
        || (presentMove.getLanding().getXC() == 4) && (presentMove.getLanding().getYC() == 3)
        || (presentMove.getLanding().getXC() == 3) && (presentMove.getLanding().getYC() == 4)
        || (presentMove.getLanding().getXC() == 4) && (presentMove.getLanding().getYC() == 4)) {

          strength = 1;

            //updating the best move
            if (strength > chosenPieceStrength) {
                chosenPieceStrength = strength;
                bestMove = presentMove;
            }
        }

        //compare white landing positions to black positions, return bestmove if available or random if not.
        while (!blackStackM.isEmpty()) {
            strength = 0;
            blackPosition = (Square) blackStackM.pop();
            if ((presentMove.getLanding().getXC() == blackPosition.getXC()) && (presentMove.getLanding().getYC() == blackPosition.getYC())) {

                //Assign strength to pieces
                if (blackPosition.getName().equals("BlackQueen")) {
                    strength = 9;
                } else if (blackPosition.getName().equals("BlackRook")) {
                    strength = 5;
                } else if (blackPosition.getName().equals("BlackBishop") || blackPosition.getName().equals("BlackKnight")) {
                    strength = 3;
                } else if (blackPosition.getName().equals("BlackPawn")) {
                    strength = 1;
                } else {
                    strength = 15;
                }
            }
            //updating the best move
            if (strength > chosenPieceStrength) {
                chosenPieceStrength = strength;
                bestMove = presentMove;
            }
        }
        //reloading the black squares
        blackStackM = (Stack) movesBlack.clone();
    }

    // Make the best move if not available make a random move.
    if (chosenPieceStrength > 0) {
        System.out.println("Selected AI Agent - Next best move: " +chosenPieceStrength);
        return bestMove;
    }

    return randomMove(random);

}

  public Move twoLevelsDeep(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }
}
