package Chat

import Data.Products.{BEER, CROISSANT, ProductType, getCroissant, getDrink}

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
        case BEER => n * getDrink(product.brand).get._2                  //TODO getorElse?
        case CROISSANT => n * getCroissant(product.brand).get._2
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
      case Login(name) => "Bonjour " + name.tail + " !"
        //TODO auth
      case Order(n, product) => "%d %s %s".format(n, product.productType, product.brand)
      case And(orderL, orderR) => "%s et %s".format(orderL.reply, orderR.reply)
      case Or(orderL, orderR) => orderL match {
        case _ if orderL.computePrice <= orderR.computePrice => orderL.reply
        case _ => orderR.reply;
      }
      case Info(order) => "Cela coûte CHF %.1f".format(order.computePrice)
      case Balance() => "Le montant actuel de votre solde est de CHF %.1f".format(0f)
      case ComplexOrder(order) => "Voici donc %s! Cela coûte CHF %.1f et votre nouveau solde est de CHF %.1f."
        .format( order.reply, order.computePrice ,0f)
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
  case class Order(n: Int, product: Product) extends ExprTree
  case class And(orderL: Order, orderR: Order) extends ExprTree
  case class Or(orderL: Order, orderR: Order) extends ExprTree
  case class Product(productType : ProductType, brand : String) extends ExprTree
  case class Info(complex_order: ExprTree) extends ExprTree
  case class Balance() extends ExprTree
  case class ComplexOrder(complexOrder: ExprTree) extends ExprTree
}
