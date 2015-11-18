package dulleh.akhyou.Utils.Events;

import dulleh.akhyou.Models.Anime;

public class FavouriteEvent {
    public boolean addToFavourites; // if false: remove from favourites
    public Anime anime;

    public FavouriteEvent (boolean addToFavourites, Anime anime) {
        this.addToFavourites = addToFavourites;
        this.anime = anime;
    }

}
