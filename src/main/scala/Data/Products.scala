package Data

object Products {
  // TODO: step 2 - here your will have an attribute that will contain the products (e.g. "bière"), their types (e.g. "Boxer"), and their prices (e.g. 2.0).
  // TODO: step 2 - You will also have to find a way to store the default type/brand of a product.

  type ProductType = Int

  val CROISSANT : ProductType = 0
  val BEER : ProductType = 1

  private val productList : List[(String, Double, ProductType)] = List(
    ("Boxer", 1.00, BEER),
    ("Farmer", 1.00, BEER),
    ("Wittekop", 2.00, BEER),
    ("PunkIPA", 3.00, BEER),
    ("Jackhammer", 3.00, BEER),
    ("Ténébreuse", 4.00, BEER),
    ("Maison", 2.00, CROISSANT),
    ("Cailler", 2.00, CROISSANT)
  )

  def contains(name : String): Boolean = {
    productList.exists(_._1 == name)
  }

  def getDrink(name : String = "Boxer"): Option[(String, Double, ProductType)] = {
    productList.find(t => t._1 == name && t._3 == BEER)
  }

  def getCroissant(name : String = "Maison"): Option[(String, Double, ProductType)] = {
    productList.find(t => t._1 == name && t._3 == CROISSANT)
  }
}
