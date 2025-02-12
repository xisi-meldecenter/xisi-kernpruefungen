package de.xisi.kernpruefung.utils.xml

import de.xisi.kernpruefung.mustBe
import org.junit.jupiter.api.Test

class XMLReaderTest {
  val xml = """
    <tutorials xmlns="abc" xmlns:x1="x123">
        <tutorial tutId="01" type="java" xmlns:x2="x545">
            <x1:title>Guava</x1:title>
            <x2:description>Introduction to Guava</x2:description>
            <foo xmlns="lo">
              <innerfoo>abc</innerfoo>
            </foo>
            <no-fo><![CDATA[  hui <test> ]]></no-fo>
        </tutorial>
    </tutorials>
  """.trimIndent()

  @Test
  fun `XMLReader kann lesen`() {
    val node = XMLReader.read(xml)


    node.name mustBe "tutorials"
    node.namespace mustBe "abc"
    node.attributes.size mustBe 0

    node.children.size mustBe 1
    val tutorial = node.children[0]
    tutorial.name mustBe "tutorial"
    tutorial.namespace mustBe "abc"
    tutorial.value mustBe ""
    tutorial.attributes.size mustBe 2

    tutorial.attributes[0].name mustBe "tutId"
    tutorial.attributes[0].value mustBe "01"

    tutorial.attributes[1].name mustBe "type"
    tutorial.attributes[1].value mustBe "java"

    tutorial.children.size mustBe 4
    tutorial.children[0].name mustBe "title"
    tutorial.children[0].namespace mustBe "x123"
    tutorial.children[0].value mustBe "Guava"
    tutorial.children[0].children.size mustBe 0

    tutorial.children[1].name mustBe "description"
    tutorial.children[1].namespace mustBe "x545"
    tutorial.children[1].value mustBe "Introduction to Guava"
    tutorial.children[1].children.size mustBe 0

    tutorial.children[2].name mustBe "foo"
    tutorial.children[2].namespace mustBe "lo"
    tutorial.children[2].value mustBe ""
    tutorial.children[2].children.size mustBe 1

    tutorial.children[2].children[0].name mustBe "innerfoo"
    tutorial.children[2].children[0].namespace mustBe "lo"
    tutorial.children[2].children[0].value mustBe "abc"
    tutorial.children[2].children[0].children.size mustBe 0

    tutorial.children[3].name mustBe "no-fo"
    tutorial.children[3].namespace mustBe "abc"
    tutorial.children[3].value mustBe "  hui <test> "
    tutorial.children[3].children.size mustBe 0


    println(node)
  }
}