class Abonne extends Utilisateur {

    // --- Attributs ---
    private ArrayList<PlayList> playlists;  
    private ArrayList<Morceau>  historique; 
    private boolean             suspendu;   

    // --- Constructeur ---

    public Abonne(String login, String motDePasse, String nom) {
        super(login, motDePasse, nom);
        this.playlists  = new ArrayList<>();
        this.historique = new ArrayList<>();
        this.suspendu   = false;
    }

    // --- Méthodes playlists ---

    public PlayList creerPlaylist(String nom) {
        PlayList nouvelle = new PlayList(nom, this);
        playlists.add(nouvelle);
        return nouvelle;
    }

    public void renommerPlaylist(PlayList playlist, String nouveauNom) throws PlayListIntrouvable {
        if (!playlists.contains(playlist)) {
            throw new PlayListIntrouvable("Cette playlist n'appartient pas à " + nom + ".");
        }
        playlist.setNom(nouveauNom);
    }

    public void supprimerPlaylist(PlayList playlist) throws PlayListIntrouvable {
        if (!playlists.remove(playlist)) {
            throw new PlayListIntrouvable("Cette playlist n'appartient pas à " + nom + ".");
        }
    }
    public void ajouterAPlaylist(Morceau m, PlayList p)
            throws PlayListIntrouvable, MorceauDejaPresent {
        if (!playlists.contains(p)) {
            throw new PlayListIntrouvable("Cette playlist n'appartient pas à " + nom + ".");
        }
        p.ajouter(m);
    }


    public void retirerDePlaylist(Morceau m, PlayList p)
            throws PlayListIntrouvable, MorceauIntrouvable {
        if (!playlists.contains(p)) {
            throw new PlayListIntrouvable("Cette playlist n'appartient pas à " + nom + ".");
        }
        p.retirer(m);
    }

    // --- Méthodes historique ---

    public void ajouterAHistorique(Morceau m) {
        // On insère en tête pour avoir le plus récent en premier
        historique.add(0, m);
    }


    public void effacerHistorique() {
        historique.clear();
    }

    // --- Gestion du compte ---

    public boolean estSuspendu() {
        return suspendu;
    }

    public void suspendre() {
        this.suspendu = true;
    }

    public void reactiver() {
        this.suspendu = false;
    }

    // --- Getters ---

    public ArrayList<PlayList> getPlaylists() {
        return new ArrayList<>(playlists);
    }
    public ArrayList<Morceau> getHistorique() {
        return new ArrayList<>(historique);
    }
    public int getNbPlaylists() {
        return playlists.size();
    }
    public int getNbEcoutesTotales() {
        return historique.size();
    }

    @Override
    public String getRole() {
        return "Abonne";
    }

    @Override
    public String toString() {
        String statut = suspendu ? " [SUSPENDU]" : "";
        return "[Abonné" + statut + "] " + nom
                + " | Playlists : " + getNbPlaylists()
                + " | Écoutes totales : " + getNbEcoutesTotales();
    }
}

