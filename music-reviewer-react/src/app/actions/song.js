import axios from 'misc/requests';
import config from "../../config";

const MOCK_SONGS =  [
    {
        "id": 1,
        "title": "Don't Stay",
        "album": "Meteora",
        "year": 2003,
        "genres": "Alternative Rock. Nu-Metal.",
        "artist": "Linkin Park"
    },
    {
        "id": 2,
        "title": "Numb",
        "album": "Meteora",
        "year": 2003,
        "genres": "Alternative Rock. Nu-Metal.",
        "artist": "Linkin Park"
    },
    {
        "id": 3,
        "title": "In the End",
        "album": "Hybrid Theory",
        "year": 2000,
        "genres": "Alternative Rock. Nu-Metal.",
        "artist": "Linkin Park"
    },
    {
        "id": 4,
        "title": "One Step Closer",
        "album": "Hybrid Theory",
        "year": 2000,
        "genres": "Alternative Rock. Nu-Metal.",
        "artist": "Linkin Park"
    },
    {
        "id": 5,
        "title": "ROADS UNTRAVELED",
        "album": "LIVING THINGS",
        "year": 2000,
        "genres": "Alternative Rock. Nu-Metal.",
        "artist": "Linkin Park"
    },
    {
        "id": 6,
        "title": "Friend, please",
        "album": "Twenty One Pilots",
        "year": 2009,
        "genres": "Dark-cabaret",
        "artist": "twenty one pilots"
    },
    {
        "id": 7,
        "title": "Ode to Sleep",
        "album": "Vessel",
        "year": 2013,
        "genres": "Alternative hip-hop. Rap-rock.",
        "artist": "twenty one pilots"
    },
    {
        "id": 8,
        "title": "Fairly Local",
        "album": "Blurryface",
        "year": 2015,
        "genres": "Rock. Alternative rock.",
        "artist": "twenty one pilots"
    },
    {
        "id": 9,
        "title": "The Hype",
        "album": "Trench",
        "year": 2018,
        "genres": "Hip-hop. Alternative rock.",
        "artist": "twenty one pilots"
    },
    {
        "id": 10,
        "title": "Redecorate",
        "album": "Scaled And Icy",
        "year": 2021,
        "genres": "Hip-hop. Alternative rock.",
        "artist": "twenty one pilots"
    },
    {
        "id": 11,
        "title": "Gasoline",
        "album": "TRAUMA",
        "year": 2019,
        "genres": "Melodic metalcore. Pop-metal",
        "artist": "I Prevail"
    },
    {
        "id": 12,
        "title": "Hurricane",
        "album": "TRAUMA",
        "year": 2019,
        "genres": "Melodic metalcore. Pop-metal",
        "artist": "I Prevail"
    },
    {
        "id": 13,
        "title": "Bad Things",
        "album": "TRUE POWER",
        "year": 2022,
        "genres": "Metalcore. Nu-metal.",
        "artist": "I Prevail"
    },
    {
        "id": 14,
        "title": "Scars",
        "album": "Lifelines",
        "year": 2016,
        "genres": "Metalcore. Post-hardcore.",
        "artist": "I Prevail"
    },
    {
        "id": 15,
        "title": "There’s Fear In Letting Go",
        "album": "TRUE POWER",
        "year": 2022,
        "genres": "Metalcore. Nu-metal.",
        "artist": "I Prevail"
    },
    {
        "id": 16,
        "title": "THE DEATH OF PIECE OF MIND",
        "album": "THE DEATH OF PIECE OF MIND",
        "year": 2022,
        "genres": "Metalcore. Alternative rock.",
        "artist": "Bad Omens"
    },
    {
        "id": 17,
        "title": "Like A Villain",
        "album": "THE DEATH OF PIECE OF MIND",
        "year": 2022,
        "genres": "Metalcore. Alternative rock.",
        "artist": "Bad Omens"
    },
    {
        "id": 18,
        "title": "Just Pretend",
        "album": "THE DEATH OF PIECE OF MIND",
        "year": 2022,
        "genres": "Metalcore. Alternative rock.",
        "artist": "Bad Omens"
    },
    {
        "id": 19,
        "title": "The Worst in Me",
        "album": "Bad Omens",
        "year": 2016,
        "genres": "Metalcore.",
        "artist": "Bad Omens"
    },
    {
        "id": 20,
        "title": "Limits",
        "album": "Finding God Before God Finds Me(Extended)",
        "year": 2020,
        "genres": "Metalcore.",
        "artist": "Bad Omens"
    },
    {
        "id": 21,
        "title": "Back in Black",
        "album": "Back in Black",
        "year": 1980,
        "genres": "Hard-rock. Heavy-metal",
        "artist": "AC/DC"
    },
    {
        "id": 22,
        "title": "You Shook Me All Night Long",
        "album": "Back in Black",
        "year": 1980,
        "genres": "Hard-rock. Heavy-metal",
        "artist": "AC/DC"
    },
    {
        "id": 23,
        "title": "Thunderstruck",
        "album": "The Razors Edge",
        "year": 1990,
        "genres": "Hard-rock. Heavy-metal",
        "artist": "AC/DC"
    },
    {
        "id": 24,
        "title": "Highway to Hell",
        "album": "Highway to Hell",
        "year": 1979,
        "genres": "Hard-rock.",
        "artist": "AC/DC"
    },
    {
        "id": 25,
        "title": "T.N.T.",
        "album": "High Voltage",
        "year": 1976,
        "genres": "Hard-rock.",
        "artist": "AC/DC"
    },
    {
        "id": 26,
        "title": "Wasted Years",
        "album": "Somewhere in Time",
        "year": 1986,
        "genres": "Heavy-metal.",
        "artist": "Iron Maiden"
    },
    {
        "id": 27,
        "title": "Hallowed Be Thy Name",
        "album": "The Number of the Beast",
        "year": 1982,
        "genres": "Heavy-metal.",
        "artist": "Iron Maiden"
    },
    {
        "id": 28,
        "title": "Fear of the Dark",
        "album": "Fear of the Dark",
        "year": 1992,
        "genres": "Heavy-metal.",
        "artist": "Iron Maiden"
    },
    {
        "id": 29,
        "title": "The Trooper",
        "album": "Piece of Mind",
        "year": 1983,
        "genres": "Heavy-metal.",
        "artist": "Iron Maiden"
    },
    {
        "id": 30,
        "title": "2 Minutes to Midnight",
        "album": "Powerslave",
        "year": 1984,
        "genres": "Heavy-metal.",
        "artist": "Iron Maiden"
    },
    {
        "id": 31,
        "title": "N.I.B.",
        "album": "Black Sabbath",
        "year": 1970,
        "genres": "Heavy-metal.",
        "artist": "Black Sabbath"
    },
    {
        "id": 32,
        "title": "Paranoid",
        "album": "Paranoid",
        "year": 1970,
        "genres": "Heavy-metal.",
        "artist": "Black Sabbath"
    },
    {
        "id": 33,
        "title": "War Pigs",
        "album": "Paranoid",
        "year": 1970,
        "genres": "Heavy-metal.",
        "artist": "Black Sabbath"
    },
    {
        "id": 34,
        "title": "Iron Man",
        "album": "Paranoid",
        "year": 1970,
        "genres": "Heavy-metal.",
        "artist": "Black Sabbath"
    },
    {
        "id": 35,
        "title": "Never Say Die!",
        "album": "Never Say Die!",
        "year": 1978,
        "genres": "Heavy-metal. Hard-rock.",
        "artist": "Black Sabbath"
    },
    {
        "id": 36,
        "title": "I'm In Love With My Car",
        "album": "A Night At The Opera",
        "year": 1975,
        "genres": "Rock. Opera.",
        "artist": "Queen"
    },
    {
        "id": 37,
        "title": "Another One Bites The Dust",
        "album": "The Game",
        "year": 1980,
        "genres": "Rock. Rock-n-Roll.",
        "artist": "Queen"
    },
    {
        "id": 38,
        "title": "We Will Rock You",
        "album": "News of the World",
        "year": 1977,
        "genres": "Hard-rock. Progressive rock.",
        "artist": "Queen"
    },
    {
        "id": 39,
        "title": "Bohemian Rhapsody",
        "album": "A Night At The Opera",
        "year": 1975,
        "genres": "Rock. Opera.",
        "artist": "Queen"
    },
    {
        "id": 40,
        "title": "Don't Stop Me Now",
        "album": "Jazz",
        "year": 1978,
        "genres": "Rock. Pop.",
        "artist": "Queen"
    },
    {
        "id": 41,
        "title": "Song without field",
        "album": "Song without field",
        "genres": "Song without field.",
        "artist": "Queen"
    },
    {
        "id": 42,
        "title": "Nothing Else Matters",
        "album": "Metallica",
        "year": 1991,
        "genres": "Heavy-metal",
        "artist": "Metallica"
    },
    {
        "id": 43,
        "title": "Enter Sandman",
        "album": "Metallica",
        "year": 1991,
        "genres": "Heavy-metal",
        "artist": "Metallica"
    },
    {
        "id": 44,
        "title": "The Unforgiven",
        "album": "Metallica",
        "year": 1991,
        "genres": "Heavy-metal",
        "artist": "Metallica"
    },
    {
        "id": 45,
        "title": "One",
        "album": "...And Justice For All",
        "year": 1991,
        "genres": "Heavy-metal",
        "artist": "Metallica"
    },
    {
        "id": 46,
        "title": "Ride The Lightning",
        "album": "Ride The Lightning",
        "year": 1984,
        "genres": "Thrash-metal",
        "artist": "Metallica"
    },
    {
        "id": 47,
        "title": "Wait and Bleed",
        "album": "Slipknot",
        "year": 1999,
        "genres": "Nu-metal.",
        "artist": "Slipknot"
    },
    {
        "id": 48,
        "title": "The Devil in I",
        "album": ".5: The Gray Chapter",
        "year": 2014,
        "genres": "Nu-metal. Heavy-metal.",
        "artist": "Slipknot"
    },
    {
        "id": 49,
        "title": "Snuff",
        "album": "All Hope Is Gone",
        "year": 2008,
        "genres": "Heavy-metal. Alternative metal",
        "artist": "Slipknot"
    },
    {
        "id": 50,
        "title": "Psychosocial",
        "album": "All Hope Is Gone",
        "year": 2008,
        "genres": "Heavy-metal. Alternative metal",
        "artist": "Slipknot"
    },
    {
        "id": 51,
        "title": "Custer",
        "album": ".5: The Gray Chapter",
        "year": 2014,
        "genres": "Nu-metal. Heavy-metal.",
        "artist": "Slipknot"
    },
    {
        "id": 52,
        "title": "Duality",
        "album": "Vol. 3: The Subliminal Verses",
        "year": 2004,
        "genres": "Nu-metal. Alternative metal.",
        "artist": "Slipknot"
    },
];

