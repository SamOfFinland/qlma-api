[![Slack](https://qlma-slackin.herokuapp.com/badge.svg)](https://qlma-slackin.herokuapp.com/)
[![Stories in Ready](https://badge.waffle.io/qlma/server.png?label=ready&title=Ready)](https://waffle.io/qlma/server)
[![Dependencies Status](http://jarkeeper.com/qlma/server/status.png)](http://jarkeeper.com/qlma/server)
![Build status](https://travis-ci.org/qlma/server.svg?branch=master)
![QLMA logo](https://raw.githubusercontent.com/qlma/media/master/qlma.png)

QLMA on oppilaitoksille tarkoitettu palvelu jonka tarkoitus on mullistaa oppilaitosten viestintä. Lisätietoa sivustolta: https://storify.com/iiuusit/qlma-n-synty


Tällä hetkellä projekti on kehityksen alla.

# Kehittämään

1. Asenna Java, jos ei ole ennestään
1. Asenna Leiningen - http://leiningen.org/
1. Kloonaa Qlma:n palvelin
1. Käynnistä palvelin

    ```bash
    $ cd $PROJECT_ROOT
    $ lein ring server
    ```

Voit myös asentaa koneellesi Vagrant-ympäristön ja hyödyntää valmiiksi konfiguroitua virtuaalikonetta.

1. Asenna Vagrant (https://www.vagrantup.com/)
1. Kloonaa Qlma:n palvelin GitHubista
1. Siirry komentokehoitteessa projektihakemistosi juureen ja käynnistä Vagrant-virtuaalikone (jälkimmäinen komento avaa SSH-terminaalin virtuaalikoneeseen)

    ```bash
    $ cd $PROJECT_ROOT
    $ vagrant up && vagrant ssh
    ```

1. Siirry virtuaalikoneessa `/vagrant`-hakemistoon ja käynnistä palvelinprosessi

    ```bash
    $ cd /vagrant
    $ lein ring server
    ```

1. Avaa nettiselaimeen osoite http://localhost:3000 ja varmista, että näet Hello World -sivun