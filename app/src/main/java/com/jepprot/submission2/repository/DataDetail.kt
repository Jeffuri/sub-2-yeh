package com.jepprot.submission2.repository

data class DataDetail(
    val login: String,
    val avatar_url: String,
    val name: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)