const deleteItem = (id) => ({
    type: 'DELETE_ITEM',
    payload: id
});

const requestSongs = () => ({
    type: 'REQUEST_SONGS_INFO',
});

const requestSongDetails = () => ({
   type: 'REQUEST_SONG_DETAILS',
});

const requestFilterSongs = () => ({
    type: 'REQUEST_FILTER',
});

const successSongsFilter = (songs) => ({
    type: 'SUCCESS_SONGS_FILTER',
    payload: songs,
})

const successSongsFetching = (songs) => ({
    payload: songs,
    type: 'SUCCESS_SONGS_FETCHING',
});

const successSongDetailsFetching = (songDetails) => ({
    payload: songDetails,
    type: 'SUCCESS_SONG_DETAILS_FETCHING',
});

const requestSaveSong = () => ({
    type: 'REQUEST_SAVE_SONG'
});

const successSaveSong = () => ({
    type: 'SUCCESS_SAVE_SONG'
});

const failureSaveSong = () => ({
    type: 'ERROR_SAVE_SONG'
});

const fetchEditSong = (songId, song) => (dispatch) => {
    dispatch(requestSaveSong);
    return editSong(songId, song)
        .then(() => {
            dispatch(successSaveSong);
        })
        .catch(() => {
            // dispatch(failureSaveSong); // if working with BE
            for (let i = 0; i < MOCK_SONGS.length; i++) {
                if (MOCK_SONGS[i].id === songId) {
                    MOCK_SONGS[i].title = song.title;
                    MOCK_SONGS[i].year = song.year;
                    MOCK_SONGS[i].album = song.album;
                    MOCK_SONGS[i].genres = song.genres;
                    MOCK_SONGS[i].artist = song.artist;
                }
            }
            dispatch(successSaveSong);
        })
}

