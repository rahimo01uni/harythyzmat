package com.eccomerce.com.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashish  Saraswat on 09/08/2016.
 */
public class ItemModel implements Parcelable {

    private  String Name="";
    private  String Price="";
   // private  int setItemArray_position;

    public ItemModel(){

    }

    public ItemModel(Parcel in) {
        setItemArray_position = in.readInt();
        Name = in.readString();
        Price = in.readString();
        ItemId = in.readString();
        item_qty = in.readString();
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public int getItemArray_position() {
        return setItemArray_position;
    }

    public void setItemArray_position(int setItemArray_positi) {
        setItemArray_position = setItemArray_positi;
    }

   int setItemArray_position;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    private String ItemId="";

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


    private  String item_qty;
    public String getItem_qty() {
        return item_qty;
    }

    public void setIteam_qty(String item_qt) {
        item_qty = item_qt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(setItemArray_position);
        dest.writeString(Name);
        dest.writeString(Price);
        dest.writeString(ItemId);
        dest.writeString(item_qty);
    }
}
