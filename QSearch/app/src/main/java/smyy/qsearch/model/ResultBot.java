package smyy.qsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sümeyye on 6.5.2017.
 */

public class ResultBot implements Serializable {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("text")
    @Expose
    public String text;
}
