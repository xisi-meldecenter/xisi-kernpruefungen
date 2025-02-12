package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.XisiKernpruefungsService
import org.junit.jupiter.api.Test

class DSKA__1Test {
    private val ds_gueltig =
        "DSKAEUBP 12345678       66667777       012024080910430130000ABCDEFG                                                                                             0500000000010 Verwaltungshaushalt                                                                                                                                 22023010120231231                          0000000000000000020.0200 Hauptverwaltung                                                                                                                                22023010120231231                          0000000000000000030.0200.4600 Personal-Nebenausgaben                                                                                                                    22023010120231231                          0000000000000000040.0200.4600.0 Personal-Nebenausgaben                                                                                                                  22023010120231231+0000000000000000000038977000000022023031520230401                                   Beleg 2                                                                                             2                                   0+0000000000000000000015000                                                                           2023072920230815                                   Beleg 3                                                                                             3                                   0+0000000000000000000023977                                                                           00000000040.0200.4600.1 Andere Nebenausgaben                                                                                                                    22023010120231231+0000000000000000000006600000000032023031520230401                                   Beleg 1                                                                                             1                                   0+0000000000000000000001100                                                                           2023072920230815                                   Beleg 2                                                                                             2                                   0+0000000000000000000002200                                                                           2023072920230815                                   Beleg 3                                                                                             3                                   0+0000000000000000000003300                                                                           E"

    private val xkps = XisiKernpruefungsService()

    @Test
    fun `ds gueltig`() {
        xkps.pruefeMeldung(ds_gueltig, "DSKA", "1")
            .exceptionBeiFehlern()
    }
}
