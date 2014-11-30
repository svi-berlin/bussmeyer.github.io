// Ein zentraler Job, der nachgelagert nach allen Buildjobs ausgeführt wird und die Artefakte an eine zentrale Stelle kopiert.

// scp Bussmeyer-bussmeyer.github.io-1-13.x86_64.rpm vagrant@192.168.2.202:/var/www/repo.local/artefacts-6.5/
// crontab anstoßen: /usr/bin/createrepo --cachedir /var/cache/repo.local/artefacts-6.5 --changelog-limit 5 --update /var/www/repo.local/artefacts-6.5 1>/dev/null
