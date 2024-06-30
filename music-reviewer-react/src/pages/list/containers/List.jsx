import React, { useEffect, useState } from "react";
import { useIntl } from "react-intl";
import Typography from "../../../components/Typography";
import Card from "../../../components/Card";
import CardTitle from "../../../components/CardTitle";
import { useDispatch, useSelector } from "react-redux";
import { createUseStyles } from "react-jss";
import useTheme from "../../../misc/hooks/useTheme";
import Dialog from "../../../components/Dialog";
import {
    Alert,
    Button,
    Collapse,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Pagination,
    TextField,
} from "@mui/material";
import actionSongs from "app/actions/song";
import pagesURLs from "../../../constants/pagesURLs";
import * as pages from "../../../constants/pages";
import IconButton from "../../../components/IconButton";
import PlusIcon from "../../../components/icons/PlusIcon";
import {Link} from "react-router-dom";

const getClasses = createUseStyles((theme) => ({
    container: {
        marginTop: '30px !important',
        width: '100%',
        border: '1px solid #ccc',
        display: 'flex',
        justifyContent: 'space-between',
        '&:hover $button': {
            display: 'block',
        },
    },
    button: {
        right: '40px',
        background: 'none',
        border: 'none',
        fontSize: '24px',
        cursor: 'pointer',
        display: 'none',
    }
}));

const LOCAL_STORAGE_KEY = 'songsFilterState';

function List() {

    const { formatMessage } = useIntl();
    const dispatch = useDispatch();
    const { theme } = useTheme();
    const classes = getClasses({ theme });

    const [state, setState] = useState({
        isConfirmDialogOpened: false,
        selectedItemId: 0,
        isAlertOpened: false,
        page: 0,
        rowsPerPage: 5,
        title: '',
        year: '',
        yearError: false,
        id: 0
    });

    useEffect(() => {
        const savedState = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
        if (savedState) {
            setState({
                ...state,
                id: savedState.id,
                page: savedState.page,
                title: savedState.title,
                year: savedState.year
            });
            dispatch(actionSongs.fetchFilterSongs({ title: savedState.title, year: parseInt(savedState.year) }));
        }
    }, [dispatch]);

    useEffect(() => {
        const toSave = {
            id: state.id,
            page: state.page,
            title: state.title,
            year: state.year
        }
        localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(toSave));
    }, [state]);

    useEffect(() => {
        if (state.id !== 0) {
            dispatch(actionSongs.fetchSongDetails({ id: state.id }));
        }

        console.log("Use Effect State Id")
    }, [state.id]);

    const handleConfirm = () => {
        dispatch(actionSongs.deleteItem(state.selectedItemId));

        setState({
            ...state,
            isConfirmDialogOpened: false,
            isAlertOpened: true,
        });
        setTimeout(() => {
            setState({
                ...state,
                isAlertOpened: false,
                isConfirmDialogOpened: false,
            });
        }, 2000);
    };

    const handleChange = (event, value) => {
        setState({
            ...state,
            page: value,
        });
    };

    const handleFilterChange = (event) => {
        setState({
            ...state,
            [event.target.name]: event.target.value
        });
    };

    const handleFilter = () => {
        dispatch(actionSongs.fetchFilterSongs({ title: state.title, year: parseInt(state.year) }));
    };

    const handleSongDetails = (songId) => {
        // setState({
        //     ...state,
        //     id: songId,
        // });
        dispatch(actionSongs.fetchSongDetails({ id: songId }));
    };

    const songs = useSelector((store) => store.song.songs);

    useEffect(() => {
        const maxPage = Math.ceil(songs.length / state.rowsPerPage);
        if (state.page > maxPage) {
            setState({
                ...state,
                page: maxPage
            });
        }
    }, [songs, state.page]);

    const showedSongs = songs.slice((state.page - 1) * state.rowsPerPage, state.page * state.rowsPerPage);

    return (
        <>
            <Typography>
                {formatMessage({ id: 'title' })}
            </Typography>
            <Link to={`${pagesURLs[pages.edit]}`} state={{changing: true, creating: true}}>
                <IconButton color="primary" aria-label="add to shopping cart">
                    <PlusIcon />
                </IconButton>
            </Link>
            <div>
                <TextField
                    name="title"
                    label={formatMessage({id: "titleFilter"})}
                    value={state.title}
                    onChange={handleFilterChange}
                    variant="outlined"
                />
                <TextField
                    name="year"
                    label={formatMessage({id: "year"})}
                    value={state.year}
                    onChange={handleFilterChange}
                    variant="outlined"
                    error={state.yearError}
                    helperText={state.yearError ? "Year must be a number" : ""}
                />
                <Button onClick={handleFilter} disabled={state.yearError}>{formatMessage({id: "filter"})}</Button>
            </div>
            <div>
                {showedSongs.map(item => (
                    <div key={item.id} className={classes.container}>
                        <Card>
                            <Link
                                onClick={() => handleSongDetails(item.id)}
                                  to={`${pagesURLs[pages.edit]}`} state={{changing: false, creating: false}}>
                                <CardTitle>
                                    {item.title}({item.album}) - {item.artist}
                                </CardTitle>
                            </Link>
                        </Card>
                        <button className={classes.button}
                                onClick={() => {
                                    setState(prevState => ({
                                        ...prevState,
                                        isConfirmDialogOpened: true,
                                        selectedItemId: item.id
                                    }));
                                }}
                        >&#128465;</button>
                    </div>
                ))}
            </div>
            <Pagination
                count={Math.ceil(songs.length / state.rowsPerPage)}
                page={state.page}
                onChange={handleChange}
            />
            <Dialog open={state.isConfirmDialogOpened}
                    maxWidth="xs"
                    onClose={() => {
                        setState(prevState => ({
                            ...prevState,
                            isConfirmDialogOpened: false
                        }));
                    }}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {formatMessage({ id: 'deleteTitle' })}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {formatMessage({ id: 'deleteText' })}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => {
                        setState(prevState => ({
                            ...prevState,
                            isConfirmDialogOpened: false
                        }));
                    }}>{formatMessage({ id: "no" })}</Button>
                    <Button onClick={handleConfirm} autoFocus>
                        {formatMessage({ id: "yes" })}
                    </Button>
                </DialogActions>
            </Dialog>
            <Collapse in={state.isAlertOpened}>
                <Alert severity="success">
                    {formatMessage({ id: "entityDeletionMessage" })}
                </Alert>
            </Collapse>
        </>
    );
}

export default List;
