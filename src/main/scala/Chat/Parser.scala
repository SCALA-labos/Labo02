package Chat

import Chat.Tokens._
import Data.Products
import Tree._

class UnexpectedTokenException(msg: String) extends Exception(msg){}

// TODO - step 4
class Parser(tokenizer: Tokenizer) {
  import tokenizer._

  var curTuple: (String, Token) = ("unknown", UNKNOWN)
  
  def curValue: String = curTuple._1
  def curToken: Token = curTuple._2

  /** Reads the next token and assigns it into the global variable curTuple */
  def readToken(): Unit = curTuple = nextToken()

  /** "Eats" the expected token, or terminates with an error. */
  private def eat(token: Token): Unit = if (token == curToken) readToken() else expected(token)

  /** Complains that what was found was not expected. The method accepts arbitrarily many arguments of type TokenClass */
  // TODO (BONUS): find a way to display the string value of the tokens (e.g. "BIERE") instead of their integer value (e.g. 6).
  private def expected(token: Token, more: Token*): Nothing = {
    throw new UnexpectedTokenException("Expected: " +
      (token.toString :: more.toList).mkString(" or ") +
      ", found: " + curToken.toString)
  }

  /** the root method of the parser: parses an entry phrase */
  def parsePhrases() : ExprTree = {
    if (curToken == BONJOUR) readToken()
    if (curToken == JE) {
      readToken()
      if(curToken == ETRE) {
        readToken()
        if (curToken == ASSOIFFE) {
          readToken()
          Thirsty()
        }
        else if (curToken == AFFAME) {
          readToken()
          Hungry()
        }
        else if (curToken == PSEUDO) {
          readToken()
          Login(curValue)
        }
        else expected(ASSOIFFE, AFFAME, PSEUDO)
      } else {//Politesse
        parsePolite()
        if(curToken == COMMANDER) {
          parseComplexOrder()
        } else if (curToken == SAVOIR) {
          parseBalance()
        } else expected(COMMANDER, SAVOIR)
      }
    } else if (curToken == COMBIEN || curToken == QUE){
      parseOrderInfoRequest()
    } else expected(JE, COMBIEN, QUE)
  }

  /*def parsePhrases() : ExprTree = {
    if (curToken == BONJOUR) readToken()
    //parseLogin()
    //parseBalance()
    parseStateOfMind()
  }*/

  def parseOrderInfoRequest(): ExprTree = {
    if (curToken == COMBIEN) {
      readToken()
      eat(COUTER)
      Info(parseOrder())
    }
    else if(curToken == QUE) {
      readToken()
      eat(ETRE)
      eat(LE)
      eat(PRIX)
      eat(DE)
      Info(parseOrder())
    }
    else expected(COMBIEN, QUE)
  }

  def parseComplexOrder(): ExprTree = {
    val leftOrder = parseOrder()
    if(curToken == ET) {
      And(leftOrder, parseComplexOrder())
    } else if (curToken == OU) {
      Or(leftOrder, parseComplexOrder())
    } else {
      ComplexOrder(leftOrder)
    }
  }

  def parseOrder(): Order = {
    readToken()
    val num = curValue.toInt
    eat(NUM)
    val productType : Token = curToken match {
      case CROISSANT => Products.CROISSANT
      case BIERE => Products.BEER
    }
    readToken()
    if(curToken == MARQUE) {
      val brand = curValue
      eat(MARQUE)
      Order(num, Product(productType, brand))
    }
    else {
      Order(num, Product(productType))
    }
  }

  def parseStateOfMind() : ExprTree = {
    readToken()
    eat(JE)
    eat(ETRE)
    curToken match {
      case AFFAME => Hungry()
      case ASSOIFFE => Thirsty()
      case _ => expected(AFFAME, ASSOIFFE)
    }
  }

  def parseLogin(): ExprTree = {
    readToken()
    eat(JE)
    curToken match {
      case ETRE => readToken()
      case ME =>
        readToken()
        eat(APPELER)
    }
    if(curToken == PSEUDO){
      Login(curValue)
    }else expected(PSEUDO)
  }

  def parsePolite() {
    eat(VOULOIR)
  }

  def parseBalance() : ExprTree = {
    readToken()
    eat(MON)
    eat(SOLDE)
    Balance()
  }

  // Start the process by reading the first token.
  readToken()
}
