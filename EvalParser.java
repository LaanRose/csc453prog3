import java.util.ArrayList;

public class EvalParser {
  Scanner scan;
  ArrayList<Scanner.Token> tokenList;   // List of Tokens extracted from the scanner
  Scanner.TokenType lookahead;          // TokenType (NUM, DIV...) of current lookahead token
  int lookpos = 0;                      // Position of lookahead token in tokenList<>

  int tempID = 0;
  String threeAddressResult = "";

  /***************** Three Address Translator ***********************/
  // TODO #2 Continued: Write the functions for E/E', T/T', and F. Return the temporary ID associated with each subexpression and
  //                    build the threeAddressResult string with your three address translation

  /****************************************/


  /***************** Simple Expression Evaluator ***********************/
  // TODO #1 Continued: Write the functions for E/E', T/T', and F. Return the expression's value

  /****************************************/

  private int ThreeE() throws Exception{
    int thisID = tempID;
    int idLeft = 0;
    int idRight = 0;

    idLeft = ThreeT();
    while(true) {
      if (lookahead.equals(Scanner.TokenType.PLUS)) {
        match();

        // Perform addition
        idRight = ThreeT();
        thisID = tempID;
        threeAddressResult += "temp" + tempID + " = " + "temp" + idLeft + " + " + "temp" + idRight + "\n";
        tempID++;
        idLeft = thisID;
        continue;
      }
      else if (lookahead.equals(Scanner.TokenType.MINUS)) {
        match();

        // Perform subtraction
        idRight = ThreeT();
        thisID = tempID;
        threeAddressResult += "temp" + tempID + " = " + "temp" + idLeft + " - " + "temp" + idRight + "\n";
        tempID++;
        idLeft = thisID;
        continue;
      }
      break;
    }

    thisID = tempID - 1;
    return thisID;
  }

  /**
   * Parses all the productions of T.
   */
  private int ThreeT() throws Exception{
    int thisID = tempID;
    int idLeft = 0;
    int idRight = 0;

    idLeft = ThreeF();
    while(true) {
      if (lookahead.equals(Scanner.TokenType.MUL)) {
        match();

        idRight = ThreeF();
        thisID = tempID;
        threeAddressResult += "temp" + tempID + " = " + "temp" + idLeft + " * " + "temp" + idRight + "\n";
        tempID++;
        idLeft = thisID;
        continue;
      } else if (lookahead.equals(Scanner.TokenType.DIV)) {
        match();

        idRight = ThreeF();
        thisID = tempID;
        threeAddressResult += "temp" + tempID + " = " + "temp" + idLeft + " / " + "temp" + idRight + "\n";
        tempID++;
        idLeft = thisID;
        continue;
      }
      break;
    }

    thisID = tempID - 1;
    return thisID;
  }

  /**
   * Parses all the productions of F.
   */
  private int ThreeF() throws Exception{
    int thisID = tempID;
    String tempString = "";

    // Handle '( E )'
    if (lookahead.equals(Scanner.TokenType.LP)) {
      match();
      // Return all calculation that happened within the parentheses
      thisID = ThreeE();
    }
    // Handle 'num'
    else if (lookahead.equals(Scanner.TokenType.NUM)) {
      tempString = "temp" + tempID + " = " + Integer.parseInt(tokenList.get(lookpos).tokenVal ) + "\n";
      tempID++;

      // Return value of this number terminal
      Integer.parseInt(tokenList.get(lookpos).tokenVal);
      match();
    }

    threeAddressResult += tempString;
    return thisID;
  }

  /**
   * Parses all the productions of E.
   */
  /*private int ThreeAddressE() {
    int myTempID = ThreeAddressT();
    while(true) {
      if (lookahead.equals(Scanner.TokenType.PLUS)) {
        match();

        // Perform addition
        int rightTempID = ThreeAddressT();
        this.threeAddressResult += "temp" + tempID + " = temp" + myTempID + " + temp" + rightTempID + "\n";
        tempID++;
        continue;
      }
      else if (lookahead.equals(Scanner.TokenType.MINUS)) {
        match();

        // Perform subtraction
        int rightTempID = ThreeAddressT();
        this.threeAddressResult += "temp" + tempID + " = temp" + myTempID + " - temp" + rightTempID + "\n";
        tempID++;
        continue;
      }
      break;
    }

    return tempID;
  }*/

  /**
   * Parses all the productions of T.
   */
  /*private int ThreeAddressT() {
    int myTempID = ThreeAddressF();
    while(true) {
      if (lookahead.equals(Scanner.TokenType.MUL)) {
        match();

        // Perform multiplication on everything
        int rightTempID = ThreeAddressF();
        this.threeAddressResult += "temp" + tempID + " = temp" + myTempID + " * temp" + rightTempID + "\n";
        tempID++;
        continue;
      } else if (lookahead.equals(Scanner.TokenType.DIV)) {
        match();

        // Perform division on everything
        int rightTempID = ThreeAddressF();
        this.threeAddressResult += "temp" + tempID + " = temp" + myTempID + " / temp" + rightTempID + "\n";
        tempID++;
        continue;
      }
      break;
    }

    return myTempID;
  }*/

