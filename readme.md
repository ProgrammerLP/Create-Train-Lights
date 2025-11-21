# Create: Train Lights

## Fehlerbehebung: Gradle wartet auf Lock (fabric-loom)

Wenn beim Build oder beim Gradle-Sync Meldungen wie diese erscheinen:

```
Waiting for lock to be released...
Have been waiting on "Lock for cache='C:\Users\Luke\.gradle\caches\fabric-loom', project=':neoforge'" held by pid '7892' ...
If this persists for an unreasonable length of time, kill this process, run './gradlew --stop' and then try again.
```

Dann blockiert ein anderer Java/Gradle‑Prozess (meist IntelliJ IDEA oder ein paralleler Build) den gemeinsamen Gradle‑Cache. Das ist kein Fehler im Projekt, sondern ein Schutzmechanismus von Gradle/Loom, damit Caches nicht korrupt werden.

Warum passiert das?
- Ein anderer Build (oder eine andere IDE‑Instanz) verwendet zur gleichen Zeit den gleichen Cache (hier: `fabric-loom`).
- IntelliJ führt im Hintergrund einen Sync/Build aus, während Sie manuell `gradlew` starten.
- Ein vorheriger Prozess ist hängen geblieben und hat die Lock-Datei nicht freigegeben.

So lösen Sie das unter Windows/IntelliJ:
1) Warten Sie kurz: Oft wird das Lock nach Abschluss des anderen Builds automatisch freigegeben.
2) Beenden Sie parallele Läufe:
   - In IntelliJ: Stoppen Sie laufende Gradle‑Tasks (Run/Debug‑Werkzeugfenster) und warten Sie, bis der Sync fertig ist.
   - Schließen Sie andere Konsolen/Terminals, in denen `gradlew` gerade läuft.
3) Gradle‑Daemons stoppen:
   - In diesem Projektordner eine Konsole öffnen und ausführen:
     - Windows PowerShell: `./gradlew.bat --stop`
4) Falls weiterhin eine bestimmte PID das Lock hält:
   - Öffnen Sie den Task‑Manager, suchen Sie den Java‑Prozess mit der genannten PID (z. B. `7892`) und beenden Sie ihn vorsichtig.
   - Alternativ in PowerShell: `taskkill /PID 7892 /F` (nur wenn Sie sicher sind, dass es der hängende Prozess ist).
5) Erneut versuchen:
   - `./gradlew.bat build` oder den IDE‑Sync neu starten.

Optionale Vorbeugung:
- Nicht mehrere Builds gleichzeitig starten (IDE + Terminal).
- In IntelliJ können Sie automatische Gradle‑Syncs reduzieren (Einstellungen > Build Tools > Gradle).
- Für Workspaces mit mehreren großen Projekten hilft manchmal ein separates Gradle‑User‑Home pro Projekt:
  - Setzen Sie die Umgebungsvariable `GRADLE_USER_HOME` auf einen projekt‑spezifischen Ordner (z. B. `<Projekt>\.gradle-user`).
- Stale Locks/Caches nur im Notfall löschen:
  - Beenden Sie vorher alle Gradle/IDE‑Prozesse, dann können Sie den Ordner `C:\Users\<User>\.gradle\caches\fabric-loom` löschen. Beim nächsten Build wird er sauber neu angelegt.

Kurzfassung: Das Warten entsteht, weil ein anderer Prozess den Loom‑Cache exklusiv benutzt. Beenden/stoppen Sie den anderen Prozess oder den Gradle‑Daemon und starten Sie den Build erneut.