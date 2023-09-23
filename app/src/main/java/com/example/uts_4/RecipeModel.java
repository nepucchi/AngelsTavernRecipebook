package com.example.uts_4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RecipeModel implements Parcelable {
    private String recipeID;
    private String recipeTitle;
    private String recipeType;
    private String recipeDesc;
    private String recipeIngre;

    public RecipeModel(){}

    public RecipeModel(String recipeID, String recipeTitle, String recipeType, String recipeDesc, String recipeIngre) {
        this.recipeID = recipeID;
        this.recipeTitle = recipeTitle;
        this.recipeType = recipeType;
        this.recipeDesc = recipeDesc;
        this.recipeIngre = recipeIngre;
    }

    protected RecipeModel(Parcel in) {
        recipeID = in.readString();
        recipeTitle = in.readString();
        recipeType = in.readString();
        recipeDesc = in.readString();
        recipeIngre = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getRecipeDesc() {
        return recipeDesc;
    }

    public void setRecipeDesc(String recipeDesc) {
        this.recipeDesc = recipeDesc;
    }

    public String getRecipeIngre() {
        return recipeIngre;
    }

    public void setRecipeIngre(String recipeIngre) {
        this.recipeIngre = recipeIngre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(recipeID);
        parcel.writeString(recipeTitle);
        parcel.writeString(recipeType);
        parcel.writeString(recipeDesc);
        parcel.writeString(recipeIngre);
    }
}