const fetchSaveSong = (song) => (dispatch) => {
    dispatch(requestSaveSong);
    return saveSong(song)
        .then(() => {
            dispatch(successSaveSong);
        })
        .catch(() => {
            // dispatch(failureSaveSong); // if working with BE
            MOCK_SONGS.push(song);
            dispatch(successSaveSong);
        })
}

const fetchFilterSongs = ({ title, year }) => (dispatch) => {
    dispatch(requestFilterSongs());
    return filterSongs(title, year)
        .then((filteredSongs) => {
            let songs;
            if (title && year) {
                songs = filteredSongs.filter(s => s.title === title && s.year === year);
            } else if (title) {
                songs = filteredSongs.filter(s => s.title === title);
            } else if (year) {
                songs = filteredSongs.filter(s => s.year === year);
            } else {
                songs = filteredSongs;
            }
            dispatch(successSongsFilter(songs));
        })
        .catch(() => {
            let songs;
            if (title && year) {
                songs = MOCK_SONGS.filter(s => s.title === title && s.year === year);
            } else if (title) {
                songs = MOCK_SONGS.filter(s => s.title === title);
            } else if (year) {
                songs = MOCK_SONGS.filter(s => s.year === year);
            } else {
                songs = MOCK_SONGS;
            }
            dispatch(successSongsFilter(songs));
        });
};

