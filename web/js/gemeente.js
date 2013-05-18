$(document).ready(function()
{


    var people = [
        '9300 Aalst', '9308 Aalst', '9310 Aalst', '9320 Aalst', '9880 Aalter', '9881 Aalter', '3200 Aarschot', '3201 Aarschot', '3202 Aarschot', '2630 Aartselaar',
        '1790 Affligem', '3570 Alken', '8690 Alveringem', '8691 Alveringem', '2000 Antwerpen', '2018 Antwerpen', '2020 Antwerpen', '2030 Antwerpen', '2040 Antwerpen',
        '2050 Antwerpen', '2060 Antwerpen', '2100 Antwerpen', '2140 Antwerpen', '2170 Antwerpen', '2170 Antwerpen', '2180 Antwerpen', '2600 Antwerpen', '2610 Antwerpen',
        '2660 Antwerpen', '8570 Anzegem', '8572 Anzegem', '8573 Anzegem', '8850 Ardooie', '8851 Ardooie', '2370 Arendonk', '3665 As', '3668 As', '1730 Asse', '1731 Asse',
        '9960 Assenede', '9961 Assenede', '9968 Assenede', '8580 Avelgem', '8581 Avelgem', '8582 Avelgem', '8583 Avelgem', '2387 Baarle-Hertog', '2490 Balen', '2491 Balen',
        '8730 Beernem', '2340 Beerse', '1650 Beersel', '1651 Beersel', '1652 Beersel', '1653 Beersel', '1654 Beersel', '3130 Begijnendijk', '3460 Bekkevoort', '3461 Bekkevoort',
        '3580 Beringen', '3581 Bergingen', '3582 Bergingen', '3583 Beringen', '2590 Berlaar', '9290 Berlare', '3060 Bertem', '3061 Bertem', '1547 Bever', '9120 Beveren', '9130 Beveren',
        '3360 Bierbeek', '3740 Bilzen', '3742 Bilzen', '3746 Bilzen', '8370 Blankenberge', '3950 Bocholt', '2530 Boechout', '2531 Boechout', '2820 Bonheiden', '2850 Boom',
        '3190 Boortmeerbeek', '3191 Boortmeerbeek', '3840 Borgloon', '2880 Bornem', '2150 Borsbeek', '3370 Boutersem', '9660 Brakel', '9661 Brakel', '2930 Brasschaat', '2960 Brecht',
        '8450 Bredene', '3960 Bree', '8000 Brugge', '8200 Brugge', '8310 Brugge', '8380 Brugge', '9255 Buggenhout', '8340 Damme', '8420 De Haan', '8421 De Haan', '9840 De Pinte',
        '8540 Deerlijk', '9800 Deinze', '9470 Denderleeuw', '9472 Denderleeuw', '9473 Denderleeuw', '9200 Dendermonde', '8720 Dentergem', '2480 Dessel', '9070 Destelbergen', '3590 Diepenbeek',
        '3290 Diest', '3293 Diest', '3294 Diest', '8600 Diksmuide', '1700 Dilbeek', '1701 Dilbeek', '1702 Dilbeek', '1703 Dilbeek', '3650 Dilsen-Stokkem', '1620 Drogenbos', '2570 Duffel',
        '2650 Edegem', '9900 Eeklo', '9420 Erpe-Mere', '2910 Essen', '9940 Evergem', '1570 Galmaarden', '9890 Gavere', '2440 Geel', '3450 Geetbets', '3454 Geetbets', '3600 Genk', '9000 Gent',
        '9030 Gent', '9031 Gent', '9032 Gent', '9040 Gent', '9041 Gent', '9042 Gent', '9050 Gent', '9051 Gent', '9052 Gent', '9500 Geraardsbergen', '9506 Geraardsbergen', '3890 Gingelom',
        '3891 Gingelom', '8470 Gistel', '3380 Glabbeek', '3381 Glabbeek', '3384 Glabbeek', '1755 Gooik', '1850 Grimbergen', '1851 Grimbergen', '1852 Grimbergen', '1853 Grimbergen',
        '2280 Grobbendonk', '2288 Grobbendonk', '3150 Haacht', '9450 Haaltert', '9451 Haaltert', '3545 Halen', '1500 Halle', '1501 Halle', '1502 Halle', '3945 Ham', '9220 Hamme',
        '3930 Hamont-Achel', '8530 Harelbeke', '8531 Harelbeke', '3500 Hasselt', '3501 Hasselt', '3510 Hasselt', '3511 Hasselt', '3512 Hasselt', '3940 Hechtel-Eksel', '3941 Hechtel-Eksel',
        '3870 Heers', '2220 Heist-op-den-Berg', '2221 Heist-op-den-Berg', '2222 Heist-op-den-Berg', '2223 Heist-op-den-Berg', '2620 Hemiksem', '3020 Herent', '2200 Herentals', '2270 Herenthout',
        '3540 Herk-de-Stad', '1540 Herne', '1541 Herne', '2230 Herselt', '3717 Herstappe', '9550 Herzele', '9551 Herzele', '9552 Herzele', '3550 Heusden-Zolder', '8950 Heuvelland',
        '8951 Heuvelland', '8952 Heuvelland', '8953 Heuvelland', '8954 Heuvelland', '8956 Heuvelland', '8958 Heuvelland', '3320 Hoegaarden', '3321 Hoegaarden', '1560 Hoeilaart', '3730 Hoeselt',
        '3732 Hoeselt', '8830 Hooglede', '2320 Hoogstraten', '2321 Hoogstraten', '2322 Hoogstraten', '2323 Hoogstraten', '2328 Hoogstraten', '9667 Horebeke', '3530 Houthalen-Helchteren',
        '3650 Houthulst', '2540 Hove', '3040 Huldenberg', '2235 Hulshout', '8480 Ichtegem', '8900 Ieper', '8902 Ieper', '8904 Ieper', '8906 Ieper', '8908 Ieper', '8770 Ingelmunster',
        '8870 Izegem', '8490 Jabbeke', '2920 Kalmthout', '1910 Kampenhout', '1880 Kapelle-op-den-Bos', '2950 Kapellen', '9970 Kaprijke', '9971 Kaprijke', '2460 Kasterlee', '3140 Keerbergen',
        '3640 Kinrooi', '9690 Kluisbergen', '9910 Knesselare', '8300 Knokke-Heist', '8301 Knokke-Heist', '8680 Koekelare', '8670 Koksijde', '2550 Kontich', '8610 Kortemark', '3470 Kortenaken',
        '3471 Kortenaken', '3472 Kortenaken', '3473 Kortenaken', '3070 Kortenberg', '3071 Kortenberg', '3078 Kortenberg', '3720 Kortessem', '3721 Kortessem', '3722 Kortessem', '3723 Kortessem',
        '3724 Kortessem', '8500 Kortrijk', '8501 Kortrijk', '8510 Kortrijk', '8511 Kortrijk', '1950 Kraainem', '9150 Kruibeke', '9770 Kruishoutem', '9771 Kruishoutem', '9772 Kruishoutem',
        '8520 Kuurne', '2430 Laakdal', '2431 Laakdal', '9270 Laarne', '3620 Lanaken', '3621 Lanaken', '3400 Landen', '3401 Landen', '3404 Landen', '8920 Langemark-Poelkapelle', '9280 Lebbeke',
        '9340 Lede', '8880 Ledegem', '8860 Lendelede', '1750 Lennik', '3970 Leopoldsburg', '3971 Leopoldsburg', '3000 Leuven', '3001 Leuven', '3010 Leuven', '3012 Leuven', '3018 Leuven',
        '8810 Lichtervelde', '1770 Liedekerke', '2500 Lier', '9570 Lierde', '9571 Lierde', '9572 Lierde', '2275 Lille', '1630 Linkebeek', '2547 Lint', '3350 Linter', '8647 Lo-Reninge',
        '9080 Lochristi', '9160 Lokeren', '3920 Lommel', '1840 Londerzeel', '9920 Lovendegem', '9921 Lovendegem', '3210 Lubbeek', '3211 Lubbeek', '3212 Lubbeek', '3560 Lummen', '9680 Maarkedal',
        '9681 Maarkedal', '9688 Maarkedal', '3680 Maaseik', '3630 Maasmechelen', '3631 Maasmechelen', '1830 Machelen', '1831 Machelen', '9990 Maldegem', '9991 Maldegem', '9992 Maldegem',
        '2390 Malle', '2800 Mechelen', '2801 Mechelen', '2811 Mechelen', '2812 Mechelen', '2450 Meerhout', '3670 Meeuwen-Gruitrode', '1860 Meise', '1861 Meise', '9090 Melle', '8930 Menen',
        '1785 Merchtem', '9820 Merelbeke', '2330 Merksplas', '8957 Mesen', '8760 Meulebeke', '8430 Middelkerke', '8431 Middelkerke', '8432 Middelkerke', '8433 Middelkerke', '8434 Middelkerke',
        '9180 Moerbeke', '2400 Mol', '8890 Moorslede', '2640 Mortsel', '9810 Nazareth', '3910 Neerpelt', '9850 Nevele', '2845 Niel', '3850 Niewerkerken', '8620 Nieuwpoort', '2560 Nijlen',
        '9400 Ninove', '9401 Ninove', '9402 Ninove', '9403 Ninove', '9404 Ninove', '9406 Ninove', '2250 Olen', '8400 Oostende', '9860 Oosterzele', '8020 Oostkamp', '8780 Oostrozebeke',
        '3660 Opglabbeek', '1745 Opwijk', '3050 Oud-Heverlee', '3051 Oud-Heverlee', '3052 Oud-Heverlee', '3053 Oud-Heverlee', '3054 Oud-Heverlee', '2360 Oud-Turnhout', '9700 Oudenaarde',
        '8460 Oudenburg', '3090 Overrijse', '3900 Overpelt', '3990 Peer', '1670 Pepingen', '1671 Pepingen', '1672 Pepingen', '1673 Pepingen', '1674 Pepingen', '8740 Pittem', '8970 Poperinge',
        '8972 Poperinge', '8978 Poperinge', '2580 Putte', '2870 Puurs', '2520 Ranst', '2380 Ravels', '2381 Ravels', '2382 Ravels', '2470 Retie', '3370 Riemst', '2310 Rijkevorsel', '8800 Roeselare',
        '9600 Ronse', '1760 Roosdaal', '1761 Roosdaal', '3110 Rotselaar', '3111 Rotselaar', '3118 Rotselaar', '8755 Ruiselede', '2840 Rumst', '2627 Schelle', '3270 Scherpenheuvel-Zichem', '3271 Scherpenheuvel-Zichem',
        '3272 Scherpenheuvel-Zichem', '2970 Schilde', '2900 Schoten', '2890 Sint-Amands', '1640 Sint-Genesius-Rode', '9170 Sint-Gillis-Waas', '2860 Sint-Katelijne-Waver', '2861 Sint-Katelijne-Waver',
        '9980 Sint-Laureins', '9981 Sint-Laureins', '9982 Sint-Laureins', '9988 Sint-Laureins', '9520 Sint-Lievens-Houtem', '9521 Sint-Lievens-Houtem', '9830 Sint-Martens-Latem', '9831 Sint-Martens-Latem',
        '9100 Sint-Niklaas', '9111 Sint-Niklaas', '9112 Sint-Niklaas', '1600 Sint-Pieters-Leeuw', '1601 Sint-Pieters-Leeuw', '1602 Sint-Pieters-Leeuw', '3800 Sint-Truiden', '3803 Sint-Truiden', '3806 Sint-Truiden',
        '8587 Spiere-Helkijn', '2940 Stabroek', '8840 Staden', 'Steenokkerzeel 1820', '9190 Stekene', '9140 Temse', '1740 Ternat', '1741 Ternat', '1742 Ternat', '3080 Tervuren', '3980 Tessenderlo', '8700 Tielt',
        '3390 Tielt-Winge', '3391 Tielt-Winge', '3300 Tienen', '3700 Tongeren', '8820 Torhout', '3120 Tremelo', '3128 Tremelo', '2300 Turnhout', '8630 Veurne', '1800 Vilvoorde', '8640 Vleteren', '3790 Voeren',
        '3791 Voeren', '3792 Voeren', '3793 Voeren', '3798 Voeren', '3790 Voeren', '2290 Vorselaar', '2350 Vosselaar', '9950 Waarschoot', '9250 Waasmunster', '9185 Wachtebeke', '8790 Waregem', '8791 Waregem',
        '8792 Waregem', '8793 Waregem', '3830 Wellen', '3831 Wellen', '3832 Wellen', '1780 Wemmel', 'Wervik 8940', '2260 Westerlo', '9230 Wetteren', '8560 Wevelgem', '1970 Wezembeek-Oppem', '9260 Wichelen', '8710 Wielsbeke',
        '2110 Wijnegem', '2830 Willebroek', '8750 Wingene', '2160 Wommelgem', '9790 Wortegem-Petegem', '2990 Wuustwezel', '2240 Zandhoven', '2242 Zandhoven', '2243 Zandhoven', '1930 Zaventem', '1932 Zaventem', '1933 Zaventem',
        '8210 Zedelgem', '8211 Zedelgem', '9240 Zele', '9060 Zelzate', '1980 Zemst', '1981 Zemst', '1982 Zemst', '9750 Zingem', '2980 Zoersel', '9932 Zomergem', '9931 Zomergem', '9930 Zomergem', '3520 Zonhoven', '8980 Zonnebeke',
        '9620 Zottegem', '3440 Zoutleeuw', '8377 Zuienkerke', '9780 Zulte', '3690 Zutendaal', '9780 Zulte', '9630 Zwalm', '9636 Zwalm', '8550 Zwevegem', '8551 Zwevegem', '8552 Zwevegem', '8553 Zwevegem', '8554 Zwevegem', '2070 Zwijndracht'];

    $("#search").autocomplete({
        source: people,
        minLength: 2

    }).click(function() {
        $(this).val("");
        $(this).autocomplete("search");
    });
});