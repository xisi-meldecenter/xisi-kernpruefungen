package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.XisiKernpruefungsService
import org.junit.jupiter.api.Test

class DSBU__1Test {
    private val ds_gueltig =
        "DSBUEUBP 12345678       66667777       012025010809092000000                                                                                                    12345678                                                                                            200141                              2023022820230228                                                                                                    0                                   000005000001060100                              +0000000000000000018560000+0000000000000000000000000                                                                      00000                                                                                                                                                                                                                            000002060200                              +0000000000000000004180000+0000000000000000000000000                                                                      00000                                                                                                                                                                                                                            000003060400                              +0000000000000000000589000+0000000000000000000000000                                                                      00000                                                                                                                                                                                                                            000004060500                              +0000000000000000000130000+0000000000000000000000000                                                                      00000                                                                                                                                                                                                                            000005037900                              +0000000000000000000000000+0000000000000000023459000                                                                      00000                                                                                                                                                                                                                            E"

    private val xkps = XisiKernpruefungsService()

    @Test
    fun `ds gueltig`() {
        xkps.pruefeMeldung(ds_gueltig, "DSBU", "1")
            .exceptionBeiFehlern()
    }
}
