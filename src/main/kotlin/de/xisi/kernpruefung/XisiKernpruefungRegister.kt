package de.xisi.kernpruefung

import de.xisi.kernpruefung.api.XisiKernpruefung
import de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung.DSBE__3
import de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung.DSBE__4
import de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung.DSBE__5
import de.xisi.kernpruefung.fachlichkeit.verfahren.dabpv.DABPV__1_0_0
import de.xisi.kernpruefung.fachlichkeit.verfahren.dsko.DSKO__4
import de.xisi.kernpruefung.fachlichkeit.verfahren.eubp.*

object XisiKernpruefungRegister {
  val mitgelieferte_verfahren: List<Class<out XisiKernpruefung>> = listOf(
    DSKO__4::class.java,

    DSBE__3::class.java,
    DSBE__4::class.java,
    DSBE__5::class.java,

    DABPV__1_0_0::class.java,

    DSST__4::class.java,
    DSKB__6::class.java,
    DSKT__1::class.java,
    DSBU__1::class.java,
    DSKA__1::class.java,
  )
}
