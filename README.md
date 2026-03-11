# Nutikas Restorani Reserveerimissüsteem

Veebirakendus restorani laudade broneerimiseks ja sobiva laua soovitamiseks. Rakendus näitab restorani saaliplaani, kus kasutaja saab filtreerida vabu laudu kellaaja, seltskonna suuruse ja eelistuste järgi ning broneerida sobiva laua.

## Funktsionaalsus

- Restorani saaliplaan kus hõivatud ja vabad lauad on visuaalselt eristatavad
- Filtreerimine kellaaja, seltskonna suuruse ja eelistuste järgi (aknakoht, vaikne nurk, terrass, privaatsaal, ligipääsetav)
- Soovitusalgoritm mis leiab sobivaima vaba laua skoori põhjal – arvestab seltskonna suurust ja eelistusi
- Laua broneerimine klikiga saaliplaanil
- Juhuslikult genereeritud broneeringud rakenduse käivitumisel
- Restoran avatud 11:00 – 23:00, broneeringuid saab teha kuni 21:00

## Tehnoloogiad

- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, H2 andmebaas
- **Frontend:** HTML, CSS, JavaScript
- **Muud:** Lombok, Maven

## Käivitamine

### Eeldused
- Java 21 installitud

### Sammud

1. Klooni repositoorium:
```bash
git clone https://github.com/christoferkalk/Smart-Restaurant-Booking-System-CGI.git
cd Smart-Restaurant-Booking-System-CGI
```

2. Käivita rakendus:
```bash
./mvnw spring-boot:run
```

3. Ava brauser ja mine aadressile:
```
http://localhost:8080
```

Rakendus genereerib käivitumisel automaatselt testandmed – lauad ja juhuslikud broneeringud järgmiste päevade peale.

## Projekti struktuur
```
src/main/java/com/example/demo/
├── controller/       # REST API endpointid
├── model/            # Andmemudelid (RestaurantTable, Reservation)
├── repository/       # Andmebaasi päringud
├── service/          # Äriloogika
└── DataInitializer   # Testandmete genereerimine

src/main/resources/
├── static/           # Frontend (index.html)
└── application.properties
```

## API endpointid

| Meetod | URL | Kirjeldus |
|--------|-----|-----------|
| GET | /api/tables | Kõik lauad |
| GET | /api/tables/available?dateTime= | Vabad lauad valitud kellaajal |
| GET | /api/reservations | Kõik broneeringud |
| POST | /api/reservations | Uus broneering |

## AI kasutamine

Kasutasin Claude'i abivahendina kogu projekti vältel. Frontend (index.html) on suuremas osas Claude'i genereeritud, kuna frontendiga on mul siiani veel vähe kogemust. Backend on kirjutatud peamiselt ise, Claude'i abi kasutasin selgituste ja konkreetsete probleemide lahendamisel. Kõik AI abil genereeritud koodijupid on dokumenteeritud.

## Arendaja märkmed

Proovisin peale igat git pushi jooksvalt dokumenteerida, et mida ja kuidas tegin. Ilmselt sai suurem osa kirja, aga vabalt võis midagi ka kahe silma vahele jääda.

06.03.26 80min 

Esimese asjana seadistasin initializr'iga Spring Booti, et luua alus oma projektile. Seejärel lõin kaks peamist entityt - Reservation ja RestaurantTable. Siis lisasin neile väljad mis arvasin, et on vajalikud, kaasa arvatud x- ja yPosition, mida võiks meil äkki hiljem vaja minna (abivahendina kasutasin üht oma varasemalt tehtud REST API databaasi ja  Claude'i – x ja y koordinaatide idee tuli sealt, samuti mõned selgitused @-annotationite kohta). Samuti panin need mudelid omavahel suhestuma @OneToMany ja @ManyToOne'ga. Nüüd oli väärtustele vaja gettereid ja settereid, et teised klassid neile ka ligi pääseksid. Kuna siiamaani olen need alati ise käsitsi välja kirjutanud siis nüüd otsustasin kasutada Lombokit, millest olen teadlik, aga kasutanud ei ole. Lombok.Data'ga ei ole vaja eraldi gettereid ja settereid välja kirjutada, tuleb vaid lisada  lombok dependecy, entityle @Data ja tööriist genereerib vajalikud getterid/setterid automaatselt! Mega tööriist ausalt! 
Samuti olen commitinud oma kirjutatud koodi peale igat märgatavat sammu - kord peale Spring Booti seadistust, siis peale esimese mudeli tekitamist, siis teise jne. Nii on asjadest hea ülevaade ja saab alati vajadusel sammu tagasi võtta.

