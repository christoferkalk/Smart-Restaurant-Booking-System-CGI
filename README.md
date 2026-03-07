





---------
tööks kulunud aeg 70min 06.03.26

Esimese asjana seadistasin initializr'iga Spring Booti, et luua alus oma projektile. Seejärel lõin kaks peamist entityt - Reservation ja RestaurantTable. Siis lisasin neile väljad mis arvasin, et on vajalikud, kaasa arvatud x- ja yPosition, mida võiks meil äkki hiljem vaja minna (abivahendina kasutasin üht oma varasemalt tehtud REST API databaasi ja  Claude'i – x ja y koordinaatide idee tuli sealt, samuti mõned selgitused @-annotationite kohta). Samuti panin need mudelid omavahel suhestuma @OneToMany ja @ManyToOne'ga. Nüüd oli väärtustele vaja gettereid ja settereid, et teised klassid neile ka ligi pääseksid. Kuna siiamaani olen need alati ise käsitsi välja kirjutanud siis nüüd otsustasin kasutada Lombokit, millest olen teadlik, aga kasutanud ei ole. Lombok.Data'ga ei ole vaja eraldi gettereid ja settereid välja kirjutada, tuleb vaid lisada  lombok dependecy, entityle @Data ja tööriist genereerib vajalikud getterid/setterid automaatselt! Mega tööriist ausalt! 
Samuti olen commitinud oma kirjutatud koodi peale igat märgatavat sammu - kord peale Spring Booti seadistust, siis peale esimese mudeli tekitamist, siis teise jne. Nii on asjadest hea ülevaade ja saab alati vajadusel sammu tagasi võtta.

07.03.26 11:15-...

Alustasin repository folderiga, kuhu lisasin kaks esialgu suht tühja faili - ReservationRepository.java ja RestaurantTableRepository.java. Importisin JpaRepository, mis annab meile automaatselt kõik põhilised andmebaasi operatsioonid kätte ilma, et peaksime midagi ise kirjutama. Samuti asendan classi seekord interface'iga ja selle ette @Repository, et lasta Spring Bootil töö ise ära teha ja 