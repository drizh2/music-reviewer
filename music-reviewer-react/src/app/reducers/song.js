const INITIAL_STATE = {
    songs: [],
    songDetails: {},
    isFetchingSongs: false,
    isFetchingSongDetails: false,
    isSavingSong: false,
    saveSongSuccess: false,
    showSongSuccess: false
}

export default function Reducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ('REQUEST_SAVE_SONG'): {
            return {
                ...state,
                isSavingSong: true
            };
        }

        case ('SUCCESS_SAVE_SONG'): {
            return {
                ...state,
                isSavingSong: false,
                saveSongSuccess: true,
                showSongSuccess: true
            };
        }

        case ('ERROR_SAVE_SONG'): {
            return {
                ...state,
                isSavingSong: false,
                saveSongSuccess: false,
                showSongSuccess: true
            };
        }

        case('REQUEST_FILTER'): {
            return {
                ...state,
                isFetchingSongs: true
            };
        }

        case ('DELETE_ITEM'): {
            return {
                ...state,
                songs: state.songs.filter(item => item.id !== action.payload)
            };
        }

        case('REQUEST_SONGS_INFO'): {
            return {
                ...state,
                isFetchingSongs: true
            };
        }

        case('REQUEST_SONG_DETAILS'): {
            return {
                ...state,
                isFetchingSongDetails : true
            };
        }

        case('SUCCESS_SONGS_FILTER'): {
            const songs = action.payload;
            return {
                ...state,
                isFetchingSongs: false,
                songs: songs,
            };
        }

        case('SUCCESS_SONGS_FETCHING'): {
            const songs = action.payload;
            return {
                ...state,
                isFetchingSongs: false,
                songs: songs
            };
        }

        case('SUCCESS_SONG_DETAILS_FETCHING'): {
            const songDetails = action.payload;
            return {
                ...state,
                isFetchingSongDetails: false,
                songDetails
            };
        }

        default: {
            return {
                ...state
            };
        }
    }
}