const fetchSongs = () => (dispatch) => {
    dispatch(requestSongs);
    return getSongs()
        .then((songs) => {
            dispatch(successSongsFetching(songs.list))
        })
        .catch(() => {
            dispatch(successSongsFetching(MOCK_SONGS))
        })
}

const fetchSongDetails = ({ id }) => (dispatch) => {
    dispatch(requestSongDetails);
    return getSongDetails(id)
        .then((song) => {
            dispatch(successSongDetailsFetching(song))
        })
        .catch(() => {
            const song = MOCK_SONGS.filter(s => s.id === id)[0];
            dispatch(successSongDetailsFetching(song))
        })
}

const getSongs = () => {
    const {
        BE_URL,
    } = config;

    return axios.post(
        `${BE_URL}/api/songs/_list`,
        {
            page: 1,
            size: 5,
        });
}

const getSongDetails = (id) => {
    const {
        BE_URL
    } = config

    return axios.get(`${BE_URL}/api/songs/${id}`);
}

const filterSongs = (title, year) => {
    const {
        BE_URL
    } = config;

    return axios.post(
        `${BE_URL}/api/songs/_list`,
        {title, year});
}

const saveSong = (song) => {
    const {
        BE_URL
    } = config;
    return axios.post(
        `${BE_URL}/api/songs`,
        {song});
}

const editSong = (songId, song) => {
    const {
        BE_URL
    } = config;
    return axios.put(
        `${BE_URL}/api/songs/${songId}`,
        {song});
}


export default {
    fetchSongs,
    fetchSongDetails,
    deleteItem,
    fetchFilterSongs,
    fetchSaveSong,
}