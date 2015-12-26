package dulleh.akhyou.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dulleh.akhyou.MainApplication;

public class Anime implements Parcelable{
    public static final int ANIME_RUSH = 0;
    public static final CharSequence ANIME_RUSH_TITLE = "ANIMERUSH";
    public static final int ANIME_RAM = 1;
    public static final CharSequence ANIME_RAM_TITLE = "ANIMERAM";
    public static final int ANIME_BAM = 2;
    public static final CharSequence ANIME_BAM_TITLE = "ANIMEBAM";
    public static final int ANIME_KISS = 3;
    public static final CharSequence ANIME_KISS_TITLE = "KISS BETA";

    // has to be here cos conflicts with V
    public Anime () {}

    private Anime(Parcel in) {
        providerType = in.readInt();
        title = in.readString();
        desc = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        status = in.readString();
        alternateTitle = in.readString();
        date = in.readString();
        genres = in.createStringArray();
        genresString = in.readString();
        episodes = new ArrayList<>();
        in.readList(episodes, null);
        majorColour = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(providerType);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(url);
        parcel.writeString(imageUrl);
        parcel.writeString(status);
        parcel.writeString(alternateTitle);
        parcel.writeString(date);
        parcel.writeStringArray(genres);
        parcel.writeString(genresString);
        parcel.writeList(episodes);
        parcel.writeInt(majorColour);
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    // ---------------------------------------------------------------------------------- //

    private Integer providerType; // if null: GeneralUtils.determineProviderType()
    private String title;
    private String desc;
    private String url;
    private String imageUrl;
    private String status;
    private String alternateTitle;
    private String date;
    private String[] genres;
    private String genresString;
    private List<Episode> episodes;
    // default to accent color
    private int majorColour = MainApplication.RED_ACCENT_RGB;

    public Integer getProviderType() {
        return providerType;
    }

    public Anime setProviderType(int providerType) {
        this.providerType = providerType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Anime setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Anime setDesc(String desc) {
        this.desc = desc.trim();
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Anime setUrl(String url) {
        this.url = url.trim();
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Anime setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.trim();
        return this;
    }

    public String[] getGenres() {
        return genres;
    }

    public Anime setGenres(String[] genres) {
        this.genres = genres;
        return this;
    }

    public String getGenresString() {
        return genresString;
    }

    public Anime setGenresString(String genresString) {
        this.genresString = genresString.trim();
        return this;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public Anime setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Anime setDate(String date) {
        this.date = date.trim();
        return this;
    }

    public String getAlternateTitle() {
        return alternateTitle;
    }

    public Anime setAlternateTitle(String alternateTitle) {
        this.alternateTitle = alternateTitle.trim();
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Anime setStatus(String status) {
        this.status = status.trim();
        return this;
    }

    public int getMajorColour() {
        return majorColour;
    }

    public Anime setMajorColour(int majorColour) {
        this.majorColour = majorColour;
        return this;
    }

    public void inheritWatchedFrom (List<Episode> oldEpisodes) {
        if (episodes != null) {
            List<String> episodeTitles = new LinkedList<>();

            for (Episode oldEpisode : oldEpisodes) {
                episodeTitles.add(oldEpisode.getTitle());
            }

            for (int i = 0; i < episodes.size(); i++) {
                Episode episode = episodes.get(i);
                if (episodeTitles.contains(episode.getTitle())) {
                    episodes.set(i, episode.setWatched(oldEpisodes.get(episodeTitles.indexOf(episode.getTitle())).isWatched()));
                }
            }
        }
    }

}
