package Data

import java.security.InvalidParameterException
import scala.collection.mutable

object UsersInfo {

  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  // TODO: step 2 - create an attribute that will contain each user and its current balance.
  private var accounts: List[(String, Double)] = List()

  def addAccount(name : String, balance : Double = 30.0) = {// FIXME : When and how should we actually call this ?
    if(accounts.exists(e => e._1 == name)) {
      throw InvalidParameterException//Account already exists
    } else {
      (name, balance) +: accounts
    }
  }

  /**
    * Update an account by decreasing its balance.
    * @param user the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  // TODO: step 2
  def purchase(user: String, amount: Double): Double = {
    var balance = accounts.find(e => e._1 == user).getOrElse(throw InvalidParameterException)._2
    balance -= amount
    balance
  }
}
