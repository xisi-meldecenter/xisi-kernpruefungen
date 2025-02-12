package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.XisiKernpruefungsService
import org.junit.jupiter.api.Test

class DSKT__1Test {
    private val ds_gueltig =
        "DSKTEUBP 12345678       66667777       012025010101020300000                                                                                                    12345678                                                                                            1060500                              2024010120241231Ehegattengehalt                                                                                                                                                                                                                                                                                             +0000000000000000000000000+0000000000000000000000000+0000000000000000000390000+0000000000000000000000000E"

    private val xkps = XisiKernpruefungsService()

    @Test
    fun `ds gueltig`() {
        xkps.pruefeMeldung(ds_gueltig, "DSKT", "1")
            .exceptionBeiFehlern()
    }
}
