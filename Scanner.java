import java.util.ArrayList;

/**
 * Eric Najork, Laan Rose
 * CSC 453
 * Programming Assignment 2
 */
public class Scanner {
  enum TokenType {
    NUM, PLUS, MINUS, MUL, DIV, LP, RP;
  }

  class Token {
    TokenType tokenType;
    String tokenVal;

    public Token(TokenType tokenType, String tokenVal) {
      this.tokenType = tokenType;
      this.tokenVal = tokenVal;
    }

    public String toString() {
      return "|" + this.tokenType + ": " + this.tokenVal + "|";
    }
  }
  /**
   * Returns true if the first characters in 'stream' represent an operator (+,-,/,*,(,)) token.
   */
  public boolean isOperator(StringBuilder stream) {
    boolean out = false;

    if (stream.charAt(0) == '+' ||
      stream.charAt(0) == '-' ||
      stream.charAt(0) == '/' ||
      stream.charAt(0) == '*' ||
      stream.charAt(0) == '(' ||
      stream.charAt(0) == ')') {

      out = true;
    } else {
      out = false;
    }

    return out;
  }

  /**
   * Returns a number Token created from 'stream'.
   */
  public Token extractNumber(StringBuilder stream) {
    TokenType tokenType = TokenType.NUM;
    String tokenVal = "";

    // Get the rest of the number if it's more than one digit.
    tokenVal += stream.charAt(0);
    int currPos = 1;
    while (currPos < stream.length() && Character.isDigit(stream.charAt(currPos)) == true) {
      tokenVal += stream.charAt(currPos);
      currPos++;
    }

    return new Token(tokenType, tokenVal);
  }

  /**
   * Returns an operator (*,/,+,-) Token created from 'stream'.
   */
  public Token extractOperator(StringBuilder stream) {
    TokenType tokenType = TokenType.NUM;
    String tokenVal = "";

    if (stream.charAt(0) == '+') {
      tokenType = TokenType.PLUS;
      tokenVal = "+";
    } else if (stream.charAt(0) == '-') {
      tokenType = TokenType.MINUS;
      tokenVal = "-";
    } else if (stream.charAt(0) == '*') {
      tokenType = TokenType.MUL;
      tokenVal = "*";
    } else if (stream.charAt(0) == '/') {
      tokenType = TokenType.DIV;
      tokenVal = "/";
    } else if (stream.charAt(0) == '(') {
      tokenType = TokenType.LP;
      tokenVal = "(";
    } else if (stream.charAt(0) == ')') {
      tokenType = TokenType.RP;
      tokenVal = ")";
    }

    return new Token(tokenType, tokenVal);
  }

  /**
   * Extracts and returns the next token in 'stream'.
   */
  public Token extractToken(StringBuilder stream) throws Exception {
    // Initialize return Token
    TokenType tokenType = TokenType.NUM;
    String tokenVal = "";
    Token newToken = new Token(tokenType, tokenVal);

    // Remove any initial whitespace from 'stream' (whitespace left behind from previous tokens)
    while (stream.charAt(0) == ' ' || stream.charAt(0) == '\n' || stream.charAt(0) == '\t') {
      stream.deleteCharAt(0);
      if (stream.length() < 1) {
        break;
      }
    }

    // Extract token if it's a number
    if (Character.isDigit(stream.charAt(0)) == true) {
      newToken = extractNumber(stream);
    }
    // Extract token if it's an operator (*,/,+,-,),()
    else if (isOperator(stream) == true) {
      newToken = extractOperator(stream);
    }
    // Handle illegal tokens
    else {
      throw new Exception("Error: illegal token '" + stream.charAt(0) + "'");
    }

    // Create and return the Token
    return newToken;
  }

  /**
   * Removes all tokens from 'arg' and returns them as a single string.
   */
  public String extractTokens(String arg) {
    // Make a return string that will contain all tokens
    String result = "";

    // Convert arg to a StringBuilder so it can be manipulated easier
    StringBuilder builderArg = new StringBuilder(arg);

    // Remove tokens from 'builderArg' and store them in 'result' until 'builderArg' is empty.
    while (builderArg.length() != 0) {
      // Copy the next token from builderArg and store it
      try {
        Token nextToken = extractToken(builderArg);
        result += nextToken.toString();

        // Remove token that was just copied from 'builderArg'
        int tokenLength = nextToken.tokenVal.length();
        for (int x = tokenLength; x > 0; x--) {
          builderArg.deleteCharAt(0);
        }
      }
      // Handle any illegal tokens
      catch (Exception e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    return result;
  }

  /**
   * Generates Token objects from 'arg' and returns them as an ArrayList.
   */
  public ArrayList<Token> extractTokenList(String arg) throws Exception {
    // Make an arraylist that will return all extracted tokens.
    ArrayList<Token> result = new ArrayList<Token>();

    // Convert arg to a StringBuilder so it can be manipulated easier
    StringBuilder builderArg = new StringBuilder(arg);

    // Remove tokens from 'builderArg' and store them in 'result' until 'builderArg' is empty.
    try {
      while (builderArg.length() != 0) {
        // Copy the next token from builderArg and store it
        Token nextToken = extractToken(builderArg);
        result.add(nextToken);

        // Remove token that was just copied from 'builderArg'
        int tokenLength = nextToken.tokenVal.length();
        for (int x = tokenLength; x > 0; x--) {
          builderArg.deleteCharAt(0);
        }
      }
    }
    // Handle illegal token exceptions
    catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }

    return result;
  }
}