package seedu.anilist.model.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.anilist.model.genre.Genre;
import seedu.anilist.model.genre.GenreList;

/**
 * Contains integration tests (interaction with AnimeList) for {@code Stats}.
 */
public class StatsTest {

    @Test
    public void genreCountLimitSize_returnsTrue() {
        //max number of top genres less than number of unique genres in GenreList
        assert Stats.getGenreCountLimit() < GenreList.getListOfGenres().length;
    }

    @Test
    public void getStats_withEmptyStats_returnsTrue() {
        int watchingCount = 0;
        int toWatchCount = 0;
        int finishedCount = 0;
        HashMap<Genre, Integer> allGenres = new HashMap<>();
        Stats stats = new Stats(watchingCount, toWatchCount, finishedCount, allGenres);

        int expectedTotalAnimes = 0;
        int expectedNumUniqueGenres = 0;
        HashMap<Genre, Integer> expectedTopGenres = new HashMap<>();

        assertEquals(stats.getTotalAnimesCount(), expectedTotalAnimes);
        assertEquals(stats.getNumUniqueGenres(), expectedNumUniqueGenres);
        assertEquals(stats.getTopGenres(), expectedTopGenres);
    }

    @Test
    public void getStats_withoutGenres_returnsTrue() {
        int watchingCount = 12;
        int toWatchCount = 23;
        int finishedCount = 34;
        HashMap<Genre, Integer> allGenres = new HashMap<>();
        Stats stats = new Stats(watchingCount, toWatchCount, finishedCount, allGenres);

        int expectedTotalAnimes = watchingCount + toWatchCount + finishedCount;
        int expectedNumUniqueGenres = 0;
        HashMap<Genre, Integer> expectedTopGenres = new HashMap<>();

        assertEquals(stats.getTotalAnimesCount(), expectedTotalAnimes);
        assertEquals(stats.getNumUniqueGenres(), expectedNumUniqueGenres);
        assertEquals(stats.getTopGenres(), expectedTopGenres);
    }

    @Test
    public void getStats_numUniqueGenresLessThanMax_returnsTrue() {
        int numUniqueGenres =
                Math.min(Stats.getGenreCountLimit(), GenreList.getListOfGenres().length);
        int watchingCount = numUniqueGenres;
        int toWatchCount = 0;
        int finishedCount = 0;
        HashMap<Genre, Integer> allGenres = new HashMap<>();

        int expectedTotalAnimes = watchingCount + toWatchCount + finishedCount;
        int expectedNumUniqueGenres = numUniqueGenres;

        HashMap<Genre, Integer> expectedTopGenres = new HashMap<>();
        for (int i = 0; i < numUniqueGenres; i++) {
            Genre genre = new Genre(GenreList.getListOfGenres()[i]);
            allGenres.put(genre, 1);
            expectedTopGenres.put(genre, 1);
        }
        Stats stats = new Stats(watchingCount, toWatchCount, finishedCount, allGenres);

        assertEquals(stats.getTotalAnimesCount(), expectedTotalAnimes);
        assertEquals(stats.getNumUniqueGenres(), expectedNumUniqueGenres);
        assertEquals(stats.getTopGenres(), expectedTopGenres);
    }

    @Test
    public void getStats_withAllGenresInGenreList_returnsTrue() {
        //set these up such that there is no ambiguity in which are the top genres
        int numGenresTaggedOnce = GenreList.getListOfGenres().length - Stats.getGenreCountLimit();
        int numGenresTaggedTwice = Stats.getGenreCountLimit();

        //each anime is tagged with one genre here
        int watchingCount = numGenresTaggedOnce + numGenresTaggedTwice;
        int toWatchCount = 0;
        int finishedCount = 0;
        HashMap<Genre, Integer> allGenres = new HashMap<>();

        int expectedTotalAnimes = watchingCount + toWatchCount + finishedCount;
        int expectedNumUniqueGenres = GenreList.getListOfGenres().length;

        HashMap<Genre, Integer> expectedTopGenres = new HashMap<>();
        for (int i = 0; i < numGenresTaggedOnce; i++) {
            //these are the genres that are tagged once
            Genre genre = new Genre(GenreList.getListOfGenres()[i]);
            allGenres.put(genre, 1);
        }
        for (int i = numGenresTaggedOnce; i < expectedTotalAnimes; i++) {
            //these would be the top genres since they are tagged twice
            Genre genre = new Genre(GenreList.getListOfGenres()[i]);
            allGenres.put(genre, 2);
            expectedTopGenres.put(genre, 2);
        }
        Stats stats = new Stats(watchingCount, toWatchCount, finishedCount, allGenres);

        assertEquals(stats.getTotalAnimesCount(), expectedTotalAnimes);
        assertEquals(stats.getNumUniqueGenres(), expectedNumUniqueGenres);
        assertEquals(stats.getTopGenres(), expectedTopGenres);
    }
}