07.03.26  230min

Alustasin repository folderiga, kuhu lisasin kaks esialgu suht tühja faili - ReservationRepository.java ja RestaurantTableRepository.java. Importisin JpaRepository, mis annab meile automaatselt kõik põhilised andmebaasi operatsioonid kätte ilma, et peaksime midagi ise kirjutama. Samuti asendan classi seekord interface'iga ja selle ette @Repository, et lasta Spring Bootil töö meie eest ära teha.
Seejärel on aeg tegeleda service layeriga - Selleks teeme uue folderi ja lisame sinna ReservationService.java ja RestaurantTableService.java, mis sisaldavad hetkel ainult kaht lihtsat meetodit - kõigi laudade/broneeringute küsimine ja salvestamine. Hiljem saame sinna lisama hakata juba keerulisemat loogikat, aga praegu on see piisav. Nüüd on aeg controller layeri jaoks, jällegi uus folder controller, kuhu tulevad eraldi controller failid broneeringutele ja laudadele. Classi ette @RestController, mis ütleb Bootile, et ta võtaks vastu ja tagastaks päringuid. Samuti @RequestMapping("/api/tables"), mis tähendab, et kõik selle controlleri endpointid algavad selle aadressiga. Kui algne Controller layer on tehtud siis on põhimõtteliselt programmi skelett valmis. Töö järjekord võiks olla midagi sellist: Request->Controller->Service->Repo->Andmebaas. Kogu nende eraldi layerite mõte on hoida programmi ülesandeid lahus (Ingliskeeles Seperation of Concerns). Niimoodi on programm skaleeritav ja igal layeril on oma kindel ülesanne. Kui kuskil peaks tekkima viga, on seda lihtsam leida ja parandada.
Lõpuks saab vähehaaval testima hakata. Käivitan rakenduse ja vaatan mida localhost meile annab. Esialgu saan vastuseks lihtalt tühja JSONi - [], sest andmebaas on tühi. Aga see on hea, sest endpoint töötab korrektselt ja andmebaas on meil tõepoolest veel tühi. Et andmebaas täita lasen Claudeil genereerida omale faili DataInitializer.java, mis peaks jooksma appi käivitamisel ja lisama andmebaasi testandmed laudade ja broneeringutega. Käivitan programmi uuesti ja uurin Localhost URL'i. Seekord tulevad andmed läbi, aga lõputult. Õnneks tuleb mul see probleem meelde oma eelmisest REST API projektist! Tegu on infinite recursioniga. See tähendab, et RestaurantTable sisaldab Reservation listi -> Reservation sisaldab RestaurantTable -> see omakorda sisaldab jälle Reservation listi -> ja nii lõputult. Et seda lõputult suhestumist peatada, peame lisama Restauranttable mudelisse @JsonManagedReference ja Reservation mudelisse @JsonBackReference. Need annotationid ütlevad JSON'ile, et ta ei serialiseeriks seda suhet mõlemast otsast, vaid ainult ühest. @JsonManagedReference on siis "parent" pool mis kuvatakse ja @JsonBackReference on "child" mis jäetakse JSON vastusest välja. Käivitan programmi uuesti ja seekord saan juba puhtama Jsoni - mõned lauad on broneeritud, mõned vabad, kõik tundub, et on nii nagu peab. Algne backend osa on nüüd sisuliselt valmis - andmed on olemas, endpointid töötavad. Järgmine samm oleks frontend. Kuna frontendiga olen veel vähe kokku puutunud siis lasin HTML/CSS/JS struktuuri luua Claude'il! Üldiselt saan aru selle koodi kontseptist ja mida see teeb, kuid peast seda kõike ise kirjutada kindlasti ei suudaks. Claude'i genereeritud index.html failist leiame Fetch requestid backendilt, jaotame lauad gridil, erinevad värvid laua staatuse järgi ja algorütm sobiva laua leidmiseks. Käivitan Spring Booti ja näen Claudei genereeritud lehte, mis näeb ausaltöeldes päris hea välja, kuid on veel natukene puudulik. Kella ja kuupäeva filter puudub täielikult, mis on broneeringute A ja O. Kuna ei mäletanud täpselt selle uue labeli input typei siis küsisin jällegi Claudeilt abi ning sain lihtsa Kuupäeva ja kellaaja labeli, mille sain ilusti index.html-is filtrite divi paigutada. Nüüd ilmus lehele ka vajalik kuupäeva ja kellaaja filter! Järgmiseks oleks see filter kuidagi ka päriselt tööle vaja panna, hetkel ma backendis seda veel implementeerinud ei ole. Tean, et kui kasutaja valib kuupäeva ja kellaaja siis peaks backend kontrollima, kas sellele ajale on juba mõni teine kasutaja laua broneerinud ja kui kauaks. Pean natukene Claudeiga nõu ja uurin kuidas ta sellele läheneks. Saan talt tagasi natukene vigase loogika(KUNAGI ÄRA VÕTA AI-KOODI KUI MINGIT UNIVERSAALSET TÕTT!!!), teen väikesed muudatused ja lisame ReservationRepository'sse päringu mis leiab broneeringud ajavahemiku järgi - ette on vaja panna ka @Query, sest kirjutame selle spetsiifilise päringu laudade leidmiseks ise! Nüüd lisan uue päringu, mis leiab broneeringud ajavahemiku järgi ka ReservationReposse ja siis lisame meetodi näol loogika ka  RestaurantTableService'sse. Lõpuks lisan uue endpointi("/available") ka controllerisse, kust peaks vabu laudu hakkama nägema. Claude mu vana sõber genereeris mulle nüüd URLi kontrollimaks kindlat aega restoranis ja õnneks filtreeris backend lauad kellaaja järgi super hästi. Nüüd on mul vaja ka frontend selle uue endpointiga ühendada! Kuna frontend on mulle veel endiselt natukene võõras siis aitas Claude mul index.html'is findTables() funktsiooni natukene mudida, mille tulemusel valmiski selline algne MVP minu rakendusest. Käivitan programmi ja vaatan mis toimub. Backend filtreerib lauad kellaaja järgi, frontend näitab saaliplaani, soovitusalgorütm leiab parima laua ja värvid näitavad laua staatust, elu on ilus! 

