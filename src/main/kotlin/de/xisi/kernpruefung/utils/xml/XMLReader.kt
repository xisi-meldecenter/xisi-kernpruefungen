package de.xisi.kernpruefung.utils.xml

import org.w3c.dom.Attr
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


object XMLReader {
  fun read(xml: String): XmlNode {
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val doc = builder.parse(InputSource(StringReader(xml)))
    val rootTag = doc.documentElement
    rootTag.normalize()

    return buildNode(rootTag)
  }

  private fun buildNode(tag: Element, incomingNamespace: String = "", namespaceMap: HashMap<String, String> = HashMap()): XmlNode {
    var currentNamespace = incomingNamespace
    val node = XmlNode()
    val tagName = tag.tagName
    val (prefix, name) = if (tagName.contains(":")) tagName.split(":").let { it[0] to it[1] } else "" to tagName
    node.name = name

    for (attribuesIndex in 0 until tag.attributes.length) {
      val attr = tag.attributes.item(attribuesIndex) as Attr
      val attrName = attr.name
      val attrValue = attr.value

      when {
        attrName == "xmlns" -> currentNamespace = attrValue
        attrName.startsWith("xmlns:") -> namespaceMap[attrName.drop("xmlns:".length)] = attrValue
        else -> (node.attributes as MutableList).add(XmlAttribute(attrName, attrValue))
      }
    }

    if (prefix == "") node.namespace = currentNamespace
    else node.namespace = namespaceMap.getOrElse(prefix) { null } ?: error("Prefix $prefix nicht definiert")


    for (childIndex in 0 until tag.childNodes.length) {
      val child = tag.childNodes.item(childIndex)
      when (child.nodeType) {
        Node.ELEMENT_NODE -> (node.children as MutableList).add(buildNode(child as Element, currentNamespace, namespaceMap))
        Node.CDATA_SECTION_NODE -> node.value = child.nodeValue
        Node.TEXT_NODE -> {
          val value = child.nodeValue
          if (value.replace(" ", "").replace("\n", "").isNotEmpty())
            node.value = value
        }
      }
    }

    return node
  }


}
