package cn.com.anyitou.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Urls implements Parcelable {

	/**
	 * 
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Urls(){
		
	}
	public Urls(Parcel in)
    {
		url = in.readString();
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(url);
	}

	public static final Parcelable.Creator<Urls> CREATOR = new Creator<Urls>() {
		@Override
		public Urls[] newArray(int size) {
			return new Urls[size];
		}

		@Override
		public Urls createFromParcel(Parcel in) {
			return new Urls(in);
		}
	};

}
