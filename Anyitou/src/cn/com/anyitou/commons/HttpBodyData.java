package cn.com.anyitou.commons;

import java.io.File;

public class HttpBodyData {
    public static final int TYPE_STRING = 1;
    public static final int TYPE_IMAGE  = 2;
    public static final int TYPE_BYTE  = 3;
    
    private int             mType;
    private File            mImageFileValue;
    private String          mStringValue;
    private byte[]          mByteValue;  
    private String          mByteFileName;

    public HttpBodyData( int type, File imageFileValue ) {
        super();
        
        mType = type;
        mImageFileValue = imageFileValue;
    }

    public HttpBodyData( int type, String stringValue ) {
        super();
        
        mType = type;
        mStringValue = stringValue;
    }
    
    public HttpBodyData( int type, byte[] byteValue, String byteFileName ) {
        super();
        
        mType = type;
        mByteValue = byteValue;
        mByteFileName = byteFileName;
    }

    public int getType() {
        return mType;
    }

    public void setType( int type ) {
        mType = type;
    }

    public File getImageFileValue() {
        return mImageFileValue;
    }

    public void setImageFileValue( File imageFileValue ) {
        mImageFileValue = imageFileValue;
    }

    public String getStringValue() {
        return mStringValue;
    }

    public void setStringValue( String stringValue ) {
        mStringValue = stringValue;
    }
    
    public byte[] getByteValue() {
        return mByteValue;
    }

    public void setByteValue( byte[] byteValue ) {
        mByteValue = byteValue;
    }
    
    public String getByteFileName() {
        return mByteFileName;
    }

    public void setByteFileName( String byteFileName ) {
        mByteFileName = byteFileName;
    }
    
}
