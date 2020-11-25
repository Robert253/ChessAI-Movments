import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/*
  Robert Foster - X17140404
  Chess game (AI to be included)
*/
public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
  	int startX;
  	int startY;
  	int initialX;
  	int initialY;
  	JPanel panels;
  	JLabel pieces;
    Boolean whiteStart;
    Boolean checkTurn;


    public ChessProject(){
        Dimension boardSize = new Dimension(600, 600);

        //Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }

        // Setting up the Initial Chess board.
		for(int i=8;i < 16; i++){
       		pieces = new JLabel( new ImageIcon("WhitePawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(0);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(1);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(6);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(2);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(5);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKing.png") );
		panels = (JPanel)chessBoard.getComponent(3);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
		panels = (JPanel)chessBoard.getComponent(4);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(7);
	    panels.add(pieces);
		for(int i=48;i < 56; i++){
       		pieces = new JLabel( new ImageIcon("BlackPawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(56);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(57);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(62);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(58);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(61);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKing.png") );
		panels = (JPanel)chessBoard.getComponent(59);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackQueen.png") );
		panels = (JPanel)chessBoard.getComponent(60);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(63);
	    panels.add(pieces);
      checkTurn = false;
      whiteStart = true;
    }

	/*
		This method checks if there is a piece present on a particular square.
	*/
	private Boolean piecePresent(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if(c instanceof JPanel){
			return false;
		}
		else{
			return true;
		}
	}

	/*
		This is a method to check if a piece is a Black piece.
	*/
	private Boolean checkWhiteOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if(((tmp1.contains("Black")))){
			oponent = true;
      if(tmp1.contains("King")){
        JOptionPane.showMessageDialog(null, "White Wins!");
        System.exit(0);
      }
		}
		else{
			oponent = false;
		}
		return oponent;
	}
  /*
    This method checks if a peice is a White piece.
  */
  private Boolean checkBlackOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if(((tmp1.contains("White")))){
			oponent = true;
      if(tmp1.contains("King")){
        JOptionPane.showMessageDialog(null, "Black Wins!");
        System.exit(0);
      }
		}
		else{
			oponent = false;
		}
		return oponent;
	}

  private String checkName(int newX, int newY){
    Component c = chessBoard.findComponentAt(newX, newY);
    if(c instanceof JLabel){
      JLabel awaitingPiece = (JLabel) c;
      String tmp1 = awaitingPiece.getIcon().toString();
      return tmp1;
    }
    else{
      return "";
    }
  }

	/*
		This method is called when we press the Mouse. So we need to find out what piece we have
		selected. We may also not have selected a piece!
	*/
    public void mousePressed(MouseEvent e){
      chessPiece = null;
      Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
      if (c instanceof JPanel)
			   return;

      Point parentLocation = c.getParent().getLocation();
      xAdjustment = parentLocation.x - e.getX();
      yAdjustment = parentLocation.y - e.getY();
      chessPiece = (JLabel)c;
		  initialX = e.getX();
		  initialY = e.getY();
		  startX = (e.getX()/75);
		  startY = (e.getY()/75);
      chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
      chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
      layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
         chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
     }

 	/*
		This method is used when the Mouse is released...we need to make sure the move was valid before
		putting the piece back on the board.
	*/
    public void mouseReleased(MouseEvent e) {
        if(chessPiece == null) return;

        chessPiece.setVisible(false);
		Boolean success = false;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length()-4));
		Boolean validMove = false;
    Boolean progression = false;

    int landingX = (e.getX()/75);
    int landingY = (e.getY()/75);
    int xMovement = Math.abs((e.getX()/75)-startX);
    int yMovement = Math.abs((e.getY()/75)-startY);


    /*
    Method for creating turns, In chess white always starts first.
    */

    if(whiteStart){
      if(pieceName.contains("White") && !(xMovement == 0 && yMovement == 0)){
        checkTurn = true;
      }
    }
    else{
      if(pieceName.contains("Black") && !(xMovement == 0 && yMovement == 0)){
        checkTurn = true;
      }
    }

    /*
    This is the start of the logic of peice movement.
    */

    //Checking for current peice turn.
    if(checkTurn){

      //King movement/rules method
    if(pieceName.contains("King")){
      try{
        //King can only move one space per turn
        if(((yMovement == 1))||(xMovement == 1)) {

          //Setting rules for checking if opponent king is present.
          if((piecePresent(e.getX(), e.getY()+75)&& checkName(e.getX(), e.getY()+75).contains("King")||(piecePresent(e.getX(), e.getY()-75)&& checkName(e.getX(), e.getY()-75).contains("King")))){
            validMove = false;
          }
          else if((piecePresent(e.getX()+75,e.getY())&& checkName(e.getX()+75, e.getY()).contains("King")||(piecePresent(e.getX()-75, getY())&& checkName(e.getX()-75, e.getY()).contains("King")))){
            validMove = false;
          }
          else if((piecePresent(e.getX()-75, e.getY()+75)&& checkName(e.getX()-75, e.getY()+75).contains("King")||(piecePresent(e.getX()+75, e.getY()-75)&& checkName(e.getX()+75, e.getY()-75).contains("King")))){
            validMove = false;
          }
          else if((piecePresent(e.getX()-75, e.getY()-75)&& checkName(e.getX()-75, e.getY()-75).contains("King")||(piecePresent(e.getX()+75, e.getY()+75)&& checkName(e.getX()+75, e.getY()+75).contains("King")))){
            validMove = false;
          }
          else{
            //Bishop movements
            validMove = true;
            if(Math.abs(startX-landingX)==Math.abs(startY-landingY)){
              //checks that the king is moving in rook movements
              if((startX-landingX < 0)&&(startY-landingY < 0)){
                validMove = true;
              }
              else if((startX-landingX < 0)&&(startY-landingY > 0)){
                validMove = true;
              }
              else if((startX-landingX > 0)&&(startY-landingY > 0)){
                validMove = true;
              }
              else if((startX-landingX > 0)&&(startY-landingY < 0)){
                validMove = true;
              }
              //checking for white pieces if black
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                //checking for black opponent if white
                else{
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
            else if(((Math.abs(startX-landingX)!=0)&&(Math.abs(startY-landingY)==0))||((Math.abs(startX-landingX)==0)&&(Math.abs(landingY-startY)!=0))){//rook movements
              if(Math.abs(startX-landingX)!=0){
                //checking the rook movements along x axis
                if(startX-landingX > 0){
                  validMove = true;
                }
                else{
                  validMove = false;
                }
              }
              else{
                //checking the rook movements along y axis
                if(startY-landingY > 0){
                  validMove = true;
                }
                else{
                  validMove = false;
                }
              }
              //checking for white opponent if black
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                else{
                  //checking for black opponent if white
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
            else{
              validMove = false;
            }
          }
        }
        else{
          validMove = false;
        }
      }
      //Exception to keep peices on the board
      catch(Exception b){
        validMove = false;
      }
    }
      //Start of the queen method/rules
    if(pieceName.contains("Queen")){
      Boolean inTheWay = false;
      int distance = Math.abs(startX-landingX);
      try{
        if(((landingX < 0) || (landingX > 7))||((landingY < 0)||(landingY > 7))){//if to prevent falling off side
          validMove = false;
        }
        //Putting in bishop movements for the queen
        else{
          validMove = true;
          //keeps track of amount of spaces moved
          if(Math.abs(startX-landingX)==Math.abs(startY-landingY)){
            //checking for distance moved in each and making sure the queen is not jumping over pieces.
            if((startX-landingX < 0)&&(startY-landingY < 0)){
              for(int i = 0; i<distance; i++){
                if(piecePresent((initialX+(i*75)), (initialY+(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX < 0)&&(startY-landingY > 0)){
              for(int i=0; i<distance; i++){
                if(piecePresent((initialX+(i*75)), (initialY-(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX > 0)&&(startY-landingY > 0)){
              for(int i=0; i < distance; i++){
                if(piecePresent((initialX-(i*75)), (initialY-(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX > 0)&&(startY-landingY < 0)){
              for(int i = 0; i < distance; i++){
                if(piecePresent((initialX-(i*75)), (initialY+(i*75)))){
                  inTheWay = true;
                }
              }
            }//end bishop checking
            //Invalid move if queen is jumping over peices
            if(inTheWay){
              validMove = false;
            }
            else{
              //checking for white opponent if black
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                else{
                  //checking for black opponent if white
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
          }
          else if(((Math.abs(startX-landingX)!=0)&&(Math.abs(startY-landingY)==0))||((Math.abs(startX-landingX)==0)&&(Math.abs(landingY-startY)!=0))){//rook movements
            if(Math.abs(startX-landingX)!=0){
              //checking the distance moved on x axis
              //checking if piece is present for the distance moved across straight line on x or y axis
              if(startX-landingX > 0){
                for(int i = 0; i < xMovement; i++){
                  if(piecePresent(initialX-(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
              else{
                for(int i = 0; i < xMovement; i++){
                  if(piecePresent(initialX+(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
            }
            else{
              if(startY-landingY > 0){
                for(int i=0; i < yMovement; i++){
                  if(piecePresent(e.getX(), initialY-(i*75))){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
              else{
                for(int i = 0; i < yMovement; i++){
                  if(piecePresent(e.getX(), initialY+(i*75))){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
            }
            if(inTheWay){
              //invalid move if piece in the way across the distance
              validMove = false;
            }
            else{
              //checking for black opponent if white
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                else{
                  //checking for white opponent if black
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
          }
          else{
            validMove = false;
          }
        }
      }
      //Preventing peices falling off the board
      catch(Exception b){
        validMove = false;
      }
    }
    //Thge start of the bishop method/rules
    if(pieceName.contains("Bishup")){
      try{
        Boolean inTheWay = false;
        //Checking the distance moved
        int distance = Math.abs(startX-landingX);
        if(((landingX < 0) || (landingX > 7))||((landingY < 0)||(landingY > 7))){
          validMove = false;
        }
        else{
          validMove = true;
          if(Math.abs(startX-landingX)==Math.abs(startY-landingY)){
            //Checking the distance moved and checking if a piece is in the way across the distance
            if((startX-landingX < 0)&&(startY-landingY < 0)){
              for(int i = 0; i<distance; i++){
                if(piecePresent((initialX+(i*75)), (initialY+(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX < 0)&&(startY-landingY > 0)){
              for(int i=0; i<distance; i++){
                if(piecePresent((initialX+(i*75)), (initialY-(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX > 0)&&(startY-landingY > 0)){
              for(int i=0; i < distance; i++){
                if(piecePresent((initialX-(i*75)), (initialY-(i*75)))){
                  inTheWay = true;
                }
              }
            }
            else if((startX-landingX > 0)&&(startY-landingY < 0)){
              for(int i = 0; i < distance; i++){
                if(piecePresent((initialX-(i*75)), (initialY+(i*75)))){
                  inTheWay = true;
                }
              }
            }
            //Invalid move if the peice is in the way
            if(inTheWay){
              validMove = false;
            }
            else{
              //checking for white opponent if black
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                else{
                  //checking for black opponent if white
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
          }
          else{
            validMove = false;
          }
        }
      }
      //Preventing the peice falling off the board
      catch(Exception b){
        validMove = false;
      }
    }
    //Start of the rook method/rules
    if(pieceName.contains("Rook")){
      Boolean inTheWay = false;
      //To prevent peice falling off the board
      try{
        if(((landingX < 0) || (landingX > 7))||((landingY < 0)||(landingY > 7))){
          validMove = false;
        }
        else{
          if(((Math.abs(startX-landingX)!=0)&&(Math.abs(startY-landingY)==0))||((Math.abs(startX-landingX)==0)&&(Math.abs(landingY-startY)!=0))){
            if(Math.abs(startX-landingX)!=0){
              //Checking the distance moved and if a piece is in the way
              if(startX-landingX > 0){
                for(int i = 0; i < xMovement; i++){
                  if(piecePresent(initialX-(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
              else{
                for(int i = 0; i < xMovement; i++){
                  if(piecePresent(initialX+(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
            }
            else{
              if(startY-landingY > 0){
                for(int i=0; i < yMovement; i++){
                  if(piecePresent(e.getX(), initialY-(i*75))){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
              else{
                for(int i = 0; i < yMovement; i++){
                  if(piecePresent(e.getX(), initialY+(i*75))){
                    inTheWay = true;
                    break;
                  }
                  else{
                    inTheWay = false;
                  }
                }
              }
            }
            //Invalid move if a peice is in the way
            if(inTheWay){
              validMove = false;
            }
            else{
              //Checking for white opponent if black
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
                else{
                  //Checking for black opponent if white
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  }
                  else{
                    validMove = false;
                  }
                }
              }
              else{
                validMove = true;
              }
            }
          }
          else{
            validMove = false;
          }
        }
      }
      //Preventing peices falling of the board
      catch(Exception b){
        validMove = false;
      }
    }
    //start of the knight Method/Rules
    if(pieceName.contains("Knight")){
      //Prevnting piece falling off the board
      try{
        //Checking the knight movement, varies in shaped L movements
        if(((xMovement == 1)&&(yMovement == 2))||((yMovement == 1)&&(xMovement == 2))){
          //Checking for not a peice present, allowing knight to jump over peices.
          if(!piecePresent(e.getX(), e.getY())){
            validMove = true;
          }
          else{
            //checking for black opponent if white
            if(pieceName.contains("White")){
              if(checkWhiteOponent(e.getX(), e.getY())){
                validMove = true;
              }
            }
            else{
              //checking for white opponent if black
              if(checkBlackOponent(e.getX(), e.getY())){
                validMove = true;
              }
            }
          }
        }
      }
      //Prevnting peice falling of the board.
      catch(Exception b){
        validMove = false;
      }
    }
    //Start of white pawn movements/rules
    if(pieceName.equals("WhitePawn")){
      try{
        //checking if pawn is on first movement because it can choose to move 1 or 2 spaces
        if(startY == 1){
          //Starting move, can move 1 or 2 squares
          if(((yMovement==1)||(yMovement==2))&&(startY < landingY)&&(xMovement == 0)){
            //Logic for moving 2 squares
            if(yMovement == 2){
        //checking to see if a piece is on the square you're trying to move to or a square between your movement if movement is 2
              if((!piecePresent(e.getX(), e.getY()))&&(!piecePresent(e.getX(), (e.getY()-75)))) {
                validMove = true;
                //If the pawn makes it to the bottom of the board
                if(landingY == 7){
                  //Transform pawn to queen
                  success = true;
                }
              }
            }
            else{
              //if a peice is not in the way
              if(!piecePresent(e.getX(), e.getY())){
                validMove = true;
                  //If the pawn makes it to the bottom of the board
                if(landingY == 7){
                  //Transform pawn to queen
                  success = true;
                }
              }
            }
          }
          //Checking for peice diagnolly
          else if((yMovement == 1)&&(startY < landingY)&&(xMovement == 1)){
            if(piecePresent(e.getX(), e.getY())){
              if(checkWhiteOponent(e.getX(), e.getY())){
                validMove = true;
                  //If the pawn makes it to the bottom of the board
                if(landingY == 7){
                  //Transform pawn to queen
                  success = true;
                }
              }
            }
          }
        }
        else{
          //Making sure the pawn is moving down the board
          if(((yMovement == 1))&&(startY < landingY)&&(xMovement == 0)){
            if(!piecePresent(e.getX(), e.getY())){
              validMove = true;
                //If the pawn makes it to the bottom of the board
              if(landingY == 7){
                //Transform pawn to queen
                success = true;
              }
            }
          }
          else if((yMovement == 1)&&(startY < landingY)&&(xMovement == 1)){
            if(piecePresent(e.getX(), e.getY())){
              if(checkWhiteOponent(e.getX(), e.getY())){
                validMove = true;
                  //If the pawn makes it to the bottom of the board
                if(landingY == 7){
                  //Transform pawn to queen
                  success = true;
                }
              }
            }
          }
        }
      }
      //Preventing the peice from falling off the board
      catch(Exception b){
        validMove = false;
      }
    }
    //Start of white pawn movements/rules
    if(pieceName.equals("BlackPawn")){
      //To prevent peice falling off the board
      try{
        //Checking to see if the black pawn is in starting postion
        if(startY == 6){
          //Starting move, can move 1 or 2 squares
          if(((yMovement==1)||(yMovement==2))&&(startY > landingY)&&(xMovement == 0)){
            //Logic for moving 2 squares
            if(yMovement == 2){
              //checking to see if a piece is on the square you're trying to move to or a square between your movement if movement is 2
              if((!piecePresent(e.getX(), e.getY()))&&(!piecePresent(e.getX(), (e.getY()+75)))) {
                validMove = true;
                //If the pawn makes it to the top of the board
                if(landingY == 0){
                  //Transform pawn to queen
                  progression = true;
                }
              }
            }
            else{
              //if a peice is not in the way
              if(!piecePresent(e.getX(), e.getY())){
                validMove = true;
                //If the pawn makes it to the top of the board
                if(landingY == 0){
                  //Transform pawn to queen
                  progression = true;
                }
              }
            }
          }
          //Checking for peice diagnolly
          else if((yMovement == 1)&&(startY > landingY)&&(xMovement == 1)){
            if(piecePresent(e.getX(), e.getY())){
              if(checkBlackOponent(e.getX(), e.getY())){
                validMove = true;
                //If the pawn makes it to the bottom of the board
                if(landingY == 0){
                  //Transform pawn to queen
                  progression = true;
                }
              }
            }
          }
        }
        else{
          //Making sure the pawn is moving up the board
          if(((yMovement == 1))&&(startY > landingY)&&(xMovement == 0)){
            if(!piecePresent(e.getX(), e.getY())){
              validMove = true;
              //If the pawn makes it to the top of the board
              if(landingY == 0){
                //Transform pawn to queen
                progression = true;
              }
            }
          }
          else if((yMovement == 1)&&(startY > landingY)&&(xMovement == 1)){
            if(piecePresent(e.getX(), e.getY())){
              //checking for white opponent if black
              if(checkBlackOponent(e.getX(), e.getY())){
                validMove = true;
                //If the pawn makes it to the top of the board
                if(landingY == 0){
                  //Transform pawn to queen
                  progression = true;
                }
              }
            }
          }
        }
      }
      //Preventing the peice from falling off the board
      catch(Exception b){
        validMove = false;
      }
    }
  //The end of the method for checking turn
  }


    //Method for invalid move
		if(!validMove){
			int location=0;
      //Returning to original location for an invalid move.
			if(startY ==0){
				location = startX;
			}
			else{
				location  = (startY*8)+startX;
			}
			String pieceLocation = pieceName+".png";
			pieces = new JLabel( new ImageIcon(pieceLocation) );
			panels = (JPanel)chessBoard.getComponent(location);
		    panels.add(pieces);
		}
		else{
      //making sure the white peice moves first
      whiteStart = !whiteStart;
      checkTurn = false;

      //Method for tranforming black pawn to queen
      if(progression){
				int location = 0 + (e.getX()/75);
				if (c instanceof JLabel){
	            	Container parent = c.getParent();
	            	parent.remove(0);
					pieces = new JLabel( new ImageIcon("BlackQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
			    	parent.add(pieces);
				}
				else{
					Container parent = (Container)c;
	            	pieces = new JLabel( new ImageIcon("BlackQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
			    	parent.add(pieces);
				}
			}
      //method for tranforming white pawn to white queen
			else if(success){
				int location = 56 + (e.getX()/75);
				if (c instanceof JLabel){
	            	Container parent = c.getParent();
	            	parent.remove(0);
					pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
			    	parent.add(pieces);
				}
				else{
					Container parent = (Container)c;
	            	pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
			    	parent.add(pieces);
				}
			}
			else{
				if (c instanceof JLabel){
	            	Container parent = c.getParent();
	            	parent.remove(0);
	            	parent.add( chessPiece );
	        	}
	        	else {
	            	Container parent = (Container)c;
	            	parent.add( chessPiece );
	        	}
	    		chessPiece.setVisible(true);
			}
		}
  }

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseMoved(MouseEvent e) {
   }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e) {

    }

	/*
		Main method that gets the ball moving.
	*/
    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
     }
}
