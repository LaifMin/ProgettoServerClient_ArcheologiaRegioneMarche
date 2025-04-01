public class Data  {
    
    private String urlImmagine;
    private String didascalia;
    private String tipologiaPDI;
    private String denominazione;
    private String comune;
    private String indirizzo;
    private String civico;
    private String telefono;
    private String email;
    private String urlSitoWeb;
    private String latitudine;
    private String longitudine;
    private String orariDiApertura;


    public Data(String urlImmagine, String didascalia, String tipologiaPDI, String denominazione, String comune,
            String indirizzo, String civico, String telefono, String email, String urlSitoWeb, String latitudine,
            String longitudine, String orariDiApertura) {
        this.urlImmagine = urlImmagine;
        this.didascalia = didascalia;
        this.tipologiaPDI = tipologiaPDI;
        this.denominazione = denominazione;
        this.comune = comune;
        this.indirizzo = indirizzo;
        this.civico = civico;
        this.telefono = telefono;
        this.email = email;
        this.urlSitoWeb = urlSitoWeb;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.orariDiApertura = orariDiApertura;
    }


    public String getUrlImmagine() {
        return urlImmagine;
    }


    public String getDidascalia() {
        return didascalia;
    }


    public String getTipologiaPDI() {
        return tipologiaPDI;
    }


    public String getDenominazione() {
        return denominazione;
    }


    public String getComune() {
        return comune;
    }


    public String getIndirizzo() {
        return indirizzo;
    }


    public String getCivico() {
        return civico;
    }


    public String getTelefono() {
        return telefono;
    }


    public String getEmail() {
        return email;
    }


    public String getUrlSitoWeb() {
        return urlSitoWeb;
    }


    public String getLatitudine() {
        return latitudine;
    }


    public String getLongitudine() {
        return longitudine;
    }


    public String getOrariDiApertura() {
        return orariDiApertura;
    }


    @Override
    public String toString() {
        return "urlImmagine: " + urlImmagine + ", didascalia: " + didascalia + ", tipologiaPDI: " + tipologiaPDI
                + ", denominazione: " + denominazione + ", comune: " + comune + ", indirizzo: " + indirizzo + ", civico:"
                + civico + ", telefono: " + telefono + ", email: " + email + ", urlSitoWeb: " + urlSitoWeb
                + ", latitudine: " + latitudine + ", longitudine: " + longitudine + ", orariDiApertura: " + orariDiApertura
                + "]";
    }

    

    
    
    
}
