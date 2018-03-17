package me.sanderbrugge.speedyhealth.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Looproutes {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("line_google")
    @Expose
    public String lineGoogle;
    @SerializedName("score")
    @Expose
    public String score;
    @SerializedName("type_id")
    @Expose
    public String typeId;
    @SerializedName("view_count")
    @Expose
    public String viewCount;
    @SerializedName("length")
    @Expose
    public String length;

    public Looproutes(String id, String name, String lineGoogle, String score, String typeId, String viewCount, String length) {
        this.id = id;
        this.name = name;
        this.lineGoogle = lineGoogle;
        this.score = score;
        this.typeId = typeId;
        this.viewCount = viewCount;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineGoogle() {
        return lineGoogle;
    }

    public void setLineGoogle(String lineGoogle) {
        this.lineGoogle = lineGoogle;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
