package looigi.gestionecampionato.dati;

import looigi.gestionecampionato.utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StrutturaCampionato {
    private List<StrutturaSquadreCampionato> Squadre = new ArrayList<>();
    private List<StrutturaSquadreClassifica> SquadreClass = new ArrayList<>();
    private HashMap<Integer, List<StrutturaPartita>> ListaPartite = new HashMap<Integer, List<StrutturaPartita>>();
    private int NumeroGiornate;
    private int GiornataAttuale;
    private int GiornataClassificaAttuale;

    public int getGiornataClassificaAttuale() {
        return GiornataClassificaAttuale;
    }

    public void setGiornataClassificaAttuale(int giornataClassificaAttuale) {
        GiornataClassificaAttuale = giornataClassificaAttuale;
    }

    public List<StrutturaSquadreCampionato> getSquadre() {
        return Squadre;
    }

    public void setSquadre(List<StrutturaSquadreCampionato> squadre) {
        Squadre = squadre;
    }

    public List<StrutturaSquadreClassifica> getSquadreClass() {
        return SquadreClass;
    }

    public void setSquadreClass(List<StrutturaSquadreClassifica> squadreClass) {
        SquadreClass = squadreClass;
    }

    public void RimuovePartitaGiornata(int Giornata, int Partita) {
        List<StrutturaPartita> l = new ArrayList<>();
        List<StrutturaPartita> g = ListaPartite.get(Giornata);
        if (g != null) {
            for (StrutturaPartita s : g) {
                if (s.getIdPartitaGiorno() != Partita) {
                    l.add(s);
                }
            }
            ListaPartite.put(Giornata, l);
        }
    }

    public List<String> RitornaSquadreGiornata(int Giornata) {
        List<String> l = new ArrayList<>();
        List<StrutturaPartita> g = ListaPartite.get(Giornata);
        if (g != null) {
            for (StrutturaPartita s : g) {
                l.add(Integer.toString(s.getIdSqCasa()) + ";" + s.getCasa());
                l.add(Integer.toString(s.getIdSqFuori()) + ";" + s.getFuori());
            }
        }
        return l;
    }

    public List<StrutturaPartita> getListaPartiteGiornata(int Giornata) {
        List<StrutturaPartita> l = ListaPartite.get(Giornata);
        return l;
    }

    public StrutturaPartita getPartitaGiornata(int Giornata, int Partita) {
        List<StrutturaPartita> l = ListaPartite.get(Giornata);
        return l.get(Partita);
    }

    public void AggiungeGiornataSeNonEsiste(int Giornata) {
        List<StrutturaPartita> l = new ArrayList<>();
        ListaPartite.put(Giornata, l);
    }

    public void AggiungePartita(int Giornata, StrutturaPartita s) {
        if (ListaPartite.get(Giornata) == null) {
            AggiungeGiornataSeNonEsiste(Giornata);
        }

        List<StrutturaPartita> l =new ArrayList<>(ListaPartite.get(Giornata));
        l.add(s);
        ListaPartite.get(Giornata).clear();
        for (StrutturaPartita ll : l) {
            ListaPartite.get(Giornata).add(ll);
        }
    }

    public void ModificaPartita(int Giornata, int Partita, StrutturaPartita s) {
        if (ListaPartite.get(Giornata) == null) {
            AggiungeGiornataSeNonEsiste(Giornata);
        }

        List<StrutturaPartita> l =new ArrayList<>(ListaPartite.get(Giornata));
        ListaPartite.get(Giornata).clear();
        for (StrutturaPartita ll : l) {
            if (ll.getIdPartitaGiorno() != Partita) {
                ListaPartite.get(Giornata).add(ll);
            } else {
                ListaPartite.get(Giornata).add(s);
            }
        }
    }

    public void CalcolaNumeroGiornate() {
        NumeroGiornate=0;
        int q = 0;
        if (getSquadreClass() != null) {
            q = Squadre.size();
            if (!Utility.getInstance().ePari(q)) {
                // q++;
            }
            NumeroGiornate = (q-1) * 2;
        }
    }

    public int getGiornataAttuale() {
        return GiornataAttuale;
    }

    public void setGiornataAttuale(int giornataAttuale) {
        GiornataAttuale = giornataAttuale;
    }

    public int getNumeroGiornate() {
        return NumeroGiornate;
    }
}