11.03.26 220min

Nüüd tuleks teha nii, et kasutaja saaks päriselt ka lauale klikates seda broneerida. Tean, et selleks vajalik endpoint "/reservations" on meil juba olemas, seega on vaja teha frontendis vorm mis saadaks POST requesti sellele aadressile. Claude'i abiga lisasin index.html'i modaali ehk peidetud akna, mis avaneb kui kasutaja klikib vabal laual (sees on vorm nime ja inimeste arvu sisestamiseks). Veel lisasin kolm funktsiooni: openBooking(tableId) – salvestab klikitud laua id ja teeb modaali nähtavaks, closeModal() – peidab modaali, confirmBooking() – võtab vormi andmed ja loob reservation objekti. Veel lisasin renderTables funktsioonis klikisündmuse - kui laud on vaba, avaneb openBooking(), kui on hõivatud ei juthu midagi. Põhimõtteliselt siis klikk laual → modaal avaneb → kasutaja täidab vormi → POST päring backendile → broneering salvestatakse → saaliplaan uueneb. Käivitan programmi ja näen, et broneeringu vorm töötab nagu mõeldud. Siis aga testimise käigus tulid välja mitmed probleemid. Esiteks olid tsoon ja eelistus kaks eraldi filtrit mis tegid sisuliselt sama asja ja häirisid mind väga – liitsin need üheks eelistuse valikuks. Frontendis tekkis palju väikseid vigu – eelistuste loogika ei töötanud õigesti, hõivatud lauad kadusid vahel vaatest ära ja ajatsooni probleem põhjustas selle, et kellaaeg näitas UTC aega mitte Eesti aega. Põhiprobleem oli see, et parandasin Claude'i abil üht asja ja läks teine katki, selline nokk kinni, saba lahti keiss. Õppisin, et ka frontendis tasub muudatusi teha väikeste sammudega ja skaleeritavalt nagu ikka. Lisasin ka broneeringu validatsiooni – kasutaja ei saa broneerida lauda rohkematele inimestele kui laud mahutab ning broneeringuid saab teha ainult lahtiolekuaegadel (11:00-21:00). DataInitializer'is parandasin juhuslike broneeringute genereerimise loogika nii, et broneeringud tekivad ainult lahtiolekuaegadel ja järgmiste päevade peale laiali. Küll aga on broneeringuid vähem kui ühes popis restoranis olla võiks - oletame, et see on selline ebapopulaarne tallinna restoran kus toit ei ole kõige parem. Lõpuks sai rakendus tööle nii nagu mõeldud – saaliplaan kuvab õigesti hõivatud ja vabad lauad valitud kellaajal, soovitusalgoritm töötab ja broneeringuid saab ka teha. Lõpuks söötsin Claudeile veel kogu oma dokumentatsiooni sisse, et ta selle põhjal mulle hea README.md kirjutaks(selles on ta küll väga hea) ja siin me nüüd oleme.


Väga lahe väike ülesanne oli ja suur aitäh selle ägeda võimaluse eest!

