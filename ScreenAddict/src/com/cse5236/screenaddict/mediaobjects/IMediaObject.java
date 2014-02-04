package com.cse5236.screenaddict.mediaobjects;

import java.net.URL;

public interface IMediaObject {
	public String getTitle();
	public String getYear();
	public String getSummary();
	public String toString();
	public URL getPrimaryImage();
	public URL getTraktPageURL();
}
