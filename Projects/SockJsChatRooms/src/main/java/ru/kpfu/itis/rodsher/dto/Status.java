package ru.kpfu.itis.rodsher.dto;

public enum Status {
    USER_SIGN_IN_SUCCESS,
    USER_SIGN_IN_ERROR,
    USER_REG_SUCCESS,
    USER_REG_ERROR,
    USER_LOAD_SUCCESS,
    USER_LOAD_ERROR,
    USER_LOAD_BY_NAME_AND_SURNAME_SUCCESS,
    USER_LOAD_FRIENDS_SUCCESS,
    ARTICLE_LOAD_SUCCESS,
    ARTICLE_LOAD_ERROR,
    ARTICLE_DELETE_SUCCESS,
    ARTICLE_DELETE_ERROR,
    ARTICLE_LOAD_BY_USER_ID_SUCCESS,
    ARTICLE_LOAD_BY_USER_ID_ERROR,
    ARTICLE_ADD_SUCCESS,
    ARTICLE_ADD_ERROR,
    FRIENDS_UPDATE_STATUS_SUCCESS,
    FRIENDS_REMOVE_SUCCESS,
    FRIENDS_REMOVE_ERROR,
    FRIENDSHIP_PRESENTED,
    FRIENDSHIP_EMPTY,
    CHANNEL_ADD_SUCCESS,
    CHANNEL_ADD_ERROR,
    CHANNEL_ADD_USER_TO_SUCCESS,
    CHANNEL_ADD_USER_TO_ERROR,
    CHANNEL_LOAD_SUCCESS,
    CHANNEL_LOAD_ERROR,
    CHANNEL_LOAD_ALL_SUCCESS,
    CHANNEL_LOAD_ALL_ERROR,
    CHANNEL_EXISTS_FOR_USERS,
    CHANNEL_NOT_EXISTS_FOR_USERS,
    CHANNEL_LOAD_USERS_SUCCESS,
    CHANNEL_LOAD_USERS_ERROR,
    MESSAGE_LOAD_SUCCESS,
    MESSAGE_LOAD_ERROR,
    MESSAGE_ADD_SUCCESS,
    MESSAGE_ADD_ERROR,
    MESSAGE_LOAD_BY_CHANNEL_ID_SUCCESS,
    MESSAGE_LOAD_BY_CHANNEL_ID_ERROR,
}
