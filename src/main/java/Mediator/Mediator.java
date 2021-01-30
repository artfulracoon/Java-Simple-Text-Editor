package Mediator;

public class Mediator {
    KelimeBulVeDegistir kelimeBulVeDegistir;
    HataliKelimeleriDuzelt hataliKelimeleriDuzelt;

    public void add(Object o) {
        if (o instanceof KelimeBulVeDegistir) {
            kelimeBulVeDegistir = (KelimeBulVeDegistir) o;
        } else {
            hataliKelimeleriDuzelt = (HataliKelimeleriDuzelt) o;
        }
    }

    public void executeKelimeBul() {
        kelimeBulVeDegistir.execute();
    }

    public void executeHataliKelime() {
        hataliKelimeleriDuzelt.execute();
    }
}

