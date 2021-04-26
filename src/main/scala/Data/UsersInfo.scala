package Data

object UsersInfo {

  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  // TODO: step 2 - create an attribute that will contain each user and its current balance.
  private var accounts: List[(String, Double)] = List()

  def login(name : String, balance : Double = 30.0) = {// FIXME : When and how should we actually call this ?
    if(!accounts.exists(e => e._1 == name)) {
      accounts = accounts :+ (name, balance)
    }
    _activeUser = name
  }

  /**
    * Update an account by decreasing its balance.
    * @param user the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  // TODO: step 2
  def purchase(user: String, amount: Double): Double = {
    var balance = accounts.find(e => e._1 == user).getOrElse(throw new IllegalArgumentException)._2
    balance -= amount
    balance
  }
}
