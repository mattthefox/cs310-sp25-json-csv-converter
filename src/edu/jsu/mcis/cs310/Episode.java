package edu.jsu.mcis.cs310;

public class Episode {
    private String prodNum;
    private String title;
    private int season;
    private int episode;
    private String stardate;
    private String originalAirdate;
    private String remasteredAirdate;

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getStardate() {
        return stardate;
    }

    public void setStardate(String stardate) {
        this.stardate = stardate;
    }

    public String getOriginalAirdate() {
        return originalAirdate;
    }

    public void setOriginalAirdate(String originalAirdate) {
        this.originalAirdate = originalAirdate;
    }

    public String getRemasteredAirdate() {
        return remasteredAirdate;
    }

    public void setRemasteredAirdate(String remasteredAirdate) {
        this.remasteredAirdate = remasteredAirdate;
    }
}
