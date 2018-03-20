package com.manshop.android.model;

public class Role {
    private Integer id;
    private String name;
    private Integer animeId;
    private Anime anime;
    private Integer akiraId;
    private Role role;

    public Role(){
    }

    public Role(Integer id, String name, Integer animeId, Anime anime, Integer akiraId, Role role) {
        this.id = id;
        this.name = name;
        this.animeId = animeId;
        this.anime = anime;
        this.akiraId = akiraId;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Integer getAkiraId() {
        return akiraId;
    }

    public void setAkiraId(Integer akiraId) {
        this.akiraId = akiraId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
