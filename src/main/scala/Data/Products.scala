package Data

object Products {
  // TODO: step 2 - here your will have an attribute that will contain the products (e.g. "bière"), their types (e.g. "Boxer"), and their prices (e.g. 2.0).
  // TODO: step 2 - You will also have to find a way to store the default type/brand of a product.

  private val productList : List[(String, Double)] = List(
    ("Boxer", 1.00),
    ("Farmer", 1.00),
    ("Wittekop", 2.00),
    ("PunkIPA", 3.00),
    ("Jackhammer", 3.00),
    ("Ténébreuse", 4.00)
  )

  def getProduct(name : String = "Boxer"): Option[(String, Double)] = {
    return productList.find(t => t._1 == name)
  }
}
