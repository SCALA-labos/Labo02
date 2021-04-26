package Chat

import Data.Products.ProductType

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
    def computePrice: Double = ???

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
        //TODO auth, price
      case Order(n, product) => "Voici donc %d %s %s! Cela coûte CHF %.1f et votre nouveau solde est de CHF %f.".format(n, product.productType, product.brand ,0f ,0f)
      case Info(order) => "Cela coûte CHF %.1f".format(0f)
      case Balance() => "Le montant actuel de votre solde est de CHF %.1f".format(0f)
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
  case class And() extends ExprTree
  case class Or() extends ExprTree
  case class Product(productType : ProductType, brand : String) extends ExprTree
  case class Info(order: Order) extends ExprTree
  case class Balance() extends ExprTree
}
