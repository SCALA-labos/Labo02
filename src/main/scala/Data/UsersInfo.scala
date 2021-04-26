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
    accounts = accounts.map(e => if(e._1 == user) (e._1,e._2-amount) else e)
    accounts.find(e => e._1 == user).getOrElse(throw new IllegalArgumentException)._2
  }

  def getCurrentBalance(): Double ={
    accounts.find(e => e._1 == _activeUser).getOrElse(throw new IllegalArgumentException)._2
  }

  def getCurrentUsername(): String ={
    _activeUser
  }
}
