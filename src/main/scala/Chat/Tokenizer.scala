package Chat

import Chat.Tokens._
import Data.Products
import Utils.Dictionary.dictionary
import Utils.SpellChecker._

class Tokenizer(input: String) {
  var tokens: Array[(String, Token)] = Array()
  private var currentTokenIndex = -1

  private def getTokenFromString(s: String): Token = s match {
    case "bonjour" => BONJOUR
    case "je" => JE
    case "etre" => ETRE
    case "vouloir" => VOULOIR
    case "et" => ET
    case "ou" => OU
    case "biere" => BIERE
    case "croissant" => CROISSANT
    case "assoiffe" => ASSOIFFE
    case "affame" => AFFAME
    case "savoir" => SAVOIR
    case "connaitre" => SAVOIR
    case "que" => QUE
    case "prix" => PRIX
    case "couter" => COUTER
    case "solde" => SOLDE
      // Added
    case "me" => ME
    case "combien" => COMBIEN
    case "commander" => COMMANDER
    case "le" => LE
    case "de" => DE
    case "mon" => MON
    case "appeler" => APPELER
      //===
    case p if p.startsWith("_") && p.length > 1 => PSEUDO // If the word starts with '_' and has more than one character it is a pseudonym.
    case n if n.forall(Character.isDigit) => NUM // If every character is a number, the word thus is a number.
    case n if Products.contains(n) => MARQUE
    case _ => UNKNOWN
  }

  def tokenize(): Unit = {
    val words = input
      .trim()
      .replaceAll("[.,!?*]", " ") // Remove punctuation.
      .replaceAll(" +|['’]", " ") // Remove multiple spaces and replace apostrophes by a space.
      .split(" ")
      .filterNot(_.isEmpty)

    // Get each word's occurence in the dictionary or check for the closest word if it is not contained in the dictionary.
    val fromDictionnary = words.map(w => dictionary.getOrElse(w, getClosestWordInDictionary(w)))

    tokens = fromDictionnary.map(t => (t, getTokenFromString(t)))
  }

  def nextToken(): (String, Token) = {
    currentTokenIndex += 1

    if (currentTokenIndex < tokens.length) tokens(currentTokenIndex)
    else ("EOL", EOL)
  }
}