  /**
   * Parses all the productions of F.
   */
  /*private int ThreeAddressF() {
    // Handle '( E )'
    int myTempID = tempID;
    if (lookahead.equals(Scanner.TokenType.LP)) {
      match();

      // Return all calculation that happened within the parentheses
      return ThreeAddressE() - 1;
    }

    // Handle 'num'
    else if (lookahead.equals(Scanner.TokenType.NUM)) {
      // Return three address value of this token
      this.threeAddressResult += "temp" + tempID + " = " + tokenList.get(lookpos).tokenVal + "\n";
      tempID++;
      match();

      if (lookahead.equals(Scanner.TokenType.RP)) {
        match();
      }
    }

    return myTempID;
  }*/

  private int E() throws Exception{
    int out = 0;

    out += T();
    while(true) {
     if (lookahead.equals(Scanner.TokenType.PLUS)) {
        match();

        // Catch parsing errors
        if (lookahead.equals(Scanner.TokenType.NUM) == false && lookahead.equals(Scanner.TokenType.LP) == false) {
          throw new Exception("Parse error: illegal token '" + lookahead.toString() + "'");
        }

        // Perform addition
        out += T();
        continue;
      }
      else if (lookahead.equals(Scanner.TokenType.MINUS)) {
        match();

        // Catch parsing errors
        if (lookahead.equals(Scanner.TokenType.NUM) == false && lookahead.equals(Scanner.TokenType.LP) == false) {
          throw new Exception("Parse error: illegal token '" + lookahead.toString() + "'");
        }

        // Perform subtraction
        out -= T();
        continue;
      }
      break;
    }

    return out;
  }

  /**
   * Parses all the productions of T.
   */
  private int T() throws Exception{
    int out = 0;

    out += F();

    while(true) {
      if (lookahead.equals(Scanner.TokenType.MUL)) {
        match();

        // Catch parsing errors
        if (lookahead.equals(Scanner.TokenType.NUM) == false && lookahead.equals(Scanner.TokenType.LP) == false) {
          throw new Exception("Parse error: illegal token '" + lookahead.toString() + "'");
        }

        // Perform multiplication on everything
        out = out * F();
        continue;
      } else if (lookahead.equals(Scanner.TokenType.DIV)) {
        match();

        // Catch parsing errors
        if (lookahead.equals(Scanner.TokenType.NUM) == false && lookahead.equals(Scanner.TokenType.LP) == false) {
          throw new Exception("Parse error: illegal token '" + lookahead.toString() + "'");
        }

        // Perform division on everything
        out = out / F();
        continue;
      }
      break;
    }

    return out;
  }

  /**
   * Parses all the productions of F.
   */
  private int F() throws Exception{
    int out = 0;

    // Handle '( E )'
    if (lookahead.equals(Scanner.TokenType.LP)) {

      match();

      // Catch parsing errors after '('
      /*if (lookahead.equals(Scanner.TokenType.NUM) == false && lookahead.equals(Scanner.TokenType.RP) == false) {
        throw new Exception("Parse error: illegal token '" + lookahead.toString() + "'");
      }*/

      // Return all calculation that happened within the parentheses
      out += E();
      match();
    }
    // Handle 'num'
    else if (lookahead.equals(Scanner.TokenType.NUM)) {
      // For now, just print out the value of the terminal.

      // Return value of this number terminal
      out += Integer.parseInt(tokenList.get(lookpos).tokenVal);
      match();
    }

    return out;
  }

  /**
   * ???
   * Increments the current lookahead token. This may or may not be what it is supposed to do, but
   * things work this way.
   */
  private void match() {
    if (lookpos < tokenList.size() - 1) {
      lookpos++;
      lookahead = tokenList.get(lookpos).tokenType;
    }
  }

  /* TODO #1: Write a parser that can evaluate expressions */
  public int evaluateExpression(String eval) {
    // Get scanner to extract tokens.
    try {
      scan = new Scanner();
      tokenList = scan.extractTokenList(eval);
    }
    // Handle illegal tokens
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Parse through the tree that made all the tokens
    lookpos = 0;
    lookahead = tokenList.get(0).tokenType;
    int evaluation = 0;

    // Parse through tokens
    try {
      evaluation = E();
    }
    // Catch any parsing errors (NOT COMPLETE).
    catch (Exception e) {
      e.printStackTrace();
    }

    // Return result that was calculated while parsing the tree
    return evaluation;
  }

  /* TODO #2: Now add three address translation to your parser*/
  public String getThreeAddr(String eval) {
    this.threeAddressResult = "";
    try {
      scan = new Scanner();
      tokenList = scan.extractTokenList(eval);
    }
    // Handle illegal tokens
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Parse through the tree that made all the tokens
    lookpos = 0;
    tempID = 0;
    lookahead = tokenList.get(0).tokenType;
    try {
      ThreeE();
    }
    // Catch any parsing errors (NOT COMPLETE)
    catch (Exception e) {
      e.printStackTrace();
    }

    return this.threeAddressResult;
  }

}
