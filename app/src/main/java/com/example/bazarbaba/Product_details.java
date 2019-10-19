package com.example.bazarbaba;

import android.os.Parcel;
import android.os.Parcelable;

public class Product_details implements Parcelable {
    private String Name;
    private String Shop;
    private Long Price;
    public Product_details(String Name,String Shop,Long Price)
    {
        this.Name=Name;
        this.Shop=Shop;
        this.Price=Price;
    }

    protected Product_details(Parcel in) {
        Name = in.readString();
        Shop = in.readString();
        if (in.readByte() == 0) {
            Price = null;
        } else {
            Price = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Shop);
        if (Price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(Price);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product_details> CREATOR = new Creator<Product_details>() {
        @Override
        public Product_details createFromParcel(Parcel in) {
            return new Product_details(in);
        }

        @Override
        public Product_details[] newArray(int size) {
            return new Product_details[size];
        }
    };

    public String getName() {
        return Name;
    }

    public String getShop() {
        return Shop;
    }

    public Long getPrice() {
        return Price;
    }
}
