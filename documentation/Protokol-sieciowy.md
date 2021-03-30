 PROZE20L Jakub Nerć – Lunar Lander
 
 ## Protokół sieciowy – projekt
 Elementem aplikacji, realizującej projekt gry Lunar Lander, jest funkcjonalność sieciowa. 
 Opierać się ona na relacji klienta (gry) z zewnętrznym serwerem. 
 Na serwerze przechowywane są pliki konfiguracyjne powiązane zarówno ze scenariuszem i ogólnymi 
 zasadami rozgrywki, jak i strukturą pojedynczych poziomów. Na nim również znajduje się 
 lista najlepszych wyników aktualizowana w razie potrzeby po każdej rozgrywce w aplikacji klienckiej. 
 W tym samym czasie do serwera podłączony może być więcej niż jeden klient. 
 
 ### Zarys protokołu
 Komunikacja między klientem a serwerem odbywa się przy wykorzystaniu 
 standardowego pakietu ```java.net```, implementującego protokół TCP. 
 Pojedynczym pakietem danych, będącym podstawą porozumiewania się między stronami, 
 będzie linika tekstu, zakończona znakiem końca lini ```\n```. 
 
 ### Kolejne etapy kominukacji klient-serwer
 Wymieniane wiadomości będą prezentowane zgodnie z następującym szablonem:  
 ```
C(i): TEXT-x-y-z => S
 ```
 gdzie ```C(i)``` – klient o identyfikatorze ```i```, ```S``` – serwer, ```TEXT``` – treść komunikatu, ```-x``` – parametr komunikatu. 
 Każdy kolejny parametr separowany jest dywizem ```-```.
 #### 1. Wysłanie żądania zalogowania przez klienta do serwera: 
 Kiedy nawiąże się połączenie TCP między klientem a serwerem, klient wysyła żądanie zalogowania
 na serwerze: 
 ``` 
 C: ADDME => S 
 ``` 
 
 #### 2. Odpowiedź serwera na żądanie zalogowania:  
 * W przypadku, gdy serwer jest w stanie obsłużyć klienta, loguje go i odpowiada komunikatem:  
 ```
 S: ADDED-i => C(i)
``` 
 gdzie ```i (int)``` to numer przydzielony klientowi przez serwer.  
 * Jeśli serwer nie może dołączyć kolejnego klienta, komunikat przybiera postać:  
 ```
 S: ADDINGFAILED => C
 ```
 
 #### 3. Żądanie pobrania głównego pliku konfiguracyjnego:
 Po nawiązaniu połączenia z serwerem, aplikacja kliencka wysyła do serwera żądanie o pobranie 
 pliku zawierającego scenariusz gry, zasady naliczania punktów oraz podstawowe parametry wspólne 
 dla każdego z poziomów.  
 ```
 C(i) GETCONFIG => S
 ```
 
 #### 4. Przesłanie pliku konfiguracyjnego: 
 * Serwer w odpowiedzi nadaje komunikat o rozpoczęciu przesyłania kolejnych linijek 
 pliku konfiguracyjnego:  
 ```
 S: CONFIGSTART => C(i)
 ```
 * Następnie przesyła kolejne pakiety, będące linijkami tekstu z przesyłanego pliku:
 ```
 S: textline => C(i)
 ```
 * Zakończenie przesyłania jest tożsame z nadaniem komunikatu:
 ```
 S: CONFIGEND => C(i)
 ```
 
 #### 5. Żądanie przesłania parametrów poziomów:  
 Klient wysyła żądanie o pobranie pliku z kofiguracją wszystkich poziomów:  
 ```
 C(i): GETLEVELS => S 
 ```  
 
 #### 6. Przesłanie pliku z parametrami poziomów:  
 * Serwer w odpowiedzi nadaje komunikat o rozpoczęciu przesyłania kolejnych linijek 
 pliku z parametrami:  
 ```
 S: LEVELSSTART => C(i)
 ```
 * Następnie przesyła kolejne pakiety, będące linijkami tekstu z przesyłanego pliku:
 ```
 S: textline => C(i)
 ```
 * Zakończenie przesyłania jest tożsame z nadaniem komunikatu:
 ```
 S: LEVELSEND => C(i)
 ```
 
 #### 7. Przesłanie wyniku gracza:
 Po zakończeniu rozgrywki do serwera przesyłany jest uzyskany przez gracza wynik wraz z jego nickiem. 
 Jeśli kwalifikuje się do znalezienia na liście najlepszych rezultatów, serwer aktualizuje ją.  
 ```
 C(i): UPDATE-n-f => S
 ```  
 gdzie ```n (char[8])```to nick, a ```f (float)``` uzyskany przez gracza wynik
 
 #### 8. Informacja o dołączeniu wyniku do listy najlepszych: 
 Po otrzymaniu wyniku gracza i przeanalizowaniu go pod kątem umieszczenia go na liście
 najlepszych rezultatów serwer wysyła wiadomość:  
 ```
 S: NEWBEST-t-s => C(i)
 ``` 
 gdzie ```t (boolean)``` przyjmie wartość ```true```, jeśli wynik znalazł się na liście najlepszych wyników, 
 ```false``` w przeciwnym wypadku; ```s (int)``` w przypadku, gdy ```t = true```, jest liczbą informującą o zajętym miejcu
 w rankingu; w przypadku ```t = false``` przyjmuje wartość ```0```
 
 #### 9. Żądanie pobrania listy najlepszych wyników:  
 Po wybraniu przez użytkownika stosownej opcji, klient żąda z serwera pliku z listą dotychczasowych 
 najlepszych wyników. 
 ```
 C(i): GETRESULTS => S 
 ```
 #### 10. Przesłanie listy najlepszych wyników: 
 * Serwer w odpowiedzi nadaje komunikat o rozpoczęciu przesyłania kolejnych linijek 
 pliku z listą najlepszych wyników:  
```
 S: RESULTSSTART => C(i)
 ```
 * Następnie przesyła kolejne pakiety, będące linijkami tekstu z przesyłanego pliku:
 ```
 S: textline => C(i)
 ```
 * Zakończenie przesyłania jest tożsame z nadaniem komunikatu:
 ```
 S: RESULTSEND => C(i)
 ```
 
 #### 11. Żądanie wylogowania: 
 Klient, chcąc się rozłączyć, wysyła do serwera żądanie wylogowania:  
 ```
 C(i): REMOVEME => S
 ```  
 
 #### 12. Odpowiedź serwera na żądanie wylogowania:
 Serwer wysyła odpowiedź o pomyślnym zakończeniu sesji:  
 ```
 S: REMOVED => C(i)
 ``` 
 
 #### 13. Komunikat o błędzie ze strony serwera:
 Jeśli na serwerze brakuje żądanego pliku, wysyłana jest wiadomość:  
 ```
 S: NOTFOUND => C(i)
 ```
  
 #### 14. Komunikat o przerwanej pracy serwera:
 Jeśli serwer jest zmuszony przerwać swoją pracę, wysyła do klientów wiadomość:  
 ```
 S: WORKLOCALLY => C
 ``` 
 co zmusza ich do pracy, w miarę możliwości, na plikach lokalnych.
 
 #### 15. Potwierdzenie otrzymania komunikatu o zatrzymaniu pracy serwera: 
 Klienci potwierdzają otrzymanie wiadomości jednocześnie deklarując, że będą pracować lokalnie i 
 nie wyślą kolejnych żądań.
 ```
C: WORKINGLOCALLY => S
```