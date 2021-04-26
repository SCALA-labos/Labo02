package Chat

object Tokens {
  type Token = Int

  // Terms
  val BONJOUR: Token     = 0
  val JE: Token          = 1
  // Actions
  val ETRE: Token        = 2
  val VOULOIR: Token     = 3
  // Operators
  val ET: Token          = 4
  val OU: Token          = 5
  // Products
  val BIERE: Token       = 6
  val CROISSANT: Token   = 7
  // Unknown word
  val UNKNOWN: Token     = 8
  // Utils
  val PSEUDO: Token      = 9
  val NUM: Token         = 10
  val EOL: Token         = 11
  // State of mind
  val ASSOIFFE : Token   = 12
  val AFFAME : Token     = 13

  // Info
  val SAVOIR : Token = 14
  // Monetary
  val PRIX : Token = 15
  val SOLDE : Token = 16
  // Interrogation
  val QUESTION : Token = 17

  // Added
  val ME : Token = 18
  val COMBIEN : Token = 19
  val COMMANDER : Token = 20
  val LE : Token = 21
  val DE : Token = 22
  val CONNAITRE : Token = 23
  val MON : Token = 24

}
