import React, { useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useIntl } from "react-intl";
import {TextField, IconButton, Alert, Collapse} from "@mui/material";
import { Edit as EditIcon } from "@mui/icons-material"; // Імпортую іконку олівця
import { useLocation, useNavigate } from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import Typography from "../../../components/Typography";
import Button from "../../../components/Button";
import Link from "../../../components/Link";
import useTheme from "../../../misc/hooks/useTheme";
import pagesURLs from "../../../constants/pagesURLs";
import * as pages from "../../../constants/pages";
import actionSongs from "app/actions/song";

const getClasses = createUseStyles((theme) => ({
    fields: {
        marginTop: "20px",
        display: "flex",
        flexDirection: "column",
        gap: "10px",
    },
    buttons: {
        marginTop: "20px",
        display: "flex",
        flexDirection: "row",
        gap: "50px",
        width: "50%",
    },
    editButtonContainer: {
        display: "flex",
        justifyContent: "flex-end",
        marginBottom: "20px",
    },
}));

function Edit() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { formatMessage } = useIntl();
    const { theme } = useTheme();
    const classes = getClasses({ theme });
    const { state } = useLocation();
    const songDetails = useSelector((store) => store.song.songDetails);

    let changing;
    let creating;

    if (state !== null) {
        changing = state.changing;
        creating = state.creating;
    }

    useEffect(() => {
        if (componentState.creating !== null && componentState.creating === true) {
            componentState.title = "";
            componentState.album = "";
            componentState.year = "";
            componentState.genres = "";
            componentState.artist = "";
        } else {
            componentState.title = songDetails.title;
            componentState.album = songDetails.album;
            componentState.year = songDetails.year;
            componentState.genres = songDetails.genres;
            componentState.artist = songDetails.artist;
        }
    }, []);

    const handleCancel = () => {
        if (componentState.creating) {
            navigate(`${pagesURLs[pages.list]}`);
        } else {
            setState({
                ...componentState,
                changing: false,
            });
        }
    };

    const handleChange = (event) => {
        setState({
            ...componentState,
            title: songDetails.title,
            album: songDetails.album,
            year: songDetails.year,
            genres: songDetails.genres,
            artist: songDetails.artist,
            [event.target.name]: event.target.value,
        });
    };

    const handleEdit = () => {
        setState({
            ...componentState,
            changing: true,
        });
    }

    const handleSave = () => {
        if (componentState.year > 2024 || componentState.year < 1900) {
            setState({
                ...state,
                errorYear: true,
                changing: true
            });
            setTimeout(() => {
                setState({
                    ...state,
                    errorYear: false,
                    changing: true
                });
            }, 2000);
        } else {
            if (componentState.creating) {
                dispatch(actionSongs.fetchSaveSong({
                    title: componentState.title,
                    album: componentState.album,
                    year: componentState.year,
                    genres: componentState.genres,
                    artist: componentState.artist
                }));
            } else {
                dispatch(actionSongs.fetchSaveSong(songDetails.id, {
                    title: componentState.title,
                    album: componentState.album,
                    year: componentState.year,
                    genres: componentState.genres,
                    artist: componentState.artist
                }));

                songDetails.title = componentState.title;
                songDetails.album = componentState.album;
                songDetails.year = componentState.year;
                songDetails.genres = componentState.genres;
                songDetails.artist = componentState.artist;
            }

            setState({
                ...state,
                successEdit: true,
                changing: false
            });
            setTimeout(() => {
                setState({
                    ...state,
                    successEdit: false,
                    changing: false
                });
            }, 2000);
        }
    }

    const [componentState, setState] = useState({
        title: "",
        year: "",
        album: "",
        artist: "",
        genres: "",
        changing: changing,
        creating: creating,
        errorYear: false,
        successEdit: false,
    });

    console.log(componentState);

    return (
        <>
            <div className={classes.editButtonContainer}>
                {!componentState.changing && (
                    <IconButton onClick={handleEdit}>
                        <EditIcon />
                    </IconButton>
                )}
            </div>
            <Typography>{formatMessage({ id: "title" })}</Typography>
            <div className={classes.fields}>
                <TextField
                    disabled={!componentState.changing}
                    name="title"
                    label={formatMessage({ id: "titleInput" })}
                    value={componentState.creating || componentState.changing ? componentState.title : songDetails.title}
                    onChange={handleChange}
                    variant="outlined"
                />
                <TextField
                    disabled={!componentState.changing}
                    name="year"
                    label={formatMessage({ id: "yearInput" })}
                    value={componentState.creating || componentState.changing ? componentState.year : songDetails.year}
                    onChange={handleChange}
                    variant="outlined"
                />
                <TextField
                    disabled={!componentState.changing}
                    name="album"
                    label={formatMessage({ id: "albumInput" })}
                    value={componentState.creating || componentState.changing ? componentState.album : songDetails.album}
                    onChange={handleChange}
                    variant="outlined"
                />
                <TextField
                    disabled={!componentState.changing}
                    name="genres"
                    label={formatMessage({ id: "genresInput" })}
                    value={componentState.creating || componentState.changing ? componentState.genres : songDetails.genres}
                    onChange={handleChange}
                    variant="outlined"
                />
                <TextField
                    disabled={!componentState.changing}
                    name="artist"
                    label={formatMessage({ id: "artistInput" })}
                    value={componentState.creating || componentState.changing ? componentState.artist : songDetails.artist}
                    onChange={handleChange}
                    variant="outlined"
                />
            </div>
            {componentState.changing && (
                <div>
                    <div className={classes.buttons}>
                        <Button onClick={handleSave}>
                            {componentState.creating
                                ? formatMessage({ id: "create" })
                                : formatMessage({ id: "save" })}
                        </Button>
                        <Button onClick={handleCancel}>{formatMessage({id: "cancel"})}</Button>
                    </div>
                </div>
            )}
            <div>
                <Button>
                    <Link
                        to={{
                            pathname: `${pagesURLs[pages.list]}`,
                        }}
                    >
                        {formatMessage({id: "back"})}
                    </Link>
                </Button>
            </div>
            <Collapse in={componentState.errorYear}>
                <Alert severity="error">
                    {formatMessage({ id: "wrongYear" })}
                </Alert>
            </Collapse>
            <Collapse in={componentState.successEdit}>
                <Alert severity="success">
                    {formatMessage({ id: "successEdit" })}
                </Alert>
            </Collapse>
        </>
    );
}

export default Edit;
