package com.example.islam.movies.watch_later;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "later")

public class WatchLaterEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "item_id")
    private int itemId;
    @ColumnInfo(name = "item_type")
    private String type;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "item_image")
    private String itemImage;

    public WatchLaterEntity() {

    }

    public WatchLaterEntity(int itemId, String type, String name, String itemImage) {

        this.itemId = itemId;
        this.type = type;
        this.name = name;
        this.itemImage = itemImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
