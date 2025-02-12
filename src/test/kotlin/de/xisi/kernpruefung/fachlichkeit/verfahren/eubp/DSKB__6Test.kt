package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.XisiKernpruefungsService
import org.junit.jupiter.api.Test

class DSKB__6Test {
    private val ds_gueltig =
        "DSKBEUBP 12345678       66667777       06202501010102030000012345678                                                                                            01100                                2024010120241231Hauptkasse                                                                                                                                            +0000000000000000000444610+0000000000000000000000000-0000000000000000000095200+0000000000000000000000000000000052024010120240101991                                Kontoeröffnung                                                                                      1000001                             0+0000000000000000000444610+000000000000000000000000000000                                                                      2024010120240101210                                                                                                                                    1000002                             0+0000000000000000000020000+000000000000000000000000000000KZ - Nicht steuerbar (keine USt oder VSt Berücksichtigung             2024010120240101600                                                                                                                                    1000003                             0-0000000000000000000050000+000000000000000000000000001900KZ66 Kosten mit Vorsteuer                                             2024010120240101600                                                                                                                                    1000004                             0-0000000000000000000030000+000000000000000000000000001900KZ66 Kosten mit Vorsteuer                                             2024010120240101620                                                                                                                                    1000005                             0-0000000000000000000035200+000000000000000000000000000700KZ66 Kosten mit Vorsteuer                                             E"

    private val xkps = XisiKernpruefungsService()

    @Test
    fun `ds gueltig`() {
        xkps.pruefeMeldung(ds_gueltig, "DSKB", "6")
            .exceptionBeiFehlern()
    }
}
