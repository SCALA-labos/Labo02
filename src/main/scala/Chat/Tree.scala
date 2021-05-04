package Chat

import Chat.Tokens.{BIERE, CROISSANT, Token}
import Data.Products.{getCroissant, getDrink}
import Data.{Products, UsersInfo}

// TODO - step 3
object Tree {

  /**
    * This sealed trait represents a node of the tree and contains methods to compute it and write its output text in console.
    */
  sealed trait ExprTree {
    /**
      * Compute the price of the current node, then returns it. If the node is not a computational node, the method
      * returns 0.0.
      * For example if we had a "+" node, we would add the values of its two children, then return the result.
      * @return the result of the computation
      */
    def computePrice: Double = this match {
      case Order(n, product) => product.productType match {
        case Products.BEER =>
          if(product.brand == null) n*getDrink().get._2
          else n * getDrink(product.brand).get._2
        case Products.CROISSANT =>
          if(product.brand == null) n*getCroissant().get._2
          else n * getCroissant(product.brand).get._2
      }
      case And(orderL, orderR) => orderL.computePrice + orderR.computePrice
      case Or(orderL, orderR) => math.min(orderL.computePrice, orderR.computePrice)
      case _ => 0.0
    }

    /**
      * Return the output text of the current node, in order to write it in console.
      * @return the output text of the current node
      */
    def reply: String = this match {
      // Example cases
      case Thirsty() => "Eh bien, la chance est de votre côté, car nous offrons les meilleures bières de la région !"
      case Hungry() => "Pas de soucis, nous pouvons notamment vous offrir des croissants faits maisons !"
      //Added
      case Login(name) => {
        UsersInfo.login(name.tail)
        "Bonjour %s !".format(UsersInfo.getCurrentUsername())}
      case Order(n, product) => "%d %s".format(n, product.brand)
      case And(orderL, orderR) => "%s et %s".format(orderL.reply, orderR.reply)
      case Or(orderL, orderR) => orderL match {
        case _ if orderL.computePrice <= orderR.computePrice => orderL.reply
        case _ => orderR.reply;
      }
      case Info(order) => "Cela coûte CHF %.1f".format(order.computePrice)
      case TotalOrder(order) => {
        val username = UsersInfo.getCurrentUsername()
        if(UsersInfo.getCurrentUsername() == null) {
          "Veuillez d'abord vous identifier"
        } else {
          val price = order.computePrice
          "Voici donc %s! Cela coûte CHF %.1f et votre nouveau solde est de CHF %.1f."
            .format( order.reply, price, UsersInfo.purchase(UsersInfo.getCurrentUsername(), price))}
        }
      case Balance() =>
        if(UsersInfo.getCurrentUsername() == null) {
          "Veuillez d'abord vous identifier"
        } else {
          "Le montant actuel de votre solde est de CHF %.1f".format(UsersInfo.getCurrentBalance())
        }
      case _ => ""
    }

  }

  /**
    * Declarations of the nodes' types.
    */
  // Example cases
  case class Thirsty() extends ExprTree
  case class Hungry() extends ExprTree
  // Added
  case class Login(name: String) extends ExprTree
  case class Product(productType : Token, brand : String = null) extends ExprTree
  case class Order(n: Int, product: Product) extends ExprTree
  case class And(orderL: Order, orderR: ExprTree) extends ExprTree
  case class Or(orderL: Order, orderR: ExprTree) extends ExprTree

  case class Info(complexOrder: ExprTree) extends ExprTree
  case class TotalOrder(complexOrder: ExprTree) extends ExprTree
  case class Balance() extends ExprTree
}
