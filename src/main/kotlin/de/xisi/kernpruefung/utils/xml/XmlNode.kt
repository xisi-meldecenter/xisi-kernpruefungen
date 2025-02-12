package de.xisi.kernpruefung.utils.xml

class XmlNode(
) {
  var name = ""
  var namespace = ""
  var value: String = ""

  constructor(namespace: String, name: String) : this() {
    this.name = name
    this.namespace = namespace
  }

  val children: List<XmlNode> = ArrayList()
  val attributes: List<XmlAttribute> = ArrayList()

  val childrenIterator = ChildrenIterator(children)

  override fun hashCode() = "$namespace$name".hashCode()
  override fun equals(other: Any?) = (other as? XmlNode)?.name == name && other.namespace == namespace

  override fun toString() = "($namespace) $name"
}

class ChildrenIterator(private val children: List<XmlNode>) {
  private var index = 0
  fun expectNextChild(expectedElement: XmlNode, notFound: () -> Unit, found: (XmlNode) -> Unit) {
    val nextChild = children.getOrNull(index)
    if (nextChild == expectedElement) {
      index++
      found(nextChild)
    } else {
      notFound()
    }
  }
}

class XmlAttribute(val name: String, val value: String